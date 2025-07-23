package gui;

import Modelo.Administrador;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class DesbloqueoUsuariosForm extends JFrame {

    private final Administrador admin;

    public DesbloqueoUsuariosForm(Administrador admin) {
        this.admin = admin;

        setTitle("Desbloquear Usuarios - LearnVerify");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ✅ Verifica que admin.getLoginService() exista
        List<Usuario> usuariosBloqueados = admin.getLoginService()
                .getUsuarios()
                .values()
                .stream()
                .filter(Usuario::isBloqueado)
                .collect(Collectors.toList());

        DefaultListModel<Usuario> modelo = new DefaultListModel<>();
        usuariosBloqueados.forEach(modelo::addElement);

        JList<Usuario> listaUsuarios = new JList<>(modelo);
        JScrollPane scroll = new JScrollPane(listaUsuarios);

        JButton btnDesbloquear = new JButton("Desbloquear");

        btnDesbloquear.addActionListener(e -> {
            Usuario seleccionado = listaUsuarios.getSelectedValue();
            if (seleccionado != null) {
                seleccionado.desbloquear();
                modelo.removeElement(seleccionado);
                JOptionPane.showMessageDialog(this, "Usuario desbloqueado exitosamente.");
            }
        });

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnDesbloquear, BorderLayout.SOUTH);

        add(panel);
    }
}
