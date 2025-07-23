package gui;

import Gestion.CertificadoGestion;
import Modelo.Instructor;
import javax.swing.*;
import java.awt.*;

public class DashboardInstructor extends JFrame {
    private final Instructor instructor;
    private final CertificadoGestion certificadoGestion;

    // Colores y estilos
    private final Color COLOR_PRIMARIO = new Color(124, 58, 237);    // #7c3aed
    private final Color COLOR_SECUNDARIO = new Color(16, 185, 129);  // #10b981
    private final Color FONDO = new Color(249, 250, 251);            // #f9fafb

    public DashboardInstructor(Instructor instructor, CertificadoGestion certificadoGestion) {
        this.instructor = instructor;
        this.certificadoGestion = certificadoGestion;

        configurarVentana();
        initUI();
    }

    private void configurarVentana() {
        setTitle("LearnVerify | Panel de Instructor");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initUI() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(FONDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Encabezado
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(FONDO);

        JLabel lblTitulo = new JLabel("Bienvenido, Instructor " + instructor.getNombre());
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(31, 41, 55));

        JLabel lblEspecialidad = new JLabel("Especialidad: " + instructor.getEspecialidad());
        lblEspecialidad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEspecialidad.setForeground(new Color(107, 114, 128));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(FONDO);
        textPanel.add(lblTitulo);
        textPanel.add(lblEspecialidad);

        headerPanel.add(textPanel, BorderLayout.WEST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Panel de opciones principales
        JPanel optionsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        optionsPanel.setBackground(FONDO);

        optionsPanel.add(crearOptionPanel("📚", "Cursos Activos", "Gestiona tus cursos actuales", COLOR_PRIMARIO,
                e -> new CursosInstructorForm(instructor).setVisible(true)));

        optionsPanel.add(crearOptionPanel("👥", "Estudiantes", "Administra estudiantes inscritos", COLOR_SECUNDARIO,
                e -> new EstudiantesInscritosForm(instructor).setVisible(true)));

        optionsPanel.add(crearOptionPanel("📝", "Certificados", "Genera y envía certificados", new Color(59, 130, 246),
                e -> new EnviarCertificadosForm(instructor, certificadoGestion).setVisible(true)));

        // Agregar componentes
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(optionsPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JPanel crearOptionPanel(String icono, String titulo, String descripcion, Color color, java.awt.event.ActionListener action) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(color, 2),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
        });

        // Icono
        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        lblIcono.setForeground(color);
        lblIcono.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Título
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(31, 41, 55));

        // Descripción
        JLabel lblDesc = new JLabel("<html><center>" + descripcion + "</center></html>", SwingConstants.CENTER);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDesc.setForeground(new Color(107, 114, 128));

        // Botón invisible para el click
        JButton btnInvisible = new JButton();
        btnInvisible.setOpaque(false);
        btnInvisible.setContentAreaFilled(false);
        btnInvisible.setBorderPainted(false);
        btnInvisible.addActionListener(action);

        // Agregar componentes
        panel.add(lblIcono, BorderLayout.NORTH);
        panel.add(lblTitulo, BorderLayout.CENTER);
        panel.add(lblDesc, BorderLayout.SOUTH);
        panel.add(btnInvisible, BorderLayout.CENTER);

        return panel;
    }
}