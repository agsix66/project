package DBConexion;

import lombok.Getter;

import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

@Getter
public class LeerProperties {
    private String servidor;
    private String usuario;
    private String password;
    private String puerto;
    private String database;

    public void cargarPropiedades(){
        InputStream input = null;
        String[] rutasBusqueda = {
                "dbConfig.properties",           // Raíz del classpath
                "/dbConfig.properties",          // Raíz absoluta del classpath
                "main/resources/dbConfig.properties", // Por si acaso
                "resources/dbConfig.properties"   // Alternativa
        };

        // Intentar múltiples rutas
        for (String ruta : rutasBusqueda) {
            input = LeerProperties.class.getClassLoader().getResourceAsStream(ruta);
            if (input != null) {
                System.out.println("✅ Archivo encontrado en: " + ruta);
                break;
            } else {
                System.out.println("❌ No encontrado en: " + ruta);
            }
        }

        // Si no encontró nada, intentar con getResourceAsStream de la clase
        if (input == null) {
            input = LeerProperties.class.getResourceAsStream("/dbConfig.properties");
            if (input != null) {
                System.out.println("✅ Archivo encontrado usando getResourceAsStream de clase");
            }
        }

        try {
            if(input == null){
                // Debug adicional
                debugClasspath();
                throw new Exception("No se encontró el archivo dbConfig.properties en ninguna ruta. " +
                        "Verifique que esté en src/main/resources/");
            }

            Properties prop = new Properties();
            prop.load(input);

            servidor = decodificarSiEsNecesario(prop.getProperty("servidor"));
            usuario = decodificarSiEsNecesario(prop.getProperty("usuario"));
            password = decodificarSiEsNecesario(prop.getProperty("password"));
            puerto = decodificarSiEsNecesario(prop.getProperty("puerto"));
            database = decodificarSiEsNecesario(prop.getProperty("database"));

            // Debug info (remover en producción)
            System.out.println("📋 Configuración cargada:");
            System.out.println("   Servidor: " + servidor);
            System.out.println("   Puerto: " + (puerto.isEmpty() ? "(default)" : puerto));
            System.out.println("   Database: " + database);
            System.out.println("   Usuario: " + (usuario.isEmpty() ? "(Windows Auth)" : usuario));
            System.out.println("   Password: " + (password.isEmpty() ? "(Windows Auth)" : "***"));

        }catch(Exception ex){
            throw new RuntimeException("Error al cargar las propiedades de la base de datos: " + ex.getMessage(), ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    // Ignore
                }
            }
        }
    }

    // Método de debug para ver el classpath
    private void debugClasspath() {
        System.out.println("\n=== DEBUG CLASSPATH ===");
        String classpath = System.getProperty("java.class.path");
        System.out.println("Classpath completo:");
        for (String path : classpath.split(System.getProperty("path.separator"))) {
            System.out.println("  - " + path);
        }

        // Listar recursos disponibles
        java.net.URL location = LeerProperties.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println("Ubicación de la clase: " + location);

        // Intentar listar el directorio resources
        java.net.URL resourcesUrl = LeerProperties.class.getClassLoader().getResource("");
        System.out.println("URL de resources: " + resourcesUrl);
        System.out.println("========================\n");
    }

    private String decodificarSiEsNecesario(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "";
        }

        try {
            // Intentar decodificar Base64
            return new String(Base64.getDecoder().decode(valor.trim()));
        } catch (IllegalArgumentException e) {
            // Si no es Base64 válido, devolver el valor original
            System.out.println("⚠️ Valor no está en Base64, usando valor original: " + valor);
            return valor.trim();
        }
    }

    public String decodificador(String valorCodificado){
        if (valorCodificado == null || valorCodificado.trim().isEmpty()) {
            return "";
        }
        return new String(Base64.getDecoder().decode(valorCodificado));
    }
}