package com.skeleton.skeletons.application.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class GetPingController {
    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    public Ping handle() {
        return new Ping();
    }
}
