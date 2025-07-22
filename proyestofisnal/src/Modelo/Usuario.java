package Modelo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String password;
    private TipoUsuario tipo; // ENUM para roles
    private boolean bloqueado;
    private int intentosFallidos;
    private LocalDateTime fechaRegistro;

    public enum TipoUsuario {
        ESTUDIANTE,
        INSTRUCTOR,
        ADMINISTRADOR
    }

    public Usuario() {
        this.bloqueado = false;
        this.intentosFallidos = 0;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Usuario(int id, String nombre, String email, String password, TipoUsuario tipo) {
        this();
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.tipo = tipo;
    }

    public void incrementarIntentosFallidos() {
        this.intentosFallidos++;
        if (this.intentosFallidos >= 3) {
            this.bloqueado = true;
        }
    }

    public void resetearIntentos() {
        this.intentosFallidos = 0;
    }

    public void desbloquear() {
        this.bloqueado = false;
        resetearIntentos();
    }
}

