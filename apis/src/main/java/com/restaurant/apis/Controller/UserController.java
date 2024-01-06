package com.restaurant.apis.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.apis.Model.User;
import com.restaurant.apis.Service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        super();
        this.userService = userService;
    }

    @PostMapping("/createNewUser")
    public ResponseEntity<User> createUserAndAssignRole(@RequestBody User user){
        // Log the received user object to check its content
        System.out.println("Received user data: " + user);

        // Return an HTTP response
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }    
}
