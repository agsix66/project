package DBConexion;

import Logs.AppLogs;
import Utileria.Dialogos;
import lombok.Getter;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class BDConexionSQL implements AutoCloseable {
    private Connection conexion;
    private final AppLogs appLogs;

    public BDConexionSQL() {
        appLogs = new AppLogs(BDConexionSQL.class);
    }

    public void conectarBD() {
        try {
            LeerProperties objProper = new LeerProperties();
            objProper.cargarPropiedades(); // Ahora maneja la excepción correctamente

            if (conexion != null && !conexion.isClosed()) {
                appLogs.infoLogs("Ya existe una conexion ");
                return;
            }

            String dbUrl = "jdbc:sqlserver://" + objProper.getServidor() + ":" + objProper.getPuerto() +
                    ";databaseName=" + objProper.getDatabase() +
                    ";encrypt=true;trustServerCertificate=true;";
            conexion = DriverManager.getConnection(dbUrl, objProper.getUsuario(), objProper.getPassword());
            appLogs.infoLogs("Conexion Realizada");

        } catch (SQLException ex) {
            appLogs.errorLogs(ex);
            Dialogos.alertesMensaje("Error de conexión a la base de datos.\nVerifique el servidor, usuario o base configurada.", "Error SQL", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            appLogs.errorLogs(e);
            // Mensaje más específico para errores de configuración
            if (e.getMessage().contains("No se encontró el archivo") || e.getMessage().contains("configurado")) {
                Dialogos.alertesMensaje("Error al cargar la configuración de la base de datos.\nVerifique que el archivo db.properties existe en src/main/resources/", "Error de Configuración", JOptionPane.ERROR_MESSAGE);
            } else {
                Dialogos.alertesMensaje("Error inesperado al intentar conectar.\nComuníquese con el departamento de Tecnología.", "Error General", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void desconectarBD() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    appLogs.infoLogs("Conexion Desconectada");
                }
            } catch (SQLException e) {
                appLogs.errorLogs(e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        desconectarBD();
    }
}