import Gestion.CargaMasivaUsuarios;
import Gestion.LoginService;
import Modelo.Usuario;
import gui.LoginForm;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            CargaMasivaUsuarios cargador = new CargaMasivaUsuarios();
            List<Usuario> usuarios = cargador.cargarDesdeCSV("liberias/usuario.csv");

            // Crear mapa con clave el correo en minúsculas
            Map<String, Usuario> mapaPorCorreo = usuarios.stream()
                    .collect(Collectors.toMap(
                            u -> u.getEmail().toLowerCase(),
                            u -> u,
                            (usuarioExistente, usuarioNuevo) -> usuarioExistente // conservar primero
                    ));

            LoginService loginService = new LoginService(mapaPorCorreo);

            SwingUtilities.invokeLater(() -> {
                LoginForm loginForm = new LoginForm(loginService);
                loginForm.setVisible(true);
            });

        } catch (Exception e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }
}
