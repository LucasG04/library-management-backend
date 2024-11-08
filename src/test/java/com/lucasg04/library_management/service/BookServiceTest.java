package com.lucasg04.library_management.service;

import com.lucasg04.library_management.entity.Book;
import com.lucasg04.library_management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

public class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    public void testCreateBook() {
        String title = "Test Book";
        Book book = new Book(title);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.createBook(title);

        assertEquals(title, createdBook.getTitle());
        assertEquals(Book.Status.AVAILABLE, createdBook.getStatus());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testRentBook() {
        UUID id = UUID.randomUUID();
        Book book = new Book("Test Book");
        book.setId(id);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book rentedBook = bookService.setBookStatus(id, Book.Status.RENTED);

        assertEquals(Book.Status.RENTED, rentedBook.getStatus());
        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testRentBookAlreadyRented() {
        UUID id = UUID.randomUUID();
        Book book = new Book("Test Book");
        book.setId(id);
        book.setStatus(Book.Status.RENTED);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.setBookStatus(id, Book.Status.RENTED);
        });

        assertEquals("Book is already RENTED", exception.getMessage());
        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(0)).save(book);
    }

    // Additional tests for returnBook, deleteBook, etc.
}