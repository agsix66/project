package Gestion;

import Modelo.Usuario;

import java.util.*;

public class UsuarioGestion {

    private final Map<Integer, Usuario> usuarios = new HashMap<>();
    private int idCounter = 1;

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setId(idCounter++);
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    public boolean modificarUsuario(int id, Usuario datosActualizados) {
        Usuario existente = usuarios.get(id);
        if (existente == null) return false;

        existente.setNombre(datosActualizados.getNombre());
        existente.setEmail(datosActualizados.getEmail());
        existente.setPassword(datosActualizados.getPassword());
        // Puedes agregar campos específicos según el tipo
        return true;
    }

    public Usuario buscarPorId(int id) {
        return usuarios.get(id);
    }

    public Collection<Usuario> obtenerTodos() {
        return usuarios.values();
    }

    public boolean desbloquearUsuario(int id) {
        Usuario u = usuarios.get(id);
        if (u != null && u.isBloqueado()) {
            u.desbloquear();
            return true;
        }
        return false;
    }
}
