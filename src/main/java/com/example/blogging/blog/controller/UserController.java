package com.example.blogging.blog.controller;

import com.example.blogging.blog.payload.ApiResponse;
import com.example.blogging.blog.payload.UserDto;
import com.example.blogging.blog.service.UserService;
import com.example.blogging.blog.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto dto = userService.createUser(userDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id,@Valid @RequestBody UserDto userDto){
        UserDto dto = userService.updateUser(id, userDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id){
        UserDto dto = userService.getUserById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> dtos = userService.getAllUser();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Integer id){
        userService.deleteUserById(id);
        return new ResponseEntity<>(new ApiResponse("user deleted successfully", true), HttpStatus.OK);
    }

}
