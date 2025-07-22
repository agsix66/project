package Gestion;

import Modelo.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CargaMasivaUsuarios {

    public List<Usuario> cargarDesdeCSV(String rutaArchivo) throws Exception {
        List<Usuario> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 4) continue;

                String tipo = datos[0].toUpperCase();
                String nombre = datos[1];
                String email = datos[2];
                String password = datos[3];

                Usuario usuario = null;
                switch (tipo) {
                    case "ESTUDIANTE":
                        String carrera = datos.length > 4 ? datos[4] : "";
                        usuario = new Estudiante(0, nombre, email, password, carrera);
                        break;
                    case "INSTRUCTOR":
                        String especialidad = datos.length > 4 ? datos[4] : "";
                        usuario = new Instructor(0, nombre, email, password, especialidad);
                        break;
                    case "ADMINISTRADOR":
                        usuario = new Administrador(0, nombre, email, password);
                        break;
                }
                if (usuario != null) {
                    lista.add(usuario);
                }
            }
        }
        return lista;
    }
}
