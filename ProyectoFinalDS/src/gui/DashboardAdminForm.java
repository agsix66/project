package gui;

import Modelo.Administrador;

import javax.swing.*;
import java.awt.*;

class DashboardAdmin extends JFrame {

    private final Administrador admin;

    public DashboardAdmin(Administrador admin) {
        this.admin = admin;

        setTitle("Dashboard Administrador - LearnVerify");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("Bienvenido Administrador: " + admin.getNombre(), SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton btnGestionCursos = new JButton("Gestionar Cursos");
        JButton btnDesbloqueo = new JButton("Desbloquear Usuarios");

        btnGestionCursos.addActionListener(e -> {
            new GestionCursosForm(admin).setVisible(true);
        });

        btnDesbloqueo.addActionListener(e -> {
            new DesbloqueoUsuariosForm(admin).setVisible(true);
        });

        panel.add(titulo);
        panel.add(btnGestionCursos);
        panel.add(btnDesbloqueo);

        add(panel);
    }
}
