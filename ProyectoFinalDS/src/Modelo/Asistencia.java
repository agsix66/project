package Modelo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Asistencia {
    private Curso curso;
    private Estudiante estudiante;
    private LocalDate fecha;
    private boolean presente;

    public Asistencia(Curso curso, Estudiante estudiante, LocalDate fecha, boolean presente) {
        this.curso = curso;
        this.estudiante = estudiante;
        this.fecha = fecha;
        this.presente = presente;
    }
}
