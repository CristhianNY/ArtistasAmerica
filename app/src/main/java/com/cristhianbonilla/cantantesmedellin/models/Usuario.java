package com.cristhianbonilla.cantantesmedellin.models;

/**
 * Created by cali1 on 10/08/2017.
 */

public class Usuario {

    private String usuario;
    private String email;
    private String imagenPerfil;
    private String idUsuario;
    private String tipoUser;
    public Usuario(){

    }

    public Usuario(String usuario, String email, String imagenPerfil, String idUsuario, String tipoUser) {
        this.usuario = usuario;
        this.email = email;
        this.imagenPerfil = imagenPerfil;
        this.idUsuario = idUsuario;
        this.tipoUser = tipoUser;
    }

    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }
}
