package cl.duoc.alumnos.ferme.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.duoc.alumnos.ferme.Globals;
import cl.duoc.alumnos.ferme.dto.SesionDTO;

/** Representa un registro de la tabla SESION.
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Entity
@Table(name = "SESION")
@NamedQueries({
    @NamedQuery(name = "Sesion.findAll", query = "SELECT u FROM Sesion u")})
public class Sesion implements Serializable {
    @SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(Sesion.class);
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_SESION")
    @SequenceGenerator(name = "sesion_seq", sequenceName = "SEQ_SESION", initialValue = 1, allocationSize = Globals.ESPACIO_ASIGNACION_SECUENCIAS_HIBERNATE)
    @GeneratedValue(generator = "sesion_seq", strategy = GenerationType.AUTO)
    private Integer _id;
    
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = true, updatable = true)
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private Usuario _usuario;
    
    @Column(name = "ABIERTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date _abierta;
    
    @Column(name = "HASH")
    private String _hash;
    
    @Column(name = "CERRADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date _cerrada;

    public Sesion() {
        super();
    }

    public Sesion(Integer id) {
        super();
        this._id = id;
    }

    public Integer getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public Usuario getUsuario() {
        return _usuario;
    }

    public void setUsuario(Usuario usuario) {
        this._usuario = usuario;
    }

    public Date getAbierta() {
        return _abierta;
    }

    public void setAbierta(Date abierta) {
        this._abierta = abierta;
    }

    public String getHash() {
        return _hash;
    }

    public void setHash(String hash) {
        this._hash = hash;
    }

    public Date getCerrada() {
        return _cerrada;
    }

    public void setCerrada(Date cerrada) {
        this._cerrada = cerrada;
    }

    public SesionDTO toDTO() {
        SesionDTO dto = new SesionDTO();
        Usuario usuarioEntity = getUsuario();
        dto.setHashSesion(_hash);
        dto.setIdUsuario(usuarioEntity.getId());
        dto.setNombreUsuario(usuarioEntity.getNombre());
        
        Persona personaEntity = usuarioEntity.getPersona();
        dto.setIdPersona(personaEntity.getId());
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this._id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sesion)) {
            return false;
        }
        final Sesion other = (Sesion) object;
        return (!Objects.equals(this._id, other.getId()));
    }

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Sesion[ idUsuario=" + _id + " ]";
    }
    
}
