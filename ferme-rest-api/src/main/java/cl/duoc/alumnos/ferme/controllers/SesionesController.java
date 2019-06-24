package cl.duoc.alumnos.ferme.controllers;

import cl.duoc.alumnos.ferme.dto.SesionDTO;
import cl.duoc.alumnos.ferme.dto.UsuarioDTO;
import cl.duoc.alumnos.ferme.pojo.LoginPOJO;
import cl.duoc.alumnos.ferme.services.interfaces.ISesionesService;
import cl.duoc.alumnos.ferme.services.interfaces.IUsuariosService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@RestController
@RequestMapping("/api/sesiones")
public class SesionesController {
    private final static Logger LOG = LoggerFactory.getLogger(SesionesController.class);
    
    @Autowired private ISesionesService sesionSvc;
    @Autowired private IUsuariosService usuarioSvc;
    
    /**
     * Almacena una Sesion nueva o actualiza una existente.
     * @param login Un JSON parseado a un objeto DTO que contiene las credenciales a almacenar/actualizar.
     * @return El ID de la sesion.
     * @throws NotFoundException Si algún ID referenciado no existe en la base de datos
     */
    @PostMapping("/abrir")
    public ResponseEntity<?> abrirSesion(@RequestBody LoginPOJO login) throws NotFoundException {
        LOG.info("abrirSesion");
        if (login != null) {
            LOG.debug("abrirSesion - login="+login);
            LOG.info("abrirSesion - Autenticando usuario desde credenciales...");
            UsuarioDTO usuario = usuarioSvc.getUsuarioFromCredentials(login.usuario, login.clave);
            if (usuario != null) {
                LOG.info("abrirSesion - Las credenciales del usuario fueron autenticadas, abriendo sesion...");
                SesionDTO sesion = sesionSvc.abrirSesion(usuario);
                LOG.debug("abrirSesion - Se abrio una sesion con id "+sesion.getIdSesion());
                LOG.info("abrirSesion - Transaccion completada correctamente");
                return ResponseEntity.ok(sesion);
            } else {
                LOG.info("abrirSesion - Las credenciales ingresadas no pudieron pasar la autenticacion");
                return ResponseEntity.noContent().build();
            }
        }
        LOG.info("abrirSesion - Las credenciales ingresadas no son validas");
        return ResponseEntity.badRequest().body(null);
    }
    
    /**
     * Almacena una Sesion nueva o actualiza una existente.
     * @param dto Un objeto DTO representando la Sesion a almacenar/actualizar.
     * @return El ID de la sesion.
     */
    @PostMapping("/validar")
    public boolean validarSesion(@RequestBody SesionDTO dto) {
        LOG.info("validarSesion");
        if (dto != null) {
            return sesionSvc.validarSesion(dto);
        }
        return false;
    }
    
    /**
     * Elimina una Sesion de la base de datos.
     * @param sesion El DTO de la Sesion a cerrar.
     * @return Siempre devuelve true, por motivos de seguridad.
     */
    @PostMapping("/cerrar")
    public boolean cerrarSesion(@RequestBody SesionDTO sesion) {
        LOG.info("cerrarSesion");
        if (sesion != null) {
            sesionSvc.cerrarSesiones(sesion);
        }
        return true;
    }
}
