package cl.duoc.alumnos.ferme.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import cl.duoc.alumnos.ferme.Globals;
import cl.duoc.alumnos.ferme.dto.ClienteDTO;
import cl.duoc.alumnos.ferme.dto.EmpleadoDTO;
import cl.duoc.alumnos.ferme.dto.PersonaDTO;
import cl.duoc.alumnos.ferme.dto.ProveedorDTO;

/** Representa un registro de la tabla PERSONA.
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Entity
@Table(name = "PERSONA")
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")})
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_PERSONA")
    @SequenceGenerator(name = "persona_seq", sequenceName = "SEQ_PERSONA", initialValue = 1, allocationSize = Globals.ESPACIO_ASIGNACION_SECUENCIAS_HIBERNATE)
    @GeneratedValue(generator = "persona_seq", strategy = GenerationType.AUTO)
    private Integer _id;

    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_COMPLETO")
    private String _nombreCompleto;
    
    @Size(min = 1, max = 20)
    @Column(name = "RUT")
    private String _rut;
    
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Email inválido")
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String _email;
    
    @Size(max = 200)
    @Column(name = "DIRECCION")
    private String _direccion;
    
    @Column(name = "FONO1")
    private Long _fono1;
    
    @Column(name = "FONO2")
    private Long _fono2;
    
    @Column(name = "FONO3")
    private Long _fono3;

    public Persona() {
        super();
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public String getNombreCompleto() {
        return _nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this._nombreCompleto = nombreCompleto;
    }

    public String getRut() {
        return _rut;
    }

    public void setRut(String rut) {
        this._rut = rut;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public String getDireccion() {
        return _direccion;
    }

    public void setDireccion(String direccion) {
        this._direccion = direccion;
    }

    public Long getFono1() {
        return _fono1;
    }

    public void setFono1(Long fono1) {
        this._fono1 = fono1;
    }

    public Long getFono2() {
        return _fono2;
    }

    public void setFono2(Long fono2) {
        this._fono2 = fono2;
    }

    public Long getFono3() {
        return _fono3;
    }

    public void setFono3(Long fono3) {
        this._fono3 = fono3;
    }
    
    public PersonaDTO toDTO() {
		
		return new PersonaDTO(
    			this.getId(),
    			this.getNombreCompleto(),
    			this.getRut(),
    			this.getDireccion(),
    			this.getEmail(),
    			this.getFono1(),
    			this.getFono2(),
    			this.getFono3()
			);
    }

	public ClienteDTO toClienteDTO() {
		
		return new ClienteDTO(
    			this.getId(),
    			this.getNombreCompleto(),
    			this.getRut(),
    			this.getDireccion(),
    			this.getEmail(),
    			this.getFono1(),
    			this.getFono2(),
    			this.getFono3()
			);
    }

	public EmpleadoDTO toEmpleadoDTO() {
		
		return new EmpleadoDTO(
    			this.getId(),
    			this.getNombreCompleto(),
    			this.getRut(),
    			this.getDireccion(),
    			this.getEmail(),
    			this.getFono1(),
    			this.getFono2(),
    			this.getFono3()
			);
    }

	public ProveedorDTO toProveedorDTO() {
		
		return new ProveedorDTO(
    			this.getId(),
    			this.getNombreCompleto(),
    			this.getRut(),
    			this.getDireccion(),
    			this.getEmail(),
    			this.getFono1(),
    			this.getFono2(),
    			this.getFono3()
			);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this._id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Persona)) {
            return false;
        }
        final Persona other = (Persona) object;
        return (Objects.equals(this._id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Persona[ id=" + _id + " ]";
    }
    
}
