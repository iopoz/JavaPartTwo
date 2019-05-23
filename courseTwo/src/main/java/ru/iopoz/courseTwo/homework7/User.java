package ru.iopoz.courseTwo.homework7;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class User {
    private final String userNick;
    private final String userPassword;
    private boolean isAuthorized;

    private final DataInputStream is;
    private final DataOutputStream os;


    public User(String nickName, String uPass, DataInputStream is, DataOutputStream os){
        this.userNick = nickName;
        this.userPassword = uPass;
        this.isAuthorized = false;
        this.is = is;
        this.os = os;
    }

    public boolean isUserAuthorized() {
        return isAuthorized;
    }

    public String getUserNick(){
        return userNick;
    }

    public String getUserPassword(){
        return userPassword;
    }

    public void setAuthorized(boolean flag){
        isAuthorized = flag;
    }

    public DataInputStream getIs() {
        return is;
    }

    public DataOutputStream getOs() {
        return os;
    }
}
