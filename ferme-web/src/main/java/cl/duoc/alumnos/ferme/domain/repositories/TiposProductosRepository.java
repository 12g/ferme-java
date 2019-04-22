package cl.duoc.alumnos.ferme.domain.repositories;

import cl.duoc.alumnos.ferme.domain.entities.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Benjamin Guillermo La Madrid <got12g at gmail.com>
 */
@Component
@Repository
public interface TiposProductosRepository extends JpaRepository<TipoProducto, Integer> {
    
}
