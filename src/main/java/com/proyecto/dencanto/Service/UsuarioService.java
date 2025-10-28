package com.proyecto.dencanto.Service;

import com.proyecto.dencanto.Modelo.Usuario;
import com.proyecto.dencanto.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        // Encriptar contraseña si es nuevo usuario
        if (usuario.getId() == null) {
            String contrasenaEncriptada = passwordEncoder.encode(usuario.getContrasenaHash());
            usuario.setContrasenaHash(contrasenaEncriptada);
        } else {
            // Si está editando, mantener la contraseña actual a menos que se cambie
            Usuario usuarioExistente = obtenerPorId(usuario.getId());
            if (!usuario.getContrasenaHash().equals(usuarioExistente.getContrasenaHash())) {
                String contrasenaEncriptada = passwordEncoder.encode(usuario.getContrasenaHash());
                usuario.setContrasenaHash(contrasenaEncriptada);
            }
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        // Verificar que no sea el último admin
        if (esUltimoAdmin(id)) {
            throw new RuntimeException("No se puede eliminar el último administrador");
        }
        usuarioRepository.deleteById(id);
    }

    private boolean esUltimoAdmin(Integer idUsuarioAEliminar) {
        List<Usuario> admins = usuarioRepository.findAll().stream()
            .filter(u -> "ADMIN".equals(u.getRol().getNombre()))
            .collect(Collectors.toList());
            
        return admins.size() == 1 && admins.get(0).getId().equals(idUsuarioAEliminar);
    }

    // Método para resetear contraseña
    public void resetearPassword(Integer id) {
        Usuario usuario = obtenerPorId(id);
        if (usuario != null) {
            // Contraseña por defecto: "123456"
            String nuevaPasswordEncriptada = passwordEncoder.encode("123456");
            usuario.setContrasenaHash(nuevaPasswordEncriptada);
            usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
}