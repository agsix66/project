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
            appLogs= new AppLogs(BDConexionSQL.class);
        }

        public void conectarBD(){
            LeerProperties objProper= new LeerProperties();
            objProper.cargarPropiedades();
            try{
                if (conexion!=null && !conexion.isClosed()){
                    appLogs.infoLogs("Ya existe una conexion ");
                    return;
                }
                String dbUrl = "jdbc:sqlserver://" + objProper.getServidor() + ":" + objProper.getPuerto() +
                        ";databaseName=" + objProper.getDatabase() +
                        ";encrypt=true;trustServerCertificate=true;";
                conexion = DriverManager.getConnection(dbUrl, objProper.getUsuario(), objProper.getPassword());
                appLogs.infoLogs("Conexion Realizada");
            }catch(SQLException ex){
                appLogs.errorLogs(ex);
                Dialogos.alertesMensaje("Error de conexión a la base de datos.\nVerifique el servidor, usuario o base configurada.","Error SQL", JOptionPane.ERROR_MESSAGE);
            }catch(Exception e) {
                appLogs.errorLogs(e);
                Dialogos.alertesMensaje("Error inesperado al intentar conectar.\nComuníquese con el departamento de Tecnología.","Error General",JOptionPane.ERROR_MESSAGE);
            }
        }

        public void desconectarBD(){
            if (conexion!=null){
                try{
                    if(!conexion.isClosed()){
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
