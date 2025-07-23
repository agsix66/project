package gui;

import Modelo.Administrador;

import javax.swing.*;
import java.awt.*;

public class GestionCursosForm extends JFrame {

    private final Administrador admin;

    public GestionCursosForm(Administrador admin) {
        this.admin = admin;
        setTitle("Gestión de Cursos - LearnVerify");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Para no cerrar todo

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Aquí se gestionarán los cursos (Agregar, Editar, Eliminar)", SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        add(panel);
    }
}
