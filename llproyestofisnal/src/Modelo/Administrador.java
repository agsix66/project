package Modelo;

public class Administrador extends Usuario {

    public Administrador() {
        super();
        setTipo(TipoUsuario.ADMINISTRADOR);
    }

    public Administrador(int id, String nombre, String email, String password) {
        super(id, nombre, email, password, TipoUsuario.ADMINISTRADOR);
    }
}

