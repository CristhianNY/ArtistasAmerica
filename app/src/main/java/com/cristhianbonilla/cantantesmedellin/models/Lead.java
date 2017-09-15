package com.cristhianbonilla.cantantesmedellin.models;

/**
 * Created by ASUS on 27/07/2017.
 */

public class Lead {

    private String categoriaEvento;
    private String hora;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String descripcion;
    private String key;
    private String mombreContacto;
    private String idUsuario;
    private String fecha;
    private String telefonoGrupo;
    private String nombreGrupo;
    private String imagenPerfil;



    public Lead() {

    }

    public Lead(String categoriaEvento, String ciudad,
                String descripcion, String direccion, String hora, String key, String telefono,
                String idUsuario, String mombreContacto, String fecha, String nombreGrupo, String telefonoGrupo, String imagenPerfil) {
        this.categoriaEvento = categoriaEvento;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.hora = hora;
        this.key = key;
        this.telefono = telefono;
        this.mombreContacto = mombreContacto;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.telefonoGrupo = telefonoGrupo;
        this.nombreGrupo = nombreGrupo;
        this.imagenPerfil = imagenPerfil;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public String getTelefonoGrupo() {
        return telefonoGrupo;
    }

    public void setTelefonoGrupo(String telefonoGrupo) {
        this.telefonoGrupo = telefonoGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCategoriaEvento() {
        return categoriaEvento;
    }

    public void setCategoriaEvento(String categoriaEvento) {
        this.categoriaEvento = categoriaEvento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMombreContacto() {
        return mombreContacto;
    }

    public void setMombreContacto(String mombreContacto) {
        this.mombreContacto = mombreContacto;
    }
}
