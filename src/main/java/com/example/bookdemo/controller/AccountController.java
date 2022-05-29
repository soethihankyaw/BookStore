package com.example.bookdemo.controller;

import com.example.bookdemo.dao.RolesDao;
import com.example.bookdemo.dao.UsersDao;
import com.example.bookdemo.entities.Roles;
import com.example.bookdemo.entities.Users;
import com.example.bookdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private RolesDao rolesDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError() {
        return "error";
    }





    @Transactional
    @ResponseBody
    @GetMapping("/make/admin")
    public String makeAdmin() {

        Users users=new Users();
        users.setPassword(passwordEncoder.encode("12345"));
        users.setAddress("Dream Land");
        users.setEmail("john@gmail.com");
        users.setName("John Doe");
        users.setPhoneNumber("55-55-555");
        Roles roles=new Roles();
        roles.setName("ROLE_ADMIN");
        users.addRole(rolesDao.save(roles));
        usersDao.save(users);

        return "successful admin!";
    }
}
