package com.spring.practice.dreamshop.service.user;

import com.spring.practice.dreamshop.dto.UserDTO;
import com.spring.practice.dreamshop.model.User;
import com.spring.practice.dreamshop.request.CreateUserRequest;
import com.spring.practice.dreamshop.request.UpdateUserRequest;

public interface IUserService {
    User read(Long id);

    User create(CreateUserRequest request);

    User update(UpdateUserRequest request);

    void delete(Long id);

    UserDTO convert_to_dto(User user);
}
