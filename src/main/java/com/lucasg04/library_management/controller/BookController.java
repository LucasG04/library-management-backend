package com.lucasg04.library_management.controller;

import com.lucasg04.library_management.entity.Book;
import com.lucasg04.library_management.entity.BookStatus;
import com.lucasg04.library_management.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    // Constructor injection
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET /books - Retrieve all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // POST /books - Create a new book
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book.getTitle(), book.getAuthor());
    }

    // DELETE /books/{id} - Delete a book
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
    }

    // PUT /books/{id}/rent - Mark a book as rented
    @PutMapping("/{id}/rent")
    public Book rentBook(@PathVariable UUID id) {
        return bookService.setBookStatus(id, BookStatus.RENTED);
    }

    // PUT /books/{id}/return - Mark a book as available
    @PutMapping("/{id}/return")
    public Book returnBook(@PathVariable UUID id) {
        return bookService.setBookStatus(id, BookStatus.AVAILABLE);
    }
}