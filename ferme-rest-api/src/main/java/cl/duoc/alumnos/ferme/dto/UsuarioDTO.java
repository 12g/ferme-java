package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.domain.entities.Usuario;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class UsuarioDTO {
    private final static Logger LOG = LoggerFactory.getLogger(UsuarioDTO.class);
    
    private Integer idUsuario;
    private String nombreUsuario;
    private String claveUsuario;
    private String fechaCreacionUsuario;
    private PersonaDTO persona;

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

    public String getFechaCreacionUsuario() {
        return fechaCreacionUsuario;
    }

    public void setFechaCreacionUsuario(String fechaCreacionUsuario) {
        this.fechaCreacionUsuario = fechaCreacionUsuario;
    }

    public PersonaDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }
    
    public Usuario toEntity() throws ParseException {
        Usuario entity = new Usuario();
        try {
            if (idUsuario != 0) {
                entity.setId(idUsuario);
            }
        } catch (NullPointerException exc) {
            LOG.info("toEntity() - idUsuario es null");
        }
        
        entity.setNombre(nombreUsuario);
        
        DateFormat formateador = new SimpleDateFormat(Ferme.DEFAULT_DATE_FORMAT);
        entity.setFechaCreacion(formateador.parse(fechaCreacionUsuario));
        
        return entity;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" + "idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", claveUsuario=" + claveUsuario + ", fechaCreacionUsuario=" + fechaCreacionUsuario + ", persona=" + persona + '}';
    }
    
}