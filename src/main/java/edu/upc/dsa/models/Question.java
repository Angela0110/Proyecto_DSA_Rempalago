package edu.upc.dsa.models;

import edu.upc.dsa.util.Fecha;

public class Question {
    String title;
    String message;
    String date;
    String sender;

    public Question(){

    }

    public Question(String title, String message, String sender){
        this.title = title;
        this.message = message;
        this.sender = sender;
        this.date = Fecha.getFecha();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
