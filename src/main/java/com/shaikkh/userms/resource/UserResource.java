package com.shaikkh.userms.resource;

import com.shaikkh.userms.model.User;
import com.shaikkh.userms.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
//@RequestMapping("/api/v1")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllUsers(@RequestHeader(name = "x-location", required = false) String location, @RequestParam(required = false) String name) {
        System.out.println("x-location: " + location);
        return ResponseEntity.ok(userService.getAllUsers(Optional.ofNullable(name)));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity getSingleUser(@PathVariable Long userId) {
        Optional<User> user = userService.getSingleUser(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody User user) throws URISyntaxException {
        User userCreated = userService.createUser(user);
        return ResponseEntity.created(new URI(userCreated.getId().toString())).build();
    }

    @GetMapping(path = "/users/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllOrders() {
        System.out.println("$$$$$$$$$$$$$");
        System.out.println("Getting all orders from Orderms");
        return userService.getAllOrders();
    }

    /*
    @DeleteMapping
    @PutMapping
    @PatchMapping
    */
}
