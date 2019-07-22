package com.debadri.doin;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class UserInfo extends RealmObject {
    String userName;
    String others;

    public void addTodo(Todo todo) {
        this.todos.add(todo);
    }

    private RealmList<Todo> todos = new RealmList<Todo>();

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
