package cl.duoc.alumnos.ferme.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import cl.duoc.alumnos.ferme.entities.Persona;
import cl.duoc.alumnos.ferme.entities.QUsuario;
import cl.duoc.alumnos.ferme.entities.Usuario;
import cl.duoc.alumnos.ferme.jpa.repositories.IPersonasRepository;
import cl.duoc.alumnos.ferme.jpa.repositories.IUsuariosRepository;
import cl.duoc.alumnos.ferme.services.interfaces.IUsuariosService;
import cl.duoc.alumnos.ferme.util.FormatoFechas;
import cl.duoc.alumnos.ferme.util.Hashing;
import javassist.NotFoundException;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Service
@Transactional
public class UsuariosService implements IUsuariosService {
    private static final Logger LOG = LoggerFactory.getLogger(UsuariosService.class);

    @Autowired private IUsuariosRepository usuarioRepo;
    @Autowired private IPersonasRepository personaRepo;
    
    @Override
    public Collection<UsuarioDTO> getUsuarios(int pageSize, int pageIndex, Predicate condicion) {
        Sort orden = Sort.by(FermeConfig.COLUMNAS_ORDENAMIENTO_MAPA.get(Usuario.class)).ascending();
        Pageable pgbl = PageRequest.of(pageIndex, pageSize, orden);
        
        List<UsuarioDTO> pagina = new ArrayList<>();
        Iterable<Usuario> usuarios;
        
        if (condicion == null) {
            usuarios = usuarioRepo.findAll(pgbl);
        } else {
            usuarios = usuarioRepo.findAll(condicion, pgbl);
        }
        
        
        usuarios.forEach((entity) -> {
            UsuarioDTO dto = entity.toDTO();
            pagina.add(dto);
        });
        
        
        return pagina;
    }

    @Override
    public Predicate queryParamsMapToUsuariosFilteringPredicate(Map<String, String> queryParamsMap) {
        
        QUsuario qUsuario = QUsuario.usuario;
        BooleanBuilder bb = new BooleanBuilder();
        for (String paramName : queryParamsMap.keySet()) {
            String paramValue = queryParamsMap.get(paramName);
            LOG.debug(paramName+"="+paramValue);
            try {
                Integer parsedValueI;
                switch (paramName) {
                    case "id":
                        parsedValueI = Integer.valueOf(paramValue);
                        bb.and(qUsuario._id.eq(parsedValueI));
                        return bb;
                    case "nombre":
                        paramValue = "%" + paramValue + "%";
                        bb.and(qUsuario._nombre.likeIgnoreCase(paramValue));
                        break;
                    case "fechaCreacion":
                        paramValue = paramValue.trim();
                        Date fecha = FormatoFechas.stringADateLocal(paramValue);
                        if (fecha == null) {
                            LOG.warn("UsuariosService.queryParamsMapToUsuariosFilteringPredicate() : El formato de la fecha ingresada no es válida.");
                        } else {
                            bb.and(qUsuario._fechaCreacion.eq(fecha));
                        }
                        break;
                    default: break;
                }
            } catch (NumberFormatException exc) {
                LOG.error("No se pudo traducir el parámetro '" + paramName + "' a un número (su valor era '" + paramValue + "').", exc);
            }
        }
        
        return bb;
    }

    @Override
    public int saveUsuario(UsuarioDTO dto) throws NotFoundException {
        
        Usuario entity;
        if (dto.getIdUsuario() == null || dto.getIdUsuario() == 0) {
            entity = dto.toEntity();
            Date fechaAhora = Calendar.getInstance().getTime();
            entity.setFechaCreacion(fechaAhora);
            LOG.debug(entity.getClave());
        } else {
            Optional<Usuario> entityQuery = usuarioRepo.findById(dto.getIdUsuario());
            if (entityQuery.isPresent()) {
                entity = entityQuery.get();
                entity.setNombre(dto.getNombreUsuario());
            } else {
                throw new NotFoundException("Usuario no encontrado");
            }
        }
        
        Optional<Persona> personaEntity = personaRepo.findById(dto.getIdPersona());
        if (personaEntity.isPresent()) {
            LOG.debug("hay persona en usuario");
            entity.setPersona(personaEntity.get());
        } else {
            throw new NotFoundException("Persona de Usuario no encontrada");
        }
        
        entity = usuarioRepo.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public boolean deleteUsuario(Integer usuarioId) {
        try {
            usuarioRepo.deleteById(usuarioId);
            return true;
        } catch (Exception exc) {
            LOG.error("Error al borrar Usuario con id " +usuarioId, exc);
            return false;
        }
    }

    @Override
    public UsuarioDTO getUsuarioFromCredentials(String username, String password) {
        QUsuario qUsr = QUsuario.usuario;
        String claveUsuarioHash = Hashing.hashear(password);
        BooleanBuilder bb = new BooleanBuilder()
                .and(qUsr._nombre.eq(username))
                .and(qUsr._clave.eq(claveUsuarioHash));

        Optional<Usuario> result = usuarioRepo.findOne(bb);
        
        if (result.isPresent()) {
            UsuarioDTO usuario = result.get().toDTO();
            usuario.setClaveUsuario(password);
            return usuario;
        } else {
            return null;
        }
    }
    
}
