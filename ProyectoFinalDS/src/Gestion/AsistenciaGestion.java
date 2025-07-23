package Gestion;

import Modelo.Asistencia;
import Modelo.Curso;
import Modelo.Estudiante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaGestion {
    private final List<Asistencia> asistencias = new ArrayList<>();

    public void registrarAsistencia(Curso curso, Estudiante estudiante, LocalDate fecha, boolean presente) {
        asistencias.add(new Asistencia(curso, estudiante, fecha, presente));
    }

    public long obtenerAsistenciasPresentes(Curso curso, Estudiante estudiante) {
        return asistencias.stream()
                .filter(a -> a.getCurso().equals(curso) && a.getEstudiante().equals(estudiante) && a.isPresente())
                .count();
    }

    public long obtenerTotalAsistenciasCurso(Curso curso) {
        return asistencias.stream()
                .filter(a -> a.getCurso().equals(curso))
                .map(Asistencia::getFecha)
                .distinct()
                .count();
    }

    public double calcularPorcentajeAsistencia(Curso curso, Estudiante estudiante) {
        long totalDias = obtenerTotalAsistenciasCurso(curso);
        if (totalDias == 0) return 0.0;
        long presentes = obtenerAsistenciasPresentes(curso, estudiante);
        return (presentes * 100.0) / totalDias;
    }
}
