package gui;

import Modelo.Estudiante;

import javax.swing.*;

public class CursosAprobadosForm extends JFrame {
    public CursosAprobadosForm(Estudiante estudiante) {
        setTitle("Cursos Aprobados");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Aquí se mostrarán los cursos aprobados por " + estudiante.getNombre(), SwingConstants.CENTER);
        add(label);
    }
}
