package com.skeleton.skeletons.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Ping")
public final class GetPingController {
    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    @Operation(operationId = "getPing", summary = "Get a ping", tags = {"Ping"}, description = "Ping controller")
    public Ping handle() {
        return new Ping();
    }
}
