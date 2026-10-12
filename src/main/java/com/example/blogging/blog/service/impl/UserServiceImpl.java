package com.example.blogging.blog.service.impl;

import com.example.blogging.blog.entity.User;
import com.example.blogging.blog.exception.ResourceNotFoundException;
import com.example.blogging.blog.payload.UserDto;
import com.example.blogging.blog.repository.UserRepository;
import com.example.blogging.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtotoUser(userDto);
        User savedUser = userRepository.save(user);
        UserDto dto = this.userToDto(savedUser);
        return dto;
    }

    @Override
    public UserDto updateUser(Integer id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("User","Id",id));
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        User savedUser = userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","Id", id));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
        userRepository.delete(user);
    }

    public UserDto userToDto(User user) {
//        UserDto dto = new UserDto();
//        dto.setId(user.getId());
//        dto.setName(user.getName());
//        dto.setPassword(user.getPassword());
//        dto.setEmail(user.getEmail());
//        return dto;
        return modelMapper.map(user, UserDto.class);
    }

    public User dtotoUser(UserDto dto) {
//        User user = new User();
//        user.setId(dto.getId());
//        user.setName(dto.getName());
//        user.setPassword(dto.getPassword());
//        user.setEmail(dto.getEmail());
//        return user;
        return modelMapper.map(dto, User.class);
    }
}
