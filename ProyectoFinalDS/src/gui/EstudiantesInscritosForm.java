package gui;

import Modelo.Instructor;

import javax.swing.*;

public class EstudiantesInscritosForm extends JFrame {
    public EstudiantesInscritosForm(Instructor instructor) {
        setTitle("Estudiantes Inscritos");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Estudiantes inscritos en cursos de: " + instructor.getNombre());
        add(label);
    }
}
