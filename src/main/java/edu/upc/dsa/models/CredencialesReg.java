package edu.upc.dsa.models;

public class CredencialesReg {


    private String username;
    private String email;
    private String password;


    public CredencialesReg(String username,String email, String password) {
        this.username = username;
        this.email=email;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){return this.email;}
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String mail) {
        this.email = email;
    }
}

