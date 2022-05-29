package com.example.bookdemo.controller;

import com.example.bookdemo.dao.BookDao;
import com.example.bookdemo.entities.Book;
import com.example.bookdemo.entities.Users;
import com.example.bookdemo.service.BookService;
import com.example.bookdemo.service.CartService;
import com.example.bookdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    //all book
    @GetMapping("/user/book")
    public String shop(Model model, Authentication authentication) {

        model.addAttribute("books", bookService.findAllBooks());
        model.addAttribute("cartSize", cartService.cartSize);
        return "user/all-books";
    }

    @GetMapping("/user/library")
    public String boughtBook(Model model, Authentication authentication) {
        Users user = (Users) userService.loadUserByUsername(authentication.getName());
        List<Book> books = userService.getBookByUserId(user.getEmail());

        if(books.isEmpty()) {
            model.addAttribute("reply","Empty in Your Library");
        }
        model.addAttribute("user",user.getName());
        model.addAttribute("books",books);
        return "user/bookshelf";
    }

    //book details
    @GetMapping("/user/book/details/{bookId}")
    public String detailsBook(@PathVariable int bookId, Model model) {
        model.addAttribute("book", bookService.findBookById(bookId));
        model.addAttribute("cartSize", cartService.cartSize);
        return "user/book-details";
    }

    //add to cart
    @GetMapping("/user/book/add/cart/{bookId}")
    public String addToCart(@PathVariable int bookId, Model model) {
        cartService.addToCart(bookService.findBookById(bookId));
        return "redirect:/user/book/details/" + bookId;
    }

    //view books in cart
    @GetMapping("/user/book/cart/view")
    public String viewCart(Model model) {
        model.addAttribute("books", cartService.allBooksInCart());
        model.addAttribute("remove", model.containsAttribute("remove"));
        model.addAttribute("clear", model.containsAttribute("clear"));
        return "user/cart-view";
    }

    //remove
    @GetMapping("/user/book/cart/remove/{bookId}")
    public String removeCart(@PathVariable("bookId") int bookId, Model model, RedirectAttributes redirectAttributes) {
        cartService.removeBookCart(bookService.findBookById(bookId));
        model.addAttribute("cartSize", cartService.cartSize);
        redirectAttributes.addFlashAttribute("remove", true);
        return "redirect:/user/book/cart/view";
    }

    //clear
    @GetMapping("/user/book/cart/clear")
    public String clearCart(Model model, RedirectAttributes redirectAttributes) {
        cartService.clearCart();
        redirectAttributes.addFlashAttribute("clear", true);
        return "redirect:/user/book/cart/view";
    }

    //checkout
    @GetMapping("/user/book/cart/checkout")
    public String checkout(Model model) {
        model.addAttribute("users", new Users());
        return "user/checkout";
    }

    @PostMapping("/user/book/cart/checkout")
    public String processUserCheckout(Users users, BindingResult result) {
        if (result.hasErrors()) {
            return "user/checkout";
        } else {
            userService.checkOut(users, cartService.allBooksInCart());
        }
        return "redirect:/login";
    }

    //register
    @GetMapping("/user/register")
    public String register(Model model) {
        model.addAttribute("users", new Users());
        return "register";
    }

    @PostMapping("/user/register")
    public String doRegister(Users user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            userService.checkOut(user, cartService.allBooksInCart());
        }
        return "redirect:/login";

    }

    @GetMapping("book/search")
    public String searchBook(@RequestParam("search") String title,Model model) {
        if(bookService.searchBook(title) == null) {
            model.addAttribute("reply","* No Book Found *");
        }
        model.addAttribute("books",bookService.searchBook(title));
        return "redirect:/user/all-books";
    }


}
