package com.livrogoogleandroid.jordi.livroandroid.bean;

/**
 * Created by jordi on 02/07/14.
 */
public class Login {

    private String usuario;
    private String senha;

    public Login(){

    }

    public Login(String usuario, String senha){
        this.usuario = usuario;
        this. senha = senha;
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

    public String getUsuario(){
        return usuario;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getSenha(){
        return senha;
    }


}
