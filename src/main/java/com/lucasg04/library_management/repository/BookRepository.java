package com.lucasg04.library_management.repository;

import com.lucasg04.library_management.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}