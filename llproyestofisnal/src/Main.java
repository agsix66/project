import DBConexion.BDConexionSQL;
import Logs.AppLogs;

import java.sql.SQLException;
import java.io.File;

public class Main {
    private static final AppLogs appLogs = new AppLogs(Main.class);

    public static void main(String[] args) {
        // Configurar codificación para consola
        System.setProperty("file.encoding", "UTF-8");

        System.out.println("=== INICIANDO APLICACIÓN ===");
        appLogs.infoLogs("Aplicación iniciada");

        // Crear directorio de logs si no existe
        crearDirectorioLogs();

        try (BDConexionSQL con = new BDConexionSQL()) {
            System.out.println("🚀 Intentando conectar a la base de datos...");
            appLogs.infoLogs("Iniciando proceso de conexión a BD");

            con.conectarBD();

            // Verificar si la conexión fue exitosa
            if (con.isConectado()) {
                System.out.println("✅ ¡Conexión exitosa a la base de datos!");
                appLogs.infoLogs("Conexión a BD establecida exitosamente");

                // Mostrar información de la conexión
                mostrarInformacionConexion(con);

                // Aquí puedes agregar más lógica de tu aplicación
                // pruebasBasicas(con);

            } else {
                System.out.println("❌ No se pudo establecer la conexión.");
                appLogs.infoLogs("Fallo al establecer conexión a BD");
            }

            System.out.println("🔄 Cerrando conexión...");
            appLogs.infoLogs("Cerrando conexión a BD");

        } catch (SQLException e) {
            System.out.println("❌ Error SQL al conectarse a la base de datos:");
            System.out.println("   Mensaje: " + e.getMessage());
            System.out.println("   Código: " + e.getErrorCode());
            System.out.println("   Estado SQL: " + e.getSQLState());
            appLogs.errorLogs(e);

        } catch (Exception e) {
            System.out.println("❌ Error general:");
            System.out.println("   Tipo: " + e.getClass().getSimpleName());
            System.out.println("   Mensaje: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("   Causa: " + e.getCause().getMessage());
            }
            appLogs.errorLogs(e);
        }

        System.out.println("=== APLICACIÓN FINALIZADA ===");
        appLogs.infoLogs("Aplicación finalizada");
    }

    private static void crearDirectorioLogs() {
        File logsDir = new File("logs");
        if (!logsDir.exists()) {
            if (logsDir.mkdirs()) {
                System.out.println("📁 Directorio de logs creado");
            } else {
                System.err.println("❌ No se pudo crear el directorio de logs");
            }
        }
    }

    private static void mostrarInformacionConexion(BDConexionSQL con) {
        try {
            System.out.println("\n📊 Información de conexión:");
            System.out.println("   - URL: " + con.getConexion().getMetaData().getURL());
            System.out.println("   - Usuario: " + con.getConexion().getMetaData().getUserName());
            System.out.println("   - Base de datos: " + con.getConexion().getCatalog());
            System.out.println("   - Driver: " + con.getConexion().getMetaData().getDriverName());
            System.out.println("   - Versión: " + con.getConexion().getMetaData().getDriverVersion());

            appLogs.infoLogs("Información de conexión mostrada correctamente");

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener información de la conexión: " + e.getMessage());
            appLogs.errorLogs(e);
        }
    }

    // Método opcional para hacer pruebas básicas
    private static void pruebasBasicas(BDConexionSQL con) {
        try {
            System.out.println("\n🧪 Ejecutando pruebas básicas...");

            // Ejemplo: verificar conectividad
            var statement = con.getConexion().createStatement();
            var resultSet = statement.executeQuery("SELECT GETDATE() as FechaHora");

            if (resultSet.next()) {
                System.out.println("✅ Prueba de consulta exitosa");
                System.out.println("   Fecha/Hora del servidor: " + resultSet.getTimestamp("FechaHora"));
                appLogs.infoLogs("Prueba básica de consulta exitosa");
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("❌ Error en pruebas básicas: " + e.getMessage());
            appLogs.errorLogs(e);
        }
    }
}