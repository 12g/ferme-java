package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.Ferme;
import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.DetalleVentaDTO;
import cl.duoc.alumnos.ferme.dto.VentaDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IVentasService;
import com.querydsl.core.types.Predicate;
import java.util.Collection;
import java.util.Map;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/gestion/ventas")
public class VentasController {
    private final static Logger LOG = LoggerFactory.getLogger(VentasController.class);
    
    @Autowired private IVentasService ventaSvc;
    
    @GetMapping("/next")
    public Integer obtenerSiguienteId() {
        return ventaSvc.getNextId();
    }
    
    @GetMapping("")
    public Collection<VentaDTO> getVentas(
        @RequestParam Map<String,String> allRequestParams
    ) {
        return this.getVentas(null, null, allRequestParams);
    }
    
    @GetMapping("/{pageSize}")
    public Collection<VentaDTO> getVentas(
        @RequestParam Integer pageSize,
        @RequestParam Map<String, String> allRequestParams
    ) {
        return this.getVentas(pageSize, null, allRequestParams);
    }
    
    @GetMapping("/{pageSize}/{pageIndex}")
    public Collection<VentaDTO> getVentas(
        @RequestParam Integer pageSize,
        @RequestParam Integer pageIndex,
        @RequestParam Map<String,String> allRequestParams
    ) {
        Integer finalPageSize = FermeConfig.PAGINACION_REGISTROS_POR_PAGINA_INICIAL;
        Integer finalPageIndex = FermeConfig.PAGINACION_INDICE_INICIAL;
        Predicate filtros = null;
        
        if (pageSize != null && pageSize > 0) {
            finalPageSize = pageSize;
        }
        if (pageIndex != null && pageIndex > 0) {
            finalPageIndex = pageIndex-1;
        }
        if (allRequestParams != null && !allRequestParams.isEmpty()) {
            filtros = ventaSvc.queryParamsMapToVentasFilteringPredicate(allRequestParams);
        }
        
        LOG.info("getVentas - "+finalPageSize+" registros; página "+finalPageIndex);
        LOG.debug("getVentas - Filtros solicitados: "+filtros);
        Collection<VentaDTO> ventas = this.ventaSvc.getVentas(finalPageSize, finalPageIndex, filtros);
        LOG.debug("getVentas - ventas.size()="+ventas.size());
        LOG.info("getVentas - Solicitud completa. Enviando respuesta al cliente.");
        return ventas;
    }
    
    /**
     * Obtiene los detalles de una orden de compra.
     * @param dto Un objeto DTO representando la Orden de Compra a consultar.
     * @return Una colección de objetos DTO.
     */
    @PostMapping("/detalles")
    public Collection<DetalleVentaDTO> getDetallesVenta(@RequestBody VentaDTO dto) {
        
        if (dto != null && dto.getIdVenta() != null && dto.getIdVenta() != 0) {
            LOG.debug("getDetallesVenta - dto="+dto.toString());
            return ventaSvc.getDetallesVenta(dto.getIdVenta());
        }
        return null;
    }
    
    /**
     * Almacena una Venta nueva o actualiza una existente.
     * @param dto Un objeto DTO representando la Venta a almacenar/actualizar.
     * @return El ID de la venta.
     */
    @PostMapping("/guardar")
    public Integer saveVenta(@RequestBody VentaDTO dto) throws NotFoundException {
        
        if (dto != null) {
            LOG.debug("saveVenta - dto="+dto);
            Integer ventaId = ventaSvc.saveVenta(dto);
            LOG.debug("saveVenta - ventaId="+ventaId);
            return ventaId;
        }
        return null;
    }
    
    /**
     * Elimina una Venta de la base de datos.
     * @param ventaId El ID de la Venta a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping("/borrar")
    public boolean deleteVenta(@RequestBody Integer ventaId) {
        
        if (ventaId != null && ventaId != 0) {
            LOG.debug("deleteVenta - clienteId="+ventaId);
            return ventaSvc.deleteVenta(ventaId);
        }
        return false;
    }
}
