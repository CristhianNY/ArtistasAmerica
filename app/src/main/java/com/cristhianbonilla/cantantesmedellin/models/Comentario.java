package com.cristhianbonilla.cantantesmedellin.models;

/**
 * Created by ASUS on 21/07/2017.
 */

public class Comentario {

    private String contentComentarios;
    private String img;
    private String username;
    private String idUsuario;
    private String fecha;
    private String key;
    private String rating;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Comentario(){


    }

    public Comentario(String profileImageUrl, String name, String fecha, String comentariokey, String idUsuario, String contentComentarios, String key, String rating){

        this.img = profileImageUrl;
        this.username = name;
        this.fecha = fecha;
        this.comentariokey = comentariokey;
        this.idUsuario = idUsuario;
        this.contentComentarios = contentComentarios;
        this.key = key;
        this.rating = rating;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComentariokey() {
        return comentariokey;
    }

    public void setComentariokey(String comentariokey) {
        this.comentariokey = comentariokey;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String comentariokey;



    public String getContentComentarios() {
        return contentComentarios;
    }

    public void setContentComentarios(String contentComentarios) {
        this.contentComentarios = contentComentarios;
    }

}
