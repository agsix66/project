package Modelo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class Certificado {
    private int id;
    private Curso curso;
    private Estudiante estudiante;
    private LocalDateTime fechaEmision;
    private String codigoVerificacion;  // único
    private String rutaArchivo;         // ruta del PDF generado

    public Certificado(int id, Curso curso, Estudiante estudiante, LocalDateTime fechaEmision, String codigoVerificacion, String rutaArchivo) {
        this.id = id;
        this.curso = curso;
        this.estudiante = estudiante;
        this.fechaEmision = fechaEmision;
        this.codigoVerificacion = codigoVerificacion;
        this.rutaArchivo = rutaArchivo;
    }
}
