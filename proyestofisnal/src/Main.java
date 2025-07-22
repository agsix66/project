import DBConexion.BDConexionSQL;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO APLICACIÓN ===");

        try(BDConexionSQL con = new BDConexionSQL()){
            System.out.println("Intentando conectar a la base de datos...");
            con.conectarBD();

            // Verificar si la conexión fue exitosa
            if(con.getConexion() != null && !con.getConexion().isClosed()) {
                System.out.println("✅ ¡Conexión exitosa a la base de datos!");

                // Opcional: Mostrar información de la conexión
                System.out.println("📊 Información de conexión:");
                System.out.println("   - URL: " + con.getConexion().getMetaData().getURL());
                System.out.println("   - Usuario: " + con.getConexion().getMetaData().getUserName());
                System.out.println("   - Base de datos: " + con.getConexion().getCatalog());

            } else {
                System.out.println("❌ No se pudo establecer la conexión.");
            }

            System.out.println("Cerrando conexión...");

        } catch (SQLException e) {
            System.out.println("❌ Error SQL al conectarse a la base de datos:");
            System.out.println("   Mensaje: " + e.getMessage());
            System.out.println("   Código: " + e.getErrorCode());
            System.out.println("   Estado SQL: " + e.getSQLState());
        } catch (Exception e) {
            System.out.println("❌ Error general:");
            System.out.println("   Tipo: " + e.getClass().getSimpleName());
            System.out.println("   Mensaje: " + e.getMessage());
            if(e.getCause() != null) {
                System.out.println("   Causa: " + e.getCause().getMessage());
            }
        }

        System.out.println("=== APLICACIÓN FINALIZADA ===");
    }
}