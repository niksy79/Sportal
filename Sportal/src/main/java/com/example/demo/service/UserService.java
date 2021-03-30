package com.example.demo.service;

import com.example.demo.dto.userdto.*;
import com.example.demo.exeptions.AuthenticationException;
import com.example.demo.exeptions.BadRequestException;
import com.example.demo.exeptions.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.repository.UserRepository;
import com.example.demo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public RegisterUserResponseDTO addUser(RegisterUserRequestDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new BadRequestException("Email already exist");
        } else {
            if (!Validator.validEMail(userDTO.getEmail())) {
                throw new BadRequestException("Invalid email");
            }
        }
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new BadRequestException("Username already exist");
        } else {
            if (!Validator.validUsername(userDTO.getUsername())) {
                throw new BadRequestException("Username must be at least 6 characters, without spaces");
            }
        }
        if (!Validator.validPassword(userDTO.getPassword())) {
            throw new BadRequestException("Password must be at least 5 characters, without spaces");
        } else {
            if (!userDTO.getPassword().equals(userDTO.getConfirm())) {
                throw new BadRequestException("Password does not match, confirm password");
            }
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        User user = new User(userDTO);
        user.setCreatedAt(LocalDate.now());
        user.setLastLogin(LocalDateTime.now());
        user.setIsAdmin(false);
        user = userRepository.save(user);
        return new RegisterUserResponseDTO(user);
    }

    public UserWithoutPassDTO login(LoginUserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user == null) {
            throw new AuthenticationException("Invalid username or password");
        } else {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(userDTO.getPassword(), user.getPassword())) {
                return new UserWithoutPassDTO(user);

            } else {
                throw new AuthenticationException("Invalid username or password");
            }
        }
    }

    public UserWithoutPassDTO loginAsAdmin(LoginUserDTO userDTO) {
        User admin = userRepository.findByUsername(userDTO.getUsername());
        if (admin == null) {
            throw new AuthenticationException("Invalid username or password");
        } else {
            if (!admin.getIsAdmin()) {
                admin.setIsAdmin(true);
                userRepository.save(admin);
            }
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(userDTO.getPassword(), admin.getPassword())) {

                return new UserWithoutPassDTO(admin);

            } else {
                throw new AuthenticationException("Invalid username or password");
            }
        }
    }

    public EditUserResponseDTO editProfile(EditUserProfileDTO profileDTO, User user) {

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(profileDTO.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid password");
        }
        if (!profileDTO.getNewPassword().equals(profileDTO.getConfirmPassword())) {
            throw new BadRequestException("Password does not match, confirm password");
        }
        profileDTO.setNewPassword(encoder.encode(profileDTO.getNewPassword()));
        profileDTO.setOldPassword(profileDTO.getNewPassword());
        user.setUsername(profileDTO.getUsername());
        user.setPassword(profileDTO.getNewPassword());
        user.setEmail(profileDTO.getEmail());
        user = userRepository.save(user);

        return new EditUserResponseDTO(user);

    }

    public UserWithoutPassDTO getById(long id) {
        Optional<User> oUser = userRepository.findById(id);
        if (oUser.isPresent()) {
            return new UserWithoutPassDTO(oUser.get());
        }
        else {
            throw new NotFoundException("User not found");
        }
    }


}
