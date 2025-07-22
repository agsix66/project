package Modelo;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Curso {
    private int id;
    private String nombre;
    private String descripcion;
    private Instructor instructor;  // Asignación 1 instructor por curso

    // Relación muchos a muchos con estudiantes
    private Set<Estudiante> estudiantes;

    public Curso() {
        estudiantes = new HashSet<>();
    }

    public Curso(int id, String nombre, String descripcion, Instructor instructor) {
        this();
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.instructor = instructor;
    }

    // Métodos para manejar la relación muchos a muchos
    public boolean agregarEstudiante(Estudiante estudiante) {
        return estudiantes.add(estudiante);
    }

    public boolean eliminarEstudiante(Estudiante estudiante) {
        return estudiantes.remove(estudiante);
    }
}

