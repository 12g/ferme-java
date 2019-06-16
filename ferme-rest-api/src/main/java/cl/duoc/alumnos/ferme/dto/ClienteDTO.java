package cl.duoc.alumnos.ferme.dto;

import cl.duoc.alumnos.ferme.domain.entities.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Benjamin Guillermo
 */
public class ClienteDTO extends PersonaDTO {
    private final static Logger LOG = LoggerFactory.getLogger(ClienteDTO.class);

    private Integer idCliente;

    public ClienteDTO() {
        super();
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
    
    public Cliente toEntity() {
        Cliente entity = new Cliente();
        try {
            if (idCliente != 0) {
                entity.setId(idCliente);
            }
        } catch (NullPointerException exc) {
            LOG.info("toEntity() - idCliente es null");
        }
        
        entity.setPersona(this.personaToEntity());
        
        return entity;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" + "idCliente=" + idCliente + ", " + super.toString() + '}';
    }
    
}