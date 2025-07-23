package gui;

import Modelo.Instructor;

import javax.swing.*;

public class CursosInstructorForm extends JFrame {
    public CursosInstructorForm(Instructor instructor) {
        setTitle("Cursos del Instructor");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Aquí se muestran los cursos del instructor: " + instructor.getNombre());
        add(label);
    }
}
