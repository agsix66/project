package VerificacionCertificado;

import DBConexion.BDConexionSQL;
import Logs.AppLogs;
import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CertificadoReporteExporter {

    private final AppLogs appLogs = new AppLogs(CertificadoReporteExporter.class);

    public void exportarReporteCertificados(String rutaArchivo) {
        BDConexionSQL conexionBD = new BDConexionSQL();
        conexionBD.conectarBD();

        String sql = "SELECT codigo_unico, nombre_estudiante, curso, fecha_emision FROM Certificados";
        try (FileWriter writer = new FileWriter(rutaArchivo);
             PreparedStatement ps = conexionBD.getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            writer.write("Codigo Unico,Nombre Estudiante,Curso,Fecha Emision\n");

            while (rs.next()) {
                writer.write(rs.getString("codigo_unico") + ",");
                writer.write(rs.getString("nombre_estudiante") + ",");
                writer.write(rs.getString("curso") + ",");
                writer.write(rs.getString("fecha_emision") + "\n");
            }

            appLogs.infoLogs("Reporte exportado correctamente en: " + rutaArchivo);

        } catch (Exception e) {
            appLogs.errorLogs(e);
        } finally {
            conexionBD.desconectarBD();
        }
    }
}
