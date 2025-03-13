package com.ky.docstory.service;

import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.entity.User;
import com.ky.docstory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new BusinessException(DocStoryResponseCode.PARAMETER_ERROR);
        }

        User user = new User();
        user.setUsername(username);

        return userRepository.save(user);
    }

}
