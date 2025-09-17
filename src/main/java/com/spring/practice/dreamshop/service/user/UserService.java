package com.spring.practice.dreamshop.service.user;

import org.springframework.stereotype.Service;
import com.spring.practice.dreamshop.model.User;
import com.spring.practice.dreamshop.request.CreateUserRequest;
import com.spring.practice.dreamshop.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    @Override
    public User read(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public User create(CreateUserRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public User update(UpdateUserRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
