package com.stcet.todoapp;

import io.realm.RealmObject;

public class UserInfo extends RealmObject {
    String userName;
    String others;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
}
