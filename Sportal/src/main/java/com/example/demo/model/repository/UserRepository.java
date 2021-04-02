package com.example.demo.model.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByUsername(String username);

    @Query("SELECT user FROM User user JOIN Comment comment ON user.id = comment.commentingUser.id")
    List<User> findAll();

}
