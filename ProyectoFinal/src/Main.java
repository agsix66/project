import DBConexion.BDConexionSQL;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try(BDConexionSQL con = new BDConexionSQL()){
            con.conectarBD();
        }catch (SQLException e){
            System.out.println("Error al conectarse a la base de datos");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}




