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
            boolean primera = true;

            while ((linea = br.readLine()) != null) {
                if (primera) { // Saltar encabezado
                    primera = false;
                    continue;
                }

                String[] datos = linea.split(",");
                if (datos.length < 7) continue;

                int id = Integer.parseInt(datos[0].trim());
                String cedula = datos[1].trim(); // no se usa aquí
                String nombreCompleto = datos[2].trim() + " " + datos[3].trim(); // nombre + apellido
                String correo = datos[4].trim().toLowerCase();
                String contrasena = datos[5].trim();
                String tipo = datos[6].trim().toUpperCase();

                Usuario usuario = null;

                switch (tipo) {
                    case "ESTUDIANTE":
                        usuario = new Estudiante(id, nombreCompleto, correo, contrasena, ""); // Puedes agregar carrera si la tienes
                        break;
                    case "INSTRUCTOR":
                        usuario = new Instructor(id, nombreCompleto, correo, contrasena, ""); // Puedes agregar especialidad si la tienes
                        break;
                    case "ADMINISTRADOR":
                        usuario = new Administrador(id, nombreCompleto, correo, contrasena);
                        break;
                    default:
                        System.out.println("Tipo desconocido: " + tipo);
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
