package ru.kata.spring.boot_security.demo.repository;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.Users;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DbInit {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
//  Создание ролей и пользователя Админ
    @PostConstruct
    private void postConstruct() {
//      Создание ролей
        Role normalUser = new Role();
        normalUser.setRole("ROLE_USER");
        normalUser.setId(1L);
        Role admin = new Role();
        admin.setRole("ROLE_ADMIN");
        admin.setId(2L);
        roleRepository.save(normalUser);
        roleRepository.save(admin);
//      Создание пользователя - с ролью Админ
        Users adminUser = new Users();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getById(2L));
        roles.add(roleRepository.getById(1L));
        adminUser.setRoles(roles);
        userRepository.save(adminUser);
    }
}
