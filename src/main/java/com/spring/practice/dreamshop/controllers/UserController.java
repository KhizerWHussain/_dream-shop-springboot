package com.spring.practice.dreamshop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.practice.dreamshop.dto.UserDTO;
import com.spring.practice.dreamshop.exception.AlreadyExistException;
import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.model.User;
import com.spring.practice.dreamshop.request.CreateUserRequest;
import com.spring.practice.dreamshop.request.UpdateUserRequest;
import com.spring.practice.dreamshop.response.APIResponse;
import com.spring.practice.dreamshop.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userInterface;

    @GetMapping("/get-me/{user_id}")
    public ResponseEntity<APIResponse> read(@PathVariable Long user_id) {
        try {
            User user = userInterface.read(user_id);
            UserDTO userDto = userInterface.convert_to_dto(user);
            return ResponseEntity.ok(new APIResponse(true, "User found successfully", userDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signup(@RequestBody CreateUserRequest request) {
        try {
            userInterface.create(request);
            return ResponseEntity.ok(new APIResponse(true, "Signup successfully", null));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse> update(@RequestBody UpdateUserRequest request) {
        try {
            userInterface.update(request);
            return ResponseEntity.ok(new APIResponse(true, "User updated successfully", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<APIResponse> delete(@PathVariable Long user_id) {
        try {
            userInterface.delete(user_id);
            return ResponseEntity.ok(new APIResponse(true, "User deleted successfully", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }
    }
}
