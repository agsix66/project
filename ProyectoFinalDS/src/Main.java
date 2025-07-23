import DBConexion.BDConexionSQL;
import Logs.AppLogs;
import Gestion.CargaMasivaUsuarios;
import Gestion.LoginService;
import Modelo.Usuario;
import gui.LoginForm;

import javax.swing.*;
import java.sql.SQLException;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static final AppLogs appLogs = new AppLogs(Main.class);
    private static BDConexionSQL conexionBD;

    public static void main(String[] args) {
        // Configurar codificación para consola
        System.setProperty("file.encoding", "UTF-8");

        System.out.println("=== INICIANDO APLICACIÓN ===");
        appLogs.infoLogs("Aplicación iniciada");

        // Crear directorio de logs si no existe
        crearDirectorioLogs();

        // Inicializar conexión a BD
        if (inicializarConexionBD()) {
            // Si la conexión es exitosa, cargar usuarios y mostrar GUI
            inicializarAplicacion();
        } else {
            System.out.println("❌ No se puede continuar sin conexión a BD");
            appLogs.infoLogs("Aplicación terminada por falta de conexión a BD");
        }
    }

    private static boolean inicializarConexionBD() {
        try {
            conexionBD = new BDConexionSQL();
            System.out.println("🚀 Intentando conectar a la base de datos...");
            appLogs.infoLogs("Iniciando proceso de conexión a BD");

            conexionBD.conectarBD();

            // Verificar si la conexión fue exitosa
            if (conexionBD.isConectado()) {
                System.out.println("✅ ¡Conexión exitosa a la base de datos!");
                appLogs.infoLogs("Conexión a BD establecida exitosamente");

                // Mostrar información de la conexión
                mostrarInformacionConexion(conexionBD);

                // Opcional: ejecutar pruebas básicas
                // pruebasBasicas(conexionBD);

                return true;
            } else {
                System.out.println("❌ No se pudo establecer la conexión.");
                appLogs.infoLogs("Fallo al establecer conexión a BD");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error SQL al conectarse a la base de datos:");
            System.out.println("   Mensaje: " + e.getMessage());
            System.out.println("   Código: " + e.getErrorCode());
            System.out.println("   Estado SQL: " + e.getSQLState());
            appLogs.errorLogs((Exception)e);
            return false;

        } catch (Exception e) {
            System.out.println("❌ Error general:");
            System.out.println("   Tipo: " + e.getClass().getSimpleName());
            System.out.println("   Mensaje: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("   Causa: " + e.getCause().getMessage());
            }
            appLogs.errorLogs(e);
            return false;
        }
    }

    private static void inicializarAplicacion() {
        try {
            System.out.println("👥 Cargando usuarios desde CSV...");
            appLogs.infoLogs("Iniciando carga de usuarios desde CSV");

            CargaMasivaUsuarios cargador = new CargaMasivaUsuarios();
            List<Usuario> usuarios = cargador.cargarDesdeCSV("liberias/usuario.csv");

            // Crear mapa con clave el correo en minúsculas
            Map<String, Usuario> mapaPorCorreo = usuarios.stream()
                    .collect(Collectors.toMap(
                            u -> u.getEmail().toLowerCase(),
                            u -> u,
                            (usuarioExistente, usuarioNuevo) -> usuarioExistente // conservar primero
                    ));

            System.out.println("✅ Usuarios cargados exitosamente: " + usuarios.size());
            appLogs.infoLogs("Usuarios cargados exitosamente: " + usuarios.size());

            // Crear servicio de login con la conexión BD y usuarios
            LoginService loginService = new LoginService(mapaPorCorreo);

            // Pasar la conexión BD al LoginService si es necesario
            // loginService.setConexionBD(conexionBD);

            System.out.println("🖥️  Iniciando interfaz gráfica...");
            appLogs.infoLogs("Iniciando interfaz gráfica");

            // Inicializar GUI en el hilo de eventos de Swing
            SwingUtilities.invokeLater(() -> {
                try {
                    // Configurar Look and Feel del sistema
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
                } catch (Exception e) {
                    appLogs.errorLogs(e);
                }

                LoginForm loginForm = new LoginForm(loginService);

                // Pasar la conexión BD al form si es necesario
                // loginForm.setConexionBD(conexionBD);

                loginForm.setVisible(true);

                System.out.println("✅ Interfaz gráfica iniciada correctamente");
                appLogs.infoLogs("Interfaz gráfica iniciada correctamente");

                // Agregar hook para cerrar la conexión cuando se cierre la aplicación
                loginForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                loginForm.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        cerrarAplicacion();
                        System.exit(0);
                    }
                });
            });

        } catch (Exception e) {
            System.out.println("❌ Error al cargar usuarios: " + e.getMessage());
            appLogs.errorLogs(e);
            cerrarAplicacion();
        }
    }

    private static void crearDirectorioLogs() {
        File logsDir = new File("logs");
        if (!logsDir.exists()) {
            if (logsDir.mkdirs()) {
                System.out.println("📁 Directorio de logs creado");
                appLogs.infoLogs("Directorio de logs creado");
            } else {
                System.err.println("❌ No se pudo crear el directorio de logs");
                appLogs.infoLogs("No se pudo crear el directorio de logs");
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

    private static void cerrarAplicacion() {
        System.out.println("🔄 Cerrando aplicación...");
        appLogs.infoLogs("Cerrando aplicación");

        if (conexionBD != null) {
            try {
                System.out.println("🔄 Cerrando conexión a BD...");
                conexionBD.close();
                appLogs.infoLogs("Conexión a BD cerrada exitosamente");
            } catch (Exception e) {
                System.err.println("❌ Error al cerrar conexión: " + e.getMessage());
                appLogs.errorLogs(e);
            }
        }

        System.out.println("=== APLICACIÓN FINALIZADA ===");
        appLogs.infoLogs("Aplicación finalizada");
    }

    // Método público para obtener la conexión BD desde otras clases si es necesario
    public static BDConexionSQL getConexionBD() {
        return conexionBD;
    }
}