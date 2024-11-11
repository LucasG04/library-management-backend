package com.lucasg04.library_management.config;

import com.lucasg04.library_management.entity.Book;
import com.lucasg04.library_management.entity.BookStatus;
import com.lucasg04.library_management.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeDatabase(BookRepository bookRepository) {
        return args -> {
            List<Book> books = List.of(
                    new Book("The Catcher in the Rye", "J.D. Salinger", BookStatus.AVAILABLE),
                    new Book("To Kill a Mockingbird", "Harper Lee", BookStatus.RENTED),
                    new Book("1984", "George Orwell", BookStatus.AVAILABLE),
                    new Book("Pride and Prejudice", "Jane Austen", BookStatus.RENTED),
                    new Book("The Great Gatsby", "F. Scott Fitzgerald", BookStatus.AVAILABLE),
                    new Book("Moby Dick", "Herman Melville", BookStatus.RENTED),
                    new Book("War and Peace", "Leo Tolstoy", BookStatus.AVAILABLE),
                    new Book("Ulysses", "James Joyce", BookStatus.RENTED),
                    new Book("The Odyssey", "Homer", BookStatus.AVAILABLE),
                    new Book("Hamlet", "William Shakespeare", BookStatus.AVAILABLE),
                    new Book("Brave New World", "Aldous Huxley", BookStatus.RENTED),
                    new Book("The Divine Comedy", "Dante Alighieri", BookStatus.AVAILABLE),
                    new Book("Les Misérables", "Victor Hugo", BookStatus.AVAILABLE),
                    new Book("Crime and Punishment", "Fyodor Dostoevsky", BookStatus.RENTED),
                    new Book("Wuthering Heights", "Emily Brontë", BookStatus.AVAILABLE));

            bookRepository.saveAll(books);
        };
    }
}