package Gestion;

import Modelo.Curso;
import Modelo.Estudiante;
import Modelo.Instructor;

import java.util.*;

public class CursoGestion {

    private final Map<Integer, Curso> cursos = new HashMap<>();
    private int idCounter = 1;

    // Crear nuevo curso
    public Curso crearCurso(String nombre, String descripcion, Instructor instructor) {
        Curso curso = new Curso(idCounter++, nombre, descripcion, instructor);
        cursos.put(curso.getId(), curso);
        return curso;
    }

    // Modificar curso existente
    public boolean modificarCurso(int id, String nombre, String descripcion, Instructor instructor) {
        Curso curso = cursos.get(id);
        if (curso == null) return false;

        curso.setNombre(nombre);
        curso.setDescripcion(descripcion);
        curso.setInstructor(instructor);
        return true;
    }

    // Obtener curso por id
    public Curso obtenerCurso(int id) {
        return cursos.get(id);
    }

    // Listar todos los cursos
    public Collection<Curso> obtenerTodos() {
        return cursos.values();
    }

    // Relación muchos a muchos: agregar estudiante a curso
    public boolean agregarEstudianteACurso(int idCurso, Estudiante estudiante) {
        Curso curso = cursos.get(idCurso);
        if (curso == null) return false;

        return curso.agregarEstudiante(estudiante);
    }

    // Remover estudiante de curso
    public boolean eliminarEstudianteDeCurso(int idCurso, Estudiante estudiante) {
        Curso curso = cursos.get(idCurso);
        if (curso == null) return false;

        return curso.eliminarEstudiante(estudiante);
    }
}
