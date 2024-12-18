package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Users;

import javax.annotation.PostConstruct;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("select user from Users user left join fetch user.roles where user.username = :name")
    Users findByUsername(String name);
}
