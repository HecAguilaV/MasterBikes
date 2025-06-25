package masterbikes.repository;

import masterbikes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Necesario para UserDetailsService
    Optional<Usuario> findByEmail(String email);
}
