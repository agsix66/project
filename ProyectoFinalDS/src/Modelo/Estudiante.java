package Modelo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Estudiante extends Usuario {
    private String carrera;

    public Estudiante() {
        super();
        setTipo(TipoUsuario.ESTUDIANTE);
    }

    public Estudiante(int id, String nombre, String email, String password, String carrera) {
        super(id, nombre, email, password, TipoUsuario.ESTUDIANTE);
        this.carrera = carrera;
    }
}
