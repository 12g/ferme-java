package cl.duoc.alumnos.ferme.controllers;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import cl.duoc.alumnos.ferme.FermeConfig;
import cl.duoc.alumnos.ferme.dto.PersonaDTO;
import cl.duoc.alumnos.ferme.services.interfaces.IPersonasService;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping(FermeConfig.URI_BASE_REST_API+"/gestion/personas")
public class PersonasController {
    private final static Logger LOG = LoggerFactory.getLogger(PersonasController.class);
    
    @Autowired private IPersonasService personaSvc;    
    
    @GetMapping("")
    public Collection<PersonaDTO> obtener(
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtener sin pagina ni cantidad determinada");
        return this.obtener(null, null, allRequestParams);
    }
    
    @GetMapping("/{pageSize}")
    public Collection<PersonaDTO> obtener(
        @PathVariable Integer pageSize,
        @RequestParam Map<String,String> allRequestParams
    ) {
        LOG.info("obtener sin pagina determinada");
        return this.obtener(pageSize, null, allRequestParams);
    }
    
    /**
     * Busca todos los personas. 
     * Si el URL tenía un query string (RequestParam, lo transforma a un Map, genera un Predicate a partir
     * de él y filtra la búsqueda con este Predicate.
     * @param pageSize
     * @param pageIndex
     * @param allRequestParams Un Map conteniendo una colección pares nombre/valor.
     * @see RequestParam
     * @see Predicate
     * @return Una colección de objetos PersonaDTO
     */
    @GetMapping("/{pageSize}/{pageIndex}")
    public Collection<PersonaDTO> obtener(
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
            filtros = this.personaSvc.queryParamsMapToPersonasFilteringPredicate(allRequestParams);
        }        
        
        LOG.info("obtener - "+finalPageSize+" registros; página "+finalPageIndex);
        LOG.debug("obtener - Filtros solicitados: "+filtros);
        Collection<PersonaDTO> personas = personaSvc.getPersonas(finalPageSize, finalPageIndex, filtros);
        LOG.debug("obtener - personas.size()="+personas.size());
        LOG.info("obtener - Solicitud completa. Enviando respuesta al cliente.");
        return personas;
    }
}
