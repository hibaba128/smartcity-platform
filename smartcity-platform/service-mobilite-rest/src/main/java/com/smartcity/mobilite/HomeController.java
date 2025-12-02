package com.smartcity.mobilite;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    // TODO: Replace with a proper index or redirect to API docs
    @GetMapping("/")
    public String home() {
        // TODO: Return a friendly application landing response
        return "Mobilite REST Service is running";
    }
}
