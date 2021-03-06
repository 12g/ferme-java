package cl.duoc.alumnos.ferme.controllers;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.DetalleOrdenCompraDTO;
import cl.duoc.alumnos.ferme.dto.OrdenCompraDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IOrdenesCompraService;
import javassist.NotFoundException;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/gestion/ordenes_compra")
public class OrdenesCompraController {
    private final static Logger LOG = LoggerFactory.getLogger(OrdenesCompraController.class);
    
    @Autowired private IOrdenesCompraService ordenCompraSvc;
    
    @GetMapping("/next")
    public Integer obtenerSiguienteId() {
        LOG.info("obtenerSiguienteId");
        return ordenCompraSvc.getNextId();
    }
    
    @GetMapping("")
    public Collection<OrdenCompraDTO> obtener(
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtener sin pagina ni cantidad determinada");
        return this.obtener(null, null, allRequestParams);
    }
    
    @GetMapping("/{pageSize}")
    public Collection<OrdenCompraDTO> obtener(
        @RequestParam Integer pageSize,
        @RequestParam Map<String, String> allRequestParams
    ) {
        LOG.info("obtener sin pagina determinada");
        return this.obtener(pageSize, null, allRequestParams);
    }
    
    @GetMapping("/{pageSize}/{pageIndex}")
    public Collection<OrdenCompraDTO> obtener(
        @RequestParam Integer pageSize,
        @RequestParam Integer pageIndex,
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtener");
        
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
            filtros = ordenCompraSvc.queryParamsMapToOrdenesCompraFilteringPredicate(allRequestParams);
        }
        
        LOG.info("obtener - "+finalPageSize+" registros; página "+finalPageIndex);
        LOG.debug("obtener - Filtros solicitados: "+filtros);
        Collection<OrdenCompraDTO> ordenesCompra = ordenCompraSvc.getOrdenesCompra(finalPageSize, finalPageIndex, filtros);
        LOG.debug("obtener - ordenesCompra.size()="+ordenesCompra.size());
        LOG.info("obtener - Solicitud completa. Enviando respuesta al cliente.");
        return ordenesCompra;
    }
    
    /**
     * Obtiene los detalles de una orden de compra.
     * @param dto Un objeto DTO representando la Orden de Compra a consultar.
     * @return Una colección de objetos DTO.
     */
    @PostMapping("/detalles")
    public ResponseEntity<?> detalles(
        @RequestBody OrdenCompraDTO dto
    ) {
        LOG.info("detalles");
        
        if (dto != null && dto.getIdOrdenCompra() != null && dto.getIdOrdenCompra() != 0) {
            LOG.debug("detalles - dto="+dto);
            Collection<DetalleOrdenCompraDTO> lista = ordenCompraSvc.getDetallesOrdenCompra(dto.getIdOrdenCompra());
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } else {
            String mensaje = "La orden de compra ingresada no es válida.";
            return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Almacena una Orden de Compra nueva o actualiza una existente.
     * @param dto Un objeto DTO representando la Orden de Compra a almacenar/actualizar.
     * @return El ID de la venta.
     */
    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(
        @RequestBody OrdenCompraDTO dto
    ) {
        LOG.info("guardar");
        
        if (dto != null) {
            LOG.debug("guardar - dto="+dto);
            try {
                Integer ordenCompraId = ordenCompraSvc.saveOrdenCompra(dto);
                LOG.debug("guardar - ordenCompraId="+ordenCompraId);
                return new ResponseEntity<>(ordenCompraId, HttpStatus.OK);
            } catch (NotFoundException exc) {
                return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            String mensaje = "La orden de compra ingresada no es válida.";
            return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Elimina una Orden de Compra de la base de datos.
     * @param ordenCompraId El ID de la Orden de Compra a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping("/borrar")
    public ResponseEntity<?> borrar(
        @RequestBody Integer ordenCompraId
    ) {
        LOG.info("borrar");
        
        if (ordenCompraId != null && ordenCompraId != 0) {
            LOG.debug("borrar - ordenCompraId="+ordenCompraId);
            boolean result = ordenCompraSvc.deleteOrdenCompra(ordenCompraId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            String mensaje = "La orden de compra ingresada no es válida.";
            return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
        }
    }
}
