package com.hamed.springsecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.util.List;

@RestController
public class BookController {
    @GetMapping("/books")
    public List<Book> getBooks(JwtAuthenticationToken token) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return  List.of(
                new Book("dd","Ddf"),
                new Book("ddf","dfd")
        );
    }
    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) { return book;}
    @GetMapping("/token")
    public Jwt token(@AuthenticationPrincipal Jwt jwt) {
        return jwt;
    }
    @GetMapping("/profile")
    public String profile(){
        return "scope test";
    }
    public record Book(String title, String author) {}
}
