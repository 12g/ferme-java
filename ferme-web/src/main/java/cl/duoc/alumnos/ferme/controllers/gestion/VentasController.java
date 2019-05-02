package cl.duoc.alumnos.ferme.controllers.gestion;

import cl.duoc.alumnos.ferme.FermeParams;
import cl.duoc.alumnos.ferme.dto.VentaDTO;
import cl.duoc.alumnos.ferme.services.IFamiliasProductoService;
import cl.duoc.alumnos.ferme.services.IProductosService;
import cl.duoc.alumnos.ferme.services.IRubrosService;
import cl.duoc.alumnos.ferme.services.ITiposProductoService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Benjamin Guillermo
 */
@RestController
@RequestMapping("/api/gestion")
public class VentasController {
    
    @Autowired private ITiposProductoService tpProductoSvc;
    @Autowired private IFamiliasProductoService fmlProductoSvc;
    @Autowired private IProductosService productoSvc;
    @Autowired private IRubrosService rubroSvc;
    
    @GetMapping("/ventas/")
    public Collection<VentaDTO> getVentas() {
        throw new UnsupportedOperationException(); //TODO
    }
    
    @GetMapping("/ventas/{pageSize}/{pageIndex}")
    public Collection<VentaDTO> getProductos(
        @RequestParam Integer pageSize,
        @RequestParam Integer pageIndex
    ) {
        throw new UnsupportedOperationException(); //TODO
    }
}