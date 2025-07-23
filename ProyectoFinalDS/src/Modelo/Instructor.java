package Modelo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Instructor extends Usuario {
    private String especialidad;

    public Instructor() {
        super();
        setTipo(TipoUsuario.INSTRUCTOR);
    }

    public Instructor(int id, String nombre, String email, String password, String especialidad) {
        super(id, nombre, email, password, TipoUsuario.INSTRUCTOR);
        this.especialidad = especialidad;
    }
}
