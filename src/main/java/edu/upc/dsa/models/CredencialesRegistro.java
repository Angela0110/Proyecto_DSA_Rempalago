package edu.upc.dsa.models;

public class CredencialesRegistro {
    private String username;
    private String password;
    private String mail;

    public CredencialesRegistro(String usrname, String pswd, String mail){
        this.mail = mail;
        this.password = pswd;
        this.username = usrname;
    }
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public String getMail(){return this.mail;}
    public void setUsername(String usrname){this.username = usrname;}
    public void setPassword(String pswd){this.password = pswd;}
    public void setMail(String mail){this.mail = mail;}
}
