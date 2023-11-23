package edu.upc.dsa.models;

public class Credenciales {

    private String username;
    private String email;
    private String password;
    private String newPassword;
    private String newUser;

    public Credenciales(){

    }
    public Credenciales(String username, String email, String password) {
        this();
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){return this.email;}

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }
}
