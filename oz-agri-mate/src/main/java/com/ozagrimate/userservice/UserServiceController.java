package com.ozagrimate.userservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user-service")
public class UserServiceController {
    private UserServiceBusinessLogic userServiceBusinessLogic;

    public UserServiceController () {
        this.userServiceBusinessLogic = new UserServiceBusinessLogic();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        try {
            User user = this.userServiceBusinessLogic.getUser(username);
            return ResponseEntity.ok(user.generateUserResponseDTO());
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> createUser (@RequestBody UserRequest userRequest) {
        try {
            User user = this.userServiceBusinessLogic.registerUser(userRequest);
            return ResponseEntity.ok(user.generateUserResponseDTO());
        }
        catch (UsernameExistsException e) {
            // Return a 409 CONFLICT response if username exists
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String username, @RequestBody UserRequest userRequest) {
        try {
            User user = this.userServiceBusinessLogic.updateUser(username, userRequest);
            return ResponseEntity.ok(user.generateUserResponseDTO());
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("user/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        try {
            this.userServiceBusinessLogic.purgeUser(username);
            return ResponseEntity.noContent().build();
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
