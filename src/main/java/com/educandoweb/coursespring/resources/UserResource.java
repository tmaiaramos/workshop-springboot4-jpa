package com.educandoweb.coursespring.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.coursespring.entities.User;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
    @GetMapping
    public ResponseEntity<User> findAll() {
        User u = new User(1L, "Maria Brown", "maria@gmail.com", "123456");
        return ResponseEntity.ok().body(u);
    }
    /* public ResponseEntity<List<User>> findAll() {
        List<User> list = new ArrayList<>();
        return ResponseEntity.ok().body(list);
    } */

}
