package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.entities.Persona;
import cl.duoc.alumnos.ferme.entities.Usuario;
import cl.duoc.alumnos.ferme.util.FormatoFechas;
import cl.duoc.alumnos.ferme.util.Hashing;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class UsuarioDTO extends PersonaDTO {
    @SuppressWarnings("unused")
	private final static Logger LOG = LoggerFactory.getLogger(PersonaDTO.class);
    
    private Integer idUsuario;
    private String nombreUsuario;
    private String claveUsuario;
    private String fechaCreacionUsuario;
    private String sesion;
    

    public UsuarioDTO() {
        super();
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getSesion() {
        return sesion;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }

    public String getFechaCreacionUsuario() {
        return fechaCreacionUsuario;
    }

    public void setFechaCreacionUsuario(String fechaCreacionUsuario) {
        this.fechaCreacionUsuario = fechaCreacionUsuario;
    }
    
    public Usuario toEntity() {
        Usuario entity = new Usuario();
        if (idUsuario != null && idUsuario != 0) {
            entity.setId(idUsuario);
        }
        entity.setNombre(nombreUsuario);
        
        String claveUsuarioHash = Hashing.hashear(claveUsuario);
        entity.setClave(claveUsuarioHash);
        
        if (fechaCreacionUsuario != null && !fechaCreacionUsuario.isEmpty()) {
            Date _fechaCreacion = FormatoFechas.stringADateLocal(fechaCreacionUsuario);
            entity.setFechaCreacion(_fechaCreacion);                
        }
        
        Persona personaEntity = super.personaToEntity();
        entity.setPersona(personaEntity);
        
        return entity;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" + "idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", fechaCreacionUsuario=" + fechaCreacionUsuario + ", persona=" + super.toString() + '}';
    }
    
}
