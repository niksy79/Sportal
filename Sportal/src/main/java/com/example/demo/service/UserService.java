package com.example.demo.service;
import com.example.demo.dto.userdto.*;
import com.example.demo.exeptions.AuthenticationException;
import com.example.demo.exeptions.BadRequestException;
import com.example.demo.exeptions.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public RegisterUserResponseDTO addUser(RegisterUserRequestDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("Email already exist");
        }
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new BadRequestException("Username already exist");
        }
        if (!userDTO.getPassword().equals(userDTO.getConfirm())) {
            throw new BadRequestException("Password does not match, confirm password");
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        User user = new User(userDTO.getUsername(), userDTO.getPassword(),
                userDTO.getEmail(), LocalDate.now(),LocalDateTime.now(),false);
        userRepository.save(user);
        return new RegisterUserResponseDTO(user);
    }

    public UserWithoutPassDTO login(LoginUserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        isUserExist(user);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(userDTO.getPassword(), user.getPassword())) {
            return new UserWithoutPassDTO(user);
        } else {
            throw new AuthenticationException("Invalid username or password");
        }

    }

    public UserWithoutPassDTO loginAsAdmin(LoginUserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        isUserExist(user);
        if (!user.getIsAdmin()) {
            user.setIsAdmin(true);
            userRepository.save(user);
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(userDTO.getPassword(), user.getPassword())) {
            return new UserWithoutPassDTO(user);
        } else {
            throw new AuthenticationException("Invalid username or password");
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
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return user.get();
    }

    public void save(User u) {
        userRepository.save(u);
    }

    public void deleteUser(long id) {
        User user = getUserById(id);
        user.setUsername(null);
        user.setEmail(null);
        user.setPassword(null);
        user.setIsAdmin(false);
        save(user);
    }

    public UserProfileDTO showProfile(long id) {
        User user = getUserById(id);

        return new UserProfileDTO(user);
    }

    public List<User> allUsersWithComments() {
        return userRepository.findAll();
    }

    public boolean checkIsUserAdmin(User u) {
        if (u.getIsAdmin()) {
            return true;
        }
        throw new AuthenticationException("Please, log in as administrator");
    }

    public void isUserExist(User user) {
        if (user == null) {
            throw new AuthenticationException("Wrong username or password");
        }
    }
}
