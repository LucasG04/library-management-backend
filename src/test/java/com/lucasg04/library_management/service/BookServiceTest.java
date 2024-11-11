package com.lucasg04.library_management.service;

import com.lucasg04.library_management.entity.Book;
import com.lucasg04.library_management.entity.BookStatus;
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
        String author = "Peter Meier";
        Book book = new Book(title, author, BookStatus.AVAILABLE);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.createBook(title, author);

        assertEquals(title, createdBook.getTitle());
        assertEquals(author, createdBook.getAuthor());
        assertEquals(BookStatus.AVAILABLE, createdBook.getStatus());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testRentBook() {
        UUID id = UUID.randomUUID();
        Book book = new Book("Test Book", "Peter Meier", BookStatus.AVAILABLE);
        book.setId(id);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book rentedBook = bookService.setBookStatus(id, BookStatus.RENTED);

        assertEquals(BookStatus.RENTED, rentedBook.getStatus());
        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testRentBookAlreadyRented() {
        UUID id = UUID.randomUUID();
        Book book = new Book("Test Book", "Peter Meier", BookStatus.RENTED);
        book.setId(id);
        book.setStatus(BookStatus.RENTED);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.setBookStatus(id, BookStatus.RENTED);
        });

        assertEquals("Book is already RENTED", exception.getMessage());
        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(0)).save(book);
    }

    // Additional tests for returnBook, deleteBook, etc.
}