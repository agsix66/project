package gui;

import Gestion.CertificadoGestion;
import Modelo.Estudiante;
import javax.swing.*;
import java.awt.*;

public class DashboardEstudiante extends JFrame {
    private final Estudiante estudiante;
    private final CertificadoGestion certificadoGestion;

    // Colores dinámicos similares a tu HTML
    private final Color COLOR_PRIMARIO = new Color(124, 58, 237);    // #7c3aed
    private final Color COLOR_SECUNDARIO = new Color(16, 185, 129);  // #10b981
    private final Color FONDO = new Color(249, 250, 251);            // #f9fafb
    private final Color TEXTO_OSCURO = new Color(31, 41, 55);        // #1f2937

    public DashboardEstudiante(Estudiante estudiante, CertificadoGestion certificadoGestion) {
        this.estudiante = estudiante;
        this.certificadoGestion = certificadoGestion;

        configurarVentana();
        initUI();
    }

    private void configurarVentana() {
        setTitle("LearnVerify | Panel de Estudiante");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initUI() {
        // Panel principal con diseño similar a tu HTML
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(FONDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Encabezado
        JLabel lblTitulo = new JLabel("Bienvenido, " + estudiante.getNombre());
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(TEXTO_OSCURO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Panel de tarjetas (cards)
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardsPanel.setBackground(FONDO);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Crear tarjetas
        cardsPanel.add(crearCard("📚", "Cursos Disponibles", "Explora todos los cursos disponibles", COLOR_PRIMARIO));
        cardsPanel.add(crearCard("✅", "Cursos Aprobados", "Revisa tu progreso académico", COLOR_SECUNDARIO));
        cardsPanel.add(crearCard("📄", "Generar Certificado", "Obtén tus certificados digitales", new Color(59, 130, 246)));
        cardsPanel.add(crearCard("👤", "Mi Perfil", "Actualiza tu información personal", new Color(234, 179, 8)));

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(FONDO);

        JButton btnSalir = new JButton("Cerrar Sesión");
        estiloBoton(btnSalir, new Color(239, 68, 68)); // Rojo

        // Agregar componentes
        mainPanel.add(lblTitulo, BorderLayout.NORTH);
        mainPanel.add(cardsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel crearCard(String icono, String titulo, String descripcion, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(color, 2),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
        });

        // Icono
        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        lblIcono.setForeground(color);
        lblIcono.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Título
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(TEXTO_OSCURO);

        // Descripción
        JLabel lblDesc = new JLabel("<html><center>" + descripcion + "</center></html>", SwingConstants.CENTER);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDesc.setForeground(new Color(107, 114, 128));

        // Agregar componentes a la tarjeta
        card.add(lblIcono, BorderLayout.NORTH);
        card.add(lblTitulo, BorderLayout.CENTER);
        card.add(lblDesc, BorderLayout.SOUTH);

        // Acción al hacer clic
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirVentanaCorrespondiente(titulo);
            }
        });

        return card;
    }

    private void estiloBoton(JButton boton, Color colorFondo) {
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void abrirVentanaCorrespondiente(String opcion) {
        switch(opcion) {
            case "Cursos Disponibles":
                new CursosDisponiblesForm(estudiante).setVisible(true);
                break;
            case "Cursos Aprobados":
                new CursosAprobadosForm(estudiante).setVisible(true);
                break;
            case "Generar Certificado":
                new CertificadosForm(estudiante, certificadoGestion).setVisible(true);
                break;
            case "Mi Perfil":
                // Implementar ventana de perfil
                JOptionPane.showMessageDialog(this, "Ventana de perfil en desarrollo");
                break;
        }
    }
}