package com.lucasg04.library_management.service;

import com.lucasg04.library_management.entity.Book;
import com.lucasg04.library_management.entity.BookStatus;
import com.lucasg04.library_management.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    // Constructor injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Retrieve all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Create a new book
    public Book createBook(String title, String author) {
        Book book = new Book(title, author, BookStatus.AVAILABLE);
        return bookRepository.save(book);
    }

    // Delete a book by ID
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }

    public Book setBookStatus(UUID id, BookStatus status) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getStatus() == status) {
            throw new RuntimeException("Book is already " + status.toString());
        }
        book.setStatus(status);
        return bookRepository.save(book);
    }
}