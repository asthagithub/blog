package com.example.blogging.blog.service;

import java.util.*;
import com.example.blogging.blog.payload.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto updateUser(Integer id, UserDto userDto);
    UserDto getUserById(Integer id);
    List<UserDto> getAllUser();
    void deleteUserById(Integer id);


}
