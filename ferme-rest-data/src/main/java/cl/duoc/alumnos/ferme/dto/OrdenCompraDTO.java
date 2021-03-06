package cl.duoc.alumnos.ferme.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.duoc.alumnos.ferme.entities.DetalleOrdenCompra;
import cl.duoc.alumnos.ferme.entities.Empleado;
import cl.duoc.alumnos.ferme.entities.OrdenCompra;
import cl.duoc.alumnos.ferme.util.FormatoFechas;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
public class OrdenCompraDTO {
    @SuppressWarnings("unused")
	private final static Logger LOG = LoggerFactory.getLogger(OrdenCompraDTO.class);
    
    private Integer idOrdenCompra;
    private Integer idEmpleado;
    private String nombreEmpleado;
    private String rutEmpleado;
    private String estadoOrdenCompra;
    private String fechaSolicitudOrdenCompra;
    private String fechaRecepcionOrdenCompra;
    private List<DetalleOrdenCompraDTO> detallesOrdenCompra;
    private Integer idProveedor;

    public OrdenCompraDTO() {
        super();
    }
    
    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getRutEmpleado() {
        return rutEmpleado;
    }

    public void setRutEmpleado(String rutEmpleado) {
        this.rutEmpleado = rutEmpleado;
    }

    public String getEstadoOrdenCompra() {
        return estadoOrdenCompra;
    }

    public void setEstadoOrdenCompra(String estadoOrdenCompra) {
        this.estadoOrdenCompra = estadoOrdenCompra;
    }

    public String getFechaSolicitudOrdenCompra() {
        return fechaSolicitudOrdenCompra;
    }

    public void setFechaSolicitudOrdenCompra(String fechaSolicitudOrdenCompra) {
        this.fechaSolicitudOrdenCompra = fechaSolicitudOrdenCompra;
    }

    public String getFechaRecepcionOrdenCompra() {
        return fechaRecepcionOrdenCompra;
    }

    public void setFechaRecepcionOrdenCompra(String fechaRecepcionOrdenCompra) {
        this.fechaRecepcionOrdenCompra = fechaRecepcionOrdenCompra;
    }

    public List<DetalleOrdenCompraDTO> getDetallesOrdenCompra() {
        return detallesOrdenCompra;
    }

    public void setDetallesOrdenCompra(List<DetalleOrdenCompraDTO> detallesOrdenCompra) {
        this.detallesOrdenCompra = detallesOrdenCompra;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    public OrdenCompra toEntity() {
        OrdenCompra entity = new OrdenCompra();
        if (idOrdenCompra != null && idOrdenCompra != 0) {
            entity.setId(idOrdenCompra);
        }
        
        if (estadoOrdenCompra != null && !estadoOrdenCompra.isEmpty()) {
            entity.setEstado(estadoOrdenCompra.charAt(0));
        }
        
        if (fechaSolicitudOrdenCompra != null && !fechaSolicitudOrdenCompra.isEmpty()) {
            Date fSolicitud = FormatoFechas.stringADateLocal(fechaSolicitudOrdenCompra);
            entity.setFechaSolicitud(fSolicitud);
        }
        
        if (fechaRecepcionOrdenCompra != null && !fechaRecepcionOrdenCompra.isEmpty()) {
            Date fRecepcion = FormatoFechas.stringADateLocal(fechaRecepcionOrdenCompra);
            entity.setFechaRecepcion(fRecepcion);
        }
        
        Empleado empleadoEntity = new Empleado();
        empleadoEntity.setId(idEmpleado);
        entity.setEmpleado(empleadoEntity);
        
        List<DetalleOrdenCompra> _detallesEntities = this.detallesToEntity();
        _detallesEntities.forEach((dtl) -> {dtl.setOrdenCompra(entity);});
        entity.setDetalles(_detallesEntities);
        
        return entity;
    }

    private List<DetalleOrdenCompra> detallesToEntity() {
        List<DetalleOrdenCompra> detallesEntities = new ArrayList<>();
        if (detallesOrdenCompra != null && !detallesOrdenCompra.isEmpty()) {
            for (DetalleOrdenCompraDTO detalle : detallesOrdenCompra) {
                DetalleOrdenCompra detalleEntity = detalle.toEntity();
                detallesEntities.add(detalleEntity);
            }
        }
        return detallesEntities;
    }

    @Override
    public String toString() {
        return "OrdenCompraDTO{" + "idOrdenCompra=" + idOrdenCompra + ", idEmpleado=" + idEmpleado + ", estadoOrdenCompra=" + estadoOrdenCompra + ", fechaSolicitudOrdenCompra=" + fechaSolicitudOrdenCompra + ", fechaRecepcionOrdenCompra=" + fechaRecepcionOrdenCompra + ", detallesOrdenCompra=" + detallesOrdenCompra + '}';
    }
    
}
