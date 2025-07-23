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
                throw new Exception("No se encontro el archivo configurado");
            }
            Properties prop = new Properties();
            prop.load(input);
            servidor = decodificador(prop.getProperty("servidor"));
            usuario = decodificador(prop.getProperty("usuario"));
            password = decodificador(prop.getProperty("password"));
            puerto = decodificador(prop.getProperty("puerto"));
            database = decodificador(prop.getProperty("database"));
        }catch(Exception ex){
            throw new RuntimeException("Error al cargar las propiedades de la base de datos",ex);
        }
    }

    public String decodificador(String valorCodificado){
        return new String(Base64.getDecoder().decode(valorCodificado));
    }
}
