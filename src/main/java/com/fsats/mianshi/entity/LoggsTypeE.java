package com.fsats.mianshi.entity;

public enum LoggsTypeE {

    INSERT("INSERT"),DELETE("DELETE"),UPDATE("UPDATE"),SELECT("SELECT"),OTHER("OTHER");

    LoggsTypeE(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
