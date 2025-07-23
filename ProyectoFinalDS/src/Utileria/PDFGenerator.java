package Utileria;

import Modelo.Curso;
import Modelo.Estudiante;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;

public class PDFGenerator {

    public static String generarCertificadoPDF(Estudiante estudiante, Curso curso, String codigoVerificacion) {
        String ruta = "Certificado_" + estudiante.getNombre() + "_" + curso.getNombre() + ".pdf";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();

            document.add(new Paragraph("CERTIFICADO DE PARTICIPACIÓN", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20)));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Se certifica que " + estudiante.getNombre() + " ha completado el curso " + curso.getNombre() + ".", FontFactory.getFont(FontFactory.HELVETICA, 14)));
            document.add(new Paragraph("\nCódigo de verificación: " + codigoVerificacion, FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10)));
            document.add(new Paragraph("\nFecha: " + java.time.LocalDate.now(), FontFactory.getFont(FontFactory.HELVETICA, 12)));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ruta;
    }
}
