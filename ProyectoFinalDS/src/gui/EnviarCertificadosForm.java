package gui;

import Gestion.CertificadoGestion;
import Modelo.Instructor;

import javax.swing.*;

public class EnviarCertificadosForm extends JFrame {
    private final Instructor instructor;
    private final CertificadoGestion certificadoGestion;

    public EnviarCertificadosForm(Instructor instructor, CertificadoGestion certificadoGestion) {
        this.instructor = instructor;
        this.certificadoGestion = certificadoGestion;

        setTitle("Enviar Certificados");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        JLabel label = new JLabel("Aquí se implementará el envío de certificados.");
        add(label);
    }
}
