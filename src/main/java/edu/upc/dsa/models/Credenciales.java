package edu.upc.dsa.models;

public class Credenciales {
    String username;
    String contraseña;

    public Credenciales(String username, String contraseña){
        this.username = username;
        this.contraseña = contraseña;
    }
    public String getUsername(){
        return this.username;
    }
    public String getContraseña(){
        return this.contraseña;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setContraseña(String contraseña){
        this.contraseña = contraseña;
    }
}
