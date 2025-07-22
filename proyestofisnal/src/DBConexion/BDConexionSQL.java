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
            objProper.cargarPropiedades();

            if (conexion != null && !conexion.isClosed()) {
                appLogs.infoLogs("Ya existe una conexion ");
                return;
            }

            // Construir URL de conexión
            String dbUrl = construirURL(objProper);
            System.out.println("Intentando conectar a: " + dbUrl);

            // Para LocalDB o Windows Authentication
            if (esWindowsAuth(objProper)) {
                conexion = DriverManager.getConnection(dbUrl);
            } else {
                conexion = DriverManager.getConnection(dbUrl, objProper.getUsuario(), objProper.getPassword());
            }

            appLogs.infoLogs("Conexion Realizada");
            System.out.println("✅ Conexión exitosa a la base de datos!");

        } catch (SQLException ex) {
            appLogs.errorLogs(ex);
            System.err.println("❌ Error SQL: " + ex.getMessage());
            Dialogos.alertesMensaje("Error de conexión a la base de datos.\nVerifique el servidor, usuario o base configurada.", "Error SQL", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            appLogs.errorLogs(e);
            System.err.println("❌ Error general: " + e.getMessage());
            if (e.getMessage().contains("No se encontró el archivo") || e.getMessage().contains("configurado")) {
                Dialogos.alertesMensaje("Error al cargar la configuración de la base de datos.\nVerifique que el archivo dbConfig.properties existe en src/main/resources/", "Error de Configuración", JOptionPane.ERROR_MESSAGE);
            } else {
                Dialogos.alertesMensaje("Error inesperado al intentar conectar.\nComuníquese con el departamento de Tecnología.", "Error General", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String construirURL(LeerProperties objProper) {
        StringBuilder url = new StringBuilder("jdbc:sqlserver://");
        url.append(objProper.getServidor());

        // Solo agregar puerto si no está vacío
        if (objProper.getPuerto() != null && !objProper.getPuerto().trim().isEmpty()) {
            url.append(":").append(objProper.getPuerto());
        }

        url.append(";databaseName=").append(objProper.getDatabase());
        url.append(";encrypt=true;trustServerCertificate=true;");

        // Para LocalDB, agregar parámetros específicos
        if (objProper.getServidor().contains("localdb")) {
            url.append("integratedSecurity=true;");
        }

        return url.toString();
    }

    private boolean esWindowsAuth(LeerProperties objProper) {
        return (objProper.getUsuario() == null || objProper.getUsuario().trim().isEmpty()) &&
                (objProper.getPassword() == null || objProper.getPassword().trim().isEmpty());
    }

    public void desconectarBD() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    appLogs.infoLogs("Conexion Desconectada");
                    System.out.println("🔌 Conexión cerrada correctamente");
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