package com.manish.springrolejwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class adminController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/normal/hello")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("Hello World! by user");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/normal")
    public ResponseEntity<String> d() {
        return ResponseEntity.ok("welcome  user /user ");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/admin/welcome")
    public ResponseEntity<String> ad() {
        return ResponseEntity.ok("welcome Admin");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/admin")
    public ResponseEntity<String> aewd() {
        return ResponseEntity.ok("welcome /Admin");
    }


}
