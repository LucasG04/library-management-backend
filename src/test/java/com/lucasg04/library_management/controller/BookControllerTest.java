package com.lucasg04.library_management.controller;

import com.lucasg04.library_management.entity.Book;
import com.lucasg04.library_management.entity.BookStatus;
import com.lucasg04.library_management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllBooks() throws Exception {
        // Given
        Book book = new Book("Test Book", "Peter Meier", BookStatus.AVAILABLE);
        var created = bookRepository.save(book);

        // When & Then
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk());

        var response = mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andReturn();
        var books = objectMapper.readValue(response.getResponse().getContentAsString(), Book[].class);
        var createdBook = Arrays.stream(books)
                .filter(b -> b.getId().equals(created.getId()))
                .findFirst()
                .orElseThrow();
        assertEquals(created.getId(), createdBook.getId());
        assertEquals(created.getTitle(), createdBook.getTitle());
        assertEquals(created.getAuthor(), createdBook.getAuthor());
        assertEquals(created.getStatus(), createdBook.getStatus());
    }

    @Test
    public void testCreateBook() throws Exception {
        // Given
        Book book = new Book("New Book", "Peter Meier", BookStatus.AVAILABLE);

        // When & Then
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    public void testRentBook() throws Exception {
        // Given
        Book book = new Book("Rentable Book", "Peter Meier", BookStatus.AVAILABLE);
        book = bookRepository.save(book);

        // When & Then
        mockMvc.perform(put("/books/" + book.getId() + "/rent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("RENTED"));
    }

    @Test
    public void testReturnBook() throws Exception {
        // Given
        Book book = new Book("Returnable Book", "Peter Meier", BookStatus.RENTED);
        book = bookRepository.save(book);

        // When & Then
        mockMvc.perform(put("/books/" + book.getId() + "/return"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Given
        Book book = new Book("Deletable Book", "Peter Meier", BookStatus.AVAILABLE);
        book = bookRepository.save(book);

        // When & Then
        mockMvc.perform(delete("/books/" + book.getId()))
                .andExpect(status().isOk());
    }
}