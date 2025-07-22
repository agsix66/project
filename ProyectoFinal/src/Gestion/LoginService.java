package Gestion;

import Modelo.Usuario;

import java.util.Map;

public class LoginService {

    private final Map<Integer, Usuario> usuarios;

    public LoginService(Map<Integer, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario login(String email, String password) {
        for (Usuario u : usuarios.values()) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                if (u.isBloqueado()) {
                    System.out.println("Usuario bloqueado.");
                    return null;
                }
                if (u.getPassword().equals(password)) {
                    u.resetearIntentos();
                    return u;
                } else {
                    u.incrementarIntentosFallidos();
                    if (u.isBloqueado()) {
                        System.out.println("Usuario bloqueado tras 3 intentos fallidos.");
                    }
                    return null;
                }
            }
        }
        return null;
    }
}
