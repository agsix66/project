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
        appLogs.infoLogs("Inicializando BDConexionSQL");
    }

    public void conectarBD() {
        try {
            appLogs.infoLogs("Iniciando proceso de conexión a la base de datos");

            LeerProperties objProper = new LeerProperties();
            objProper.cargarPropiedades();
            appLogs.infoLogs("Propiedades de conexión cargadas correctamente");

            if (conexion != null && !conexion.isClosed()) {
                appLogs.infoLogs("Ya existe una conexión activa");
                System.out.println("⚠️  Ya existe una conexión activa");
                return;
            }

            // Construir URL de conexión
            String dbUrl = construirURL(objProper);
            appLogs.infoLogs("URL de conexión construida: " + ocultarCredenciales(dbUrl));
            System.out.println("🔗 Intentando conectar a: " + ocultarCredenciales(dbUrl));

            // Para LocalDB o Windows Authentication
            if (esWindowsAuth(objProper)) {
                appLogs.infoLogs("Conectando con autenticación de Windows");
                System.out.println("🔑 Usando autenticación de Windows");
                conexion = DriverManager.getConnection(dbUrl);
            } else {
                appLogs.infoLogs("Conectando con usuario y contraseña");
                System.out.println("🔑 Usando autenticación SQL Server");
                conexion = DriverManager.getConnection(dbUrl, objProper.getUsuario(), objProper.getPassword());
            }

            appLogs.infoLogs("Conexión establecida correctamente");
            System.out.println("✅ ¡Conexión exitosa a la base de datos!");

        } catch (SQLException ex) {
            appLogs.errorLogs(ex);
            System.err.println("❌ Error SQL: " + ex.getMessage());
            System.err.println("   Código de error: " + ex.getErrorCode());
            System.err.println("   Estado SQL: " + ex.getSQLState());

            String mensajeError = construirMensajeErrorSQL(ex);
            Dialogos.alertesMensaje(mensajeError, "Error SQL", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            appLogs.errorLogs(e);
            System.err.println("❌ Error general: " + e.getMessage());

            String mensajeError;
            if (e.getMessage().contains("No se encontró el archivo") || e.getMessage().contains("configurado")) {
                mensajeError = "Error al cargar la configuración de la base de datos.\nVerifique que el archivo dbConfig.properties existe en src/main/resources/";
            } else {
                mensajeError = "Error inesperado al intentar conectar.\nDetalles: " + e.getMessage();
            }

            Dialogos.alertesMensaje(mensajeError, "Error de Configuración", JOptionPane.ERROR_MESSAGE);
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
        url.append("loginTimeout=10;"); // Timeout de 10 segundos

        // Para LocalDB, agregar parámetros específicos
        if (objProper.getServidor().contains("localdb") || objProper.getServidor().contains("LocalDB")) {
            url.append("integratedSecurity=true;");
            appLogs.infoLogs("Configuración para LocalDB detectada");
        }

        return url.toString();
    }

    private boolean esWindowsAuth(LeerProperties objProper) {
        return (objProper.getUsuario() == null || objProper.getUsuario().trim().isEmpty()) &&
                (objProper.getPassword() == null || objProper.getPassword().trim().isEmpty());
    }

    private String ocultarCredenciales(String url) {
        // Ocultar información sensible en los logs
        return url.replaceAll("password=([^;]+)", "password=***");
    }

    private String construirMensajeErrorSQL(SQLException ex) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Error de conexión a la base de datos:\n");

        switch (ex.getErrorCode()) {
            case 2:
                mensaje.append("• El servidor no está disponible o el nombre es incorrecto\n");
                mensaje.append("• Verifique que SQL Server esté ejecutándose\n");
                break;
            case 18456:
                mensaje.append("• Error de autenticación\n");
                mensaje.append("• Verifique usuario y contraseña\n");
                break;
            case 4060:
                mensaje.append("• La base de datos especificada no existe\n");
                mensaje.append("• Verifique el nombre de la base de datos\n");
                break;
            default:
                mensaje.append("• Código de error: ").append(ex.getErrorCode()).append("\n");
                mensaje.append("• Mensaje: ").append(ex.getMessage()).append("\n");
        }

        return mensaje.toString();
    }

    public void desconectarBD() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    appLogs.infoLogs("Conexión cerrada correctamente");
                    System.out.println("🔌 Conexión cerrada correctamente");
                } else {
                    appLogs.infoLogs("La conexión ya estaba cerrada");
                }
            } catch (SQLException e) {
                appLogs.errorLogs(e);
                System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
            }
        } else {
            appLogs.infoLogs("No hay conexión para cerrar");
        }
    }

    public boolean isConectado() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            appLogs.errorLogs(e);
            return false;
        }
    }

    @Override
    public void close() throws Exception {
        appLogs.infoLogs("Cerrando BDConexionSQL mediante AutoCloseable");
        desconectarBD();
    }
}