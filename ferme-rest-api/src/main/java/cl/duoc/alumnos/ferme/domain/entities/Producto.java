package cl.duoc.alumnos.ferme.domain.entities;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.dto.ProductoDTO;
import java.io.Serializable;
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
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;

/**
 *
 * @author Benjamin Guillermo
 */
@Entity
@Table(name = "PRODUCTO")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_PRODUCTO")
    @SequenceGenerator(name = "producto_seq", sequenceName = "SEQ_PRODUCTO", initialValue = 1, allocationSize = Ferme.DEFAULT_HIBERNATE_SEQUENCES_ALLOCATION_SIZE)
    @GeneratedValue(generator = "producto_seq", strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "CODIGO")
    private String codigo;
    
    @NonNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    
    @NonNull
    @Column(name = "STOCK_ACTUAL")
    private int stockActual;
    
    @NonNull
    @Column(name = "STOCK_CRITICO")
    private int stockCritico;
    
    @NonNull
    @Column(name = "PRECIO")
    private long precio;
    
    @Size(max = 300)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @NonNull
    @JoinColumn(name = "ID_TIPO_PRODUCTO", referencedColumnName = "ID_TIPO_PRODUCTO")
    @ManyToOne(cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    private TipoProducto tipo;

    public Producto() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getStockCritico() {
        return stockCritico;
    }

    public void setStockCritico(int stockCritico) {
        this.stockCritico = stockCritico;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    public ProductoDTO toDTO() {
        ProductoDTO dto = new ProductoDTO();
        TipoProducto tipoEntity = this.getTipo();
        FamiliaProducto familiaEntity = tipoEntity.getFamilia();
        
        dto.setIdProducto(id);
        dto.setCodigoProducto(codigo);
        dto.setNombreProducto(nombre);
        dto.setDescripcionProducto(descripcion);
        dto.setPrecioProducto(precio);
        dto.setStockActualProducto(stockActual);
        dto.setStockCriticoProducto(stockCritico);
        dto.setIdTipoProducto(tipoEntity.getId());
        dto.setNombreTipoProducto(tipoEntity.getNombre());
        dto.setIdFamiliaProducto(familiaEntity.getId());
        dto.setDescripcionFamiliaProducto(familiaEntity.getDescripcion());
        
        return dto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Producto)) {
            return false;
        }
        final Producto other = (Producto) object;
        return (Objects.equals(this.id, other.getId()));
    }
    
    

    @Override
    public String toString() {
        return "cl.duoc.alumnos.ferme.entities.domain.Producto[ id=" + id + " ]";
    }
    
}
