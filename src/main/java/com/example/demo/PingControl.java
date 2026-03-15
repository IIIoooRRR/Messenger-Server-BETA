package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingControl {
@GetMapping("/ping")
    public String ping() {
    return "Лев бебебебе";
}

}
