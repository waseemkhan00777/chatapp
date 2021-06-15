package com.clbyte.chatapp.Model;

public class Chat {
    private String sender;
    private String receiver;
    private String message;
    private boolean isseen;

    public Chat(){
//        sender = receiver = message = "";
    }
    public Chat(String sender, String receiver, String message,boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen = isseen;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public boolean isIsseen() {
        return isseen;
    }
}
