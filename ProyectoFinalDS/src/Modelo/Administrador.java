package Modelo;

import Gestion.LoginService;

public class Administrador extends Usuario {
    private LoginService loginService;

    public Administrador(int id, String nombre, String email, String password) {
        super(id, nombre, email, password, TipoUsuario.ADMINISTRADOR);
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public LoginService getLoginService() {
        return loginService;
    }
}
