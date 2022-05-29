package com.example.bookdemo.service;

import com.example.bookdemo.dao.BookDao;
import com.example.bookdemo.dao.RolesDao;
import com.example.bookdemo.dao.UsersBookDao;
import com.example.bookdemo.dao.UsersDao;
import com.example.bookdemo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private RolesDao rolesDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private UsersBookDao usersBookDao;


    //for understanding , we need this code to refactor.
    @Transactional
    public void checkOut(Users users, List<BookDto> books){
        UserBook userBook =new UserBook();
        Roles roles=new Roles();
        roles.setName("ROLE_USER");
        users.addRole(rolesDao.save(roles));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        userBook.setUsers(usersDao.save(users));
        List<Book> bookList = courseDtoToEntity(books);
        for(Book book:bookList){
            userBook.addBook(book);
        }
        usersBookDao.save(userBook);

    }

    private List<Book> courseDtoToEntity(List<BookDto> bookDtos){
        List<Book> books=new ArrayList<>();
        for(BookDto bookDto:bookDtos){
            books.add(bookDao.getById(bookDto.getId()));
        }
        return books;
    }

    @Transactional
    public List<Book> getBookByUserId(String  email) {
        return usersBookDao.findUserBookByUserId(email)
                .getBookList();
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> users = usersDao.findByEmail(email);
        if(!users.isPresent()) {
            throw new UsernameNotFoundException(email + " Not Found");
        }
        return users.get();
    }
}
