package Modelo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Evaluacion {
    private Curso curso;
    private Estudiante estudiante;
    private double nota;

    public Evaluacion(Curso curso, Estudiante estudiante, double nota) {
        this.curso = curso;
        this.estudiante = estudiante;
        this.nota = nota;
    }
}
