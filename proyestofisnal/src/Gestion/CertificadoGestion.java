package Gestion;

import Modelo.Certificado;
import Modelo.Curso;
import Modelo.Estudiante;
import Utileria.PDFGenerator;
import Utileria.EmailSender;

import java.time.LocalDateTime;
import java.util.*;

public class CertificadoGestion {
    private final List<Certificado> certificados = new ArrayList<>();
    private int idCounter = 1;

    public Certificado emitirCertificado(Curso curso, Estudiante estudiante) {
        String codigoVerificacion = UUID.randomUUID().toString();

        String rutaArchivo = PDFGenerator.generarCertificadoPDF(estudiante, curso, codigoVerificacion);

        Certificado certificado = new Certificado(
                idCounter++,
                curso,
                estudiante,
                LocalDateTime.now(),
                codigoVerificacion,
                rutaArchivo
        );

        certificados.add(certificado);

        EmailSender.enviarCertificado(estudiante.getEmail(), rutaArchivo);

        return certificado;
    }

    public Certificado buscarPorCodigo(String codigo) {
        return certificados.stream()
                .filter(c -> c.getCodigoVerificacion().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Certificado> obtenerTodos() {
        return certificados;
    }
}
