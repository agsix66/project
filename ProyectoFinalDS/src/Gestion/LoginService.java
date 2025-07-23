package Gestion;

import Modelo.Usuario;
import java.util.Map;

public class LoginService {
    private final Map<String, Usuario> usuarios;

    public LoginService(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario login(String email, String password) {
        Usuario usuario = usuarios.get(email.toLowerCase());
        if (usuario != null && !usuario.isBloqueado() && usuario.getPassword().equals(password)) {
            usuario.resetearIntentos();
            return usuario;
        } else if (usuario != null) {
            usuario.incrementarIntentosFallidos();
        }
        return null;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }
}
