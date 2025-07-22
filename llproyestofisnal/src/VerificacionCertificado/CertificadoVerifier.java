package VerificacionCertificado;

import DBConexion.BDConexionSQL;
import Logs.AppLogs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CertificadoVerifier {

    private final AppLogs appLogs = new AppLogs(CertificadoVerifier.class);

    public void verificarCertificadoPorCodigo(String codigoUnico) {
        BDConexionSQL conexionBD = new BDConexionSQL();
        conexionBD.conectarBD();

        String sql = "SELECT nombre_estudiante, curso, fecha_emision FROM Certificados WHERE codigo_unico = ?";
        try (PreparedStatement ps = conexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, codigoUnico);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre_estudiante");
                String curso = rs.getString("curso");
                String fecha = rs.getString("fecha_emision");
                appLogs.infoLogs("Certificado válido: " + nombre + ", Curso: " + curso + ", Fecha: " + fecha);
            } else {
                appLogs.infoLogs("Código no encontrado o certificado inválido.");
            }
        } catch (Exception e) {
            appLogs.errorLogs(e);
        } finally {
            conexionBD.desconectarBD();
        }
    }
}
