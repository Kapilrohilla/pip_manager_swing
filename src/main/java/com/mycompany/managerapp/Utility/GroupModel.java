package com.mycompany.managerapp.Utility;

public class GroupModel {

    String id;
    String name;

    public GroupModel() {

    }

    public GroupModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
