package masterbikes.controller;

import lombok.RequiredArgsConstructor;
import masterbikes.dto.LoginRequest;
import masterbikes.dto.LoginResponse;
import masterbikes.model.Usuario;
import masterbikes.repository.UsuarioRepository;
import masterbikes.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(request.getEmail()))
                .findFirst().orElse(null);
        if (usuario == null || !passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas. Verifica tu email y contraseña."));
        }
        String token = jwtUtil.generateToken(usuario.getEmail(), Map.of(
                "rol", usuario.getRol().name(),
                "sucursal", usuario.getSucursal()
        ));
        return ResponseEntity.ok(new LoginResponse(token, usuario.getRol().name(), "Login exitoso."));
    }
}
