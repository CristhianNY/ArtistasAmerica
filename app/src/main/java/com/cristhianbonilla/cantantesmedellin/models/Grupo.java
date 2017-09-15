package com.cristhianbonilla.cantantesmedellin.models;

import java.security.acl.Group;

/**
 * Created by ASUS on 2/07/2017.
 */

public class Grupo {

    public Grupo() {

    }

    private String nombre , imagen, celular,fijo,email, nombreContacto, califocacion,
            propietario,categoria, key, urlimagen,youTube,url,socialF,descripcion;

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSocialF() {
        return socialF;
    }

    public void setSocialF(String socialF) {
        this.socialF = socialF;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYouTube() {
        return youTube;
    }

    public void setYouTube(String youTube) {
        this.youTube = youTube;
    }

    public Grupo(String nombre, String nombreContacto, String email, String celular, String fijo, String socialF, String youTube,
                 String imagen, String descripcion, String categoria, String key, String urlimagen, String propietario

    ) {
        this.descripcion = descripcion;

        this.categoria = categoria;
        this.celular = celular;
        this.email = email;
        this.fijo = fijo;
        this.nombre = nombre;
        this.nombreContacto = nombreContacto;
        this.youTube = youTube;
        this.socialF = socialF;
        this.key = key;
        this.imagen= imagen;
        this.urlimagen = urlimagen;
        this.propietario = propietario;

    }

    public String getCalifocacion() {
        return califocacion;
    }

    public void setCalifocacion(String califocacion) {
        this.califocacion = califocacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFijo() {
        return fijo;
    }

    public void setFijo(String fijo) {
        this.fijo = fijo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }



}
