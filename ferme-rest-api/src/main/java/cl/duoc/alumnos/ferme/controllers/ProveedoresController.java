package cl.duoc.alumnos.ferme.controllers;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.ProveedorDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IProveedoresService;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/gestion/proveedores")
public class ProveedoresController {
    private final static Logger LOG = LoggerFactory.getLogger(ProveedoresController.class);
    
    @Autowired private IProveedoresService proveedorSvc;
    
    @GetMapping("")
    public Collection<ProveedorDTO> getProveedores(
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtener sin pagina ni cantidad determinada");        
        return this.getProveedores(null, null, allRequestParams);
    }
    
    @GetMapping("/{pageSize}")
    public Collection<ProveedorDTO> getProveedores(
        @PathVariable Integer pageSize,
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtener sin pagina determinada");
        return this.getProveedores(pageSize, null, allRequestParams);
    }
    
    /**
     * Busca todos los proveedores. 
     * Si el URL tenía un query string (RequestParam, lo transforma a un Map, genera un Predicate a partir
     * de él y filtra la búsqueda con este Predicate.
     * @param pageSize
     * @param pageIndex
     * @param allRequestParams Un Map conteniendo una colección pares nombre/valor.
     * @see RequestParam
     * @see Predicate
     * @return Una colección de objetos ProveedorDTO
     */
    @GetMapping("/{pageSize}/{pageIndex}")
    public Collection<ProveedorDTO> getProveedores(
        @PathVariable Integer pageSize,
        @PathVariable Integer pageIndex,
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
            filtros = this.proveedorSvc.queryParamsMapToProveedoresFilteringPredicate(allRequestParams);
        }
        
        LOG.info("obtener - "+finalPageSize+" registros; página "+finalPageIndex);
        LOG.debug("obtener - Filtros solicitados: "+filtros);
        Collection<ProveedorDTO> proveedores = this.proveedorSvc.getProveedores(finalPageSize, finalPageIndex, filtros);
        LOG.debug("obtener - proveedores.size()="+proveedores.size());
        LOG.info("obtener - Solicitud completa. Enviando respuesta al cliente.");
        return proveedores;
    }
    
    /**
     * Almacena un Rubro nuevo o actualiza uno existente.
     * @param dto Un objeto DTO representando el Rubro a almacenar/actualizar.
     * @return El ID del rubro.
     */
    @PostMapping("/guardar")
    public Integer guardar(
        @RequestBody ProveedorDTO dto
    ) {
        LOG.info("guardar");
        if (dto != null) {
            LOG.debug("guardar - dto="+dto);
            Integer proveedorId = proveedorSvc.saveProveedor(dto);
            LOG.debug("guardar - proveedorId="+proveedorId);
            return proveedorId;
        }
        return null;
    }
    
    /**
     * Elimina un Rubro de la base de datos.
     * @param proveedorId El ID del Rubro a eliminar.
     * @return true si la operación fue exitosa, false si no lo fue.
     */
    @PostMapping("/borrar")
    public boolean borrar(
        @RequestBody Integer proveedorId
    ) {
        LOG.info("borrar");
        if (proveedorId != null && proveedorId != 0) {
            LOG.debug("borrar - proveedorId="+proveedorId);
            return proveedorSvc.deleteProveedor(proveedorId);
        }
        return false;
    }
}
