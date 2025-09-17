package com.spring.practice.dreamshop.service.user;

import org.springframework.stereotype.Service;
import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.model.User;
import com.spring.practice.dreamshop.repository.UserRepository;
import com.spring.practice.dreamshop.request.CreateUserRequest;
import com.spring.practice.dreamshop.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository _user;

    @Override
    public User read(Long id) {
        return _user.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
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
