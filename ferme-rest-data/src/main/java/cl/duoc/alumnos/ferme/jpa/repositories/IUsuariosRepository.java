package cl.duoc.alumnos.ferme.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import cl.duoc.alumnos.ferme.entities.Usuario;

/**
 *
 * @author Benjamin Guillermo <got12g at gmail.com>
 */
@Component
@Repository
public interface IUsuariosRepository extends JpaRepository<Usuario, Integer>, QuerydslPredicateExecutor<Usuario> {
    
}
