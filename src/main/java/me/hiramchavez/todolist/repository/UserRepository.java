package me.hiramchavez.todolist.repository;

import me.hiramchavez.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmailAndActiveTrue(String userEmail);
}
