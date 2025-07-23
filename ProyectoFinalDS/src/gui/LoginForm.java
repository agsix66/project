package gui;

import Gestion.CertificadoGestion;
import Gestion.LoginService;
import Modelo.Usuario;
import Modelo.Estudiante;
import Modelo.Instructor;
import Modelo.Administrador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginForm extends JFrame {

    private final LoginService loginService;

    public LoginForm(LoginService loginService) {
        this.loginService = loginService;
        initUI();
    }

    private void initUI() {
        setTitle("LearnVerify - Iniciar Sesión");
        setSize(420, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 250, 255));

        JLabel titleLabel = new JLabel("LearnVerify");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(37, 99, 235));
        titleLabel.setBounds(130, 20, 200, 30);
        panel.add(titleLabel);

        JLabel slogan = new JLabel("Tu aprendizaje, certificado en un clic");
        slogan.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        slogan.setForeground(new Color(107, 114, 128));
        slogan.setBounds(90, 50, 250, 20);
        panel.add(slogan);

        JLabel userLabel = new JLabel("Correo electrónico:");
        userLabel.setBounds(60, 90, 150, 20);
        panel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(60, 115, 280, 30);
        userField.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)));
        panel.add(userField);

        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setBounds(60, 160, 150, 20);
        panel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(60, 185, 280, 30);
        passField.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)));
        panel.add(passField);

        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.setBounds(60, 230, 280, 35);
        loginButton.setBackground(new Color(16, 185, 129));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());

        loginButton.addActionListener((ActionEvent e) -> {
            String email = userField.getText();
            String password = new String(passField.getPassword());

            Usuario usuario = loginService.login(email, password);
            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getNombre() +
                        " (" + usuario.getTipo() + ")");

                switch (usuario.getTipo()) {
                    case ESTUDIANTE:
                        Estudiante estudiante = (Estudiante) usuario;
                        CertificadoGestion certificadoGestion = new CertificadoGestion();
                        DashboardEstudiante dashboard = new DashboardEstudiante(estudiante, certificadoGestion);
                        dashboard.setVisible(true);
                        this.dispose();
                        break;

                    case INSTRUCTOR:
                        Instructor instructor = (Instructor) usuario;
                        CertificadoGestion certificadoGestionI = new CertificadoGestion();
                        DashboardInstructor dashboardI = new DashboardInstructor(instructor, certificadoGestionI);
                        dashboardI.setVisible(true);
                        this.dispose();
                        break;

                    case ADMINISTRADOR:
                        Administrador administrador = (Administrador) usuario;
                        administrador.setLoginService(loginService);
                        DashboardAdmin dashAdmin = new DashboardAdmin(administrador);
                        dashAdmin.setVisible(true);
                        this.dispose();
                        break;

                    default:
                        JOptionPane.showMessageDialog(this,
                                "El tipo de usuario no está habilitado aún",
                                "Acceso restringido", JOptionPane.WARNING_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Credenciales inválidas o usuario bloqueado",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(loginButton);
        add(panel);
    }
}
