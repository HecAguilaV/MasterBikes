package masterbikes.service;

// Servicio de negocio para gestión de usuarios.
// Encapsula la lógica de acceso a datos y reglas de negocio para usuarios.
import lombok.RequiredArgsConstructor;
import masterbikes.model.Usuario;
import masterbikes.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Lista todos los usuarios registrados.
     */
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     * @param id identificador
     * @return Optional<Usuario> si existe
     */
    public Optional<Usuario> obtenerUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Crea un usuario nuevo, encriptando la contraseña antes de guardar.
     * @param usuario datos del usuario
     * @return usuario creado
     */
    public Usuario crearUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> actualizarUsuario(Long id, Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(u -> {
                    u.setEmail(usuario.getEmail());
                    u.setNombre(usuario.getNombre());
                    u.setPassword(usuario.getPassword());
                    u.setRol(usuario.getRol());
                    u.setSucursal(usuario.getSucursal());
                    u.setActivo(usuario.isActivo());
                    return usuarioRepository.save(u);
                });
    }

    /**
     * Realiza un borrado lógico del usuario, cambiando su estado a inactivo.
     * @param id el ID del usuario a desactivar.
     * @return true si el usuario fue encontrado y desactivado, false en caso contrario.
     */
    public boolean eliminarUsuario(Long id) {
        return obtenerUsuario(id).map(usuario -> {
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
            return true;
        }).orElse(false);
    }
}
