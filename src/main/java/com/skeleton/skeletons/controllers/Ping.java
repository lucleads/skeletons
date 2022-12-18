package com.skeleton.skeletons.controllers;

public final class Ping {
    private final String status;

    public Ping() {
        this.status = "OK";
    }

    public String getStatus() {
        return status;
    }
}