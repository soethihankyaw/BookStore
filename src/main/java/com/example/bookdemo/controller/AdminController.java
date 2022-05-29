package com.example.bookdemo.controller;

import com.example.bookdemo.dao.BookDao;
import com.example.bookdemo.entities.Book;
import com.example.bookdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AdminController {
    @Autowired
    private BookService bookService;

    private int bookId;

    @GetMapping("/admin/dashboard")
    public String dashBoard(Model model) {
        model.addAttribute("books",bookService.findAllBooks());
        return "admin/dashboard";
    }

    //    admin-dashboard section
    @GetMapping("/admin/book")
    public String showAllBook(Model model) {
        model.addAttribute("books",bookService.findAllBooks());
        model.addAttribute("success",model.containsAttribute("success"));
        model.addAttribute("delete",model.containsAttribute("delete"));
        model.addAttribute("update",model.containsAttribute("update"));
        return "admin/all-book";
    }

    @GetMapping("/admin/book/create")
    public String createBook(Model model) {
        model.addAttribute("book",new Book());
        return "admin/create-book";
    }

    @PostMapping("/admin/book/create")
    public String createBook(@Valid Book book, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "admin/all-book";
        } else {
            bookService.saveBook(book);
            redirectAttributes.addFlashAttribute("success",true);
        }
        return "redirect:/admin/book";
    }

    @GetMapping("/admin/book/delete/{bookId}")
    public String deleteBook(@PathVariable int bookId, RedirectAttributes redirectAttributes) {
        bookService.removeBook(bookId);
        redirectAttributes.addFlashAttribute("delete",true);
        return "redirect:/admin/book";
    }

    @GetMapping("/admin/book/update/{bookId}")
    public String updateBook(@PathVariable int bookId,Model model) {
        model.addAttribute("book",bookService.findBookById(bookId));
        this.bookId = bookId;
        return "admin/update-book";
    }

    @PostMapping("/admin/book/updated")
    public String updateBook(Book book,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "admin/all-book";
        } else {
            bookService.updateBook(bookId,book);
            redirectAttributes.addFlashAttribute("update",true);
        }
        return "redirect:/admin/book";
    }

}
