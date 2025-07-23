package gui;

import Modelo.Estudiante;

import javax.swing.*;

public class CursosDisponiblesForm extends JFrame {
    public CursosDisponiblesForm(Estudiante estudiante) {
        setTitle("Cursos Disponibles");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Aquí se mostrarán los cursos disponibles para " + estudiante.getNombre(), SwingConstants.CENTER);
        add(label);
    }
}
