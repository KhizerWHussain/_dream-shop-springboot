package com.spring.practice.dreamshop.service.user;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.spring.practice.dreamshop.dto.UserDTO;
import com.spring.practice.dreamshop.exception.AlreadyExistException;
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
    private final ModelMapper modelMapper;

    @Override
    public User read(Long id) {
        return _user.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User create(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !_user.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setFirst_name(request.getFirst_name());
                    user.setLast_name(request.getLast_name());
                    user.setPassword(request.getPassword());
                    return _user.save(user);
                }).orElseThrow(() -> new AlreadyExistException("Email already exists"));
    }

    @Override
    public User update(UpdateUserRequest request) {
        return _user.findById(request.getId())
                .map((existing) -> {
                    existing.setFirst_name(request.getFirst_name());
                    existing.setLast_name(request.getLast_name());
                    return _user.save(existing);
                })
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void delete(Long id) {
        _user.findById(id)
                .ifPresentOrElse(_user::delete, () -> {
                    throw new NotFoundException("User not found");
                });
    }

    @Override
    public UserDTO convert_to_dto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
