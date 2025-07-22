package Gestion;

import Modelo.Curso;
import Modelo.Estudiante;
import Modelo.Evaluacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EvaluacionGestion {
    private final List<Evaluacion> evaluaciones = new ArrayList<>();

    public void registrarNota(Curso curso, Estudiante estudiante, double nota) {
        Optional<Evaluacion> existente = evaluaciones.stream()
                .filter(e -> e.getCurso().equals(curso) && e.getEstudiante().equals(estudiante))
                .findFirst();
        if (existente.isPresent()) {
            existente.get().setNota(nota);
        } else {
            evaluaciones.add(new Evaluacion(curso, estudiante, nota));
        }
    }

    public double obtenerNota(Curso curso, Estudiante estudiante) {
        return evaluaciones.stream()
                .filter(e -> e.getCurso().equals(curso) && e.getEstudiante().equals(estudiante))
                .map(Evaluacion::getNota)
                .findFirst()
                .orElse(0.0);
    }

    public boolean validarRequisitosCertificado(Curso curso, Estudiante estudiante,
                                                AsistenciaGestion asistenciaGestion,
                                                double minAsistencia, double minNota) {
        double porcentajeAsistencia = asistenciaGestion.calcularPorcentajeAsistencia(curso, estudiante);
        double nota = obtenerNota(curso, estudiante);

        return porcentajeAsistencia >= minAsistencia && nota >= minNota;
    }
}

