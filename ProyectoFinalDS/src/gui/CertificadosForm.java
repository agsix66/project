package gui;

import Gestion.CertificadoGestion;
import Modelo.Estudiante;

import javax.swing.*;

public class CertificadosForm extends JFrame {
    public CertificadosForm(Estudiante estudiante, CertificadoGestion certificadoGestion) {
        setTitle("Generar Certificados");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Aquí puedes generar tus certificados, " + estudiante.getNombre(), SwingConstants.CENTER);
        add(label);
    }
}
