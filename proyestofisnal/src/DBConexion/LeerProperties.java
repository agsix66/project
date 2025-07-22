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
        try(InputStream input = LeerProperties.class.getClassLoader().getResourceAsStream("dbConfig.properties")){
            if(input == null){
                throw new Exception("No se encontro el archivo dbConfig.properties configurado");
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
        }
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
            System.out.println(" Valor no está en Base64, usando valor original: " + valor);
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