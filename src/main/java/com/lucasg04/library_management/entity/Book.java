package com.lucasg04.library_management.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Book {

    @Id
    private UUID id = UUID.randomUUID();

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;

    public enum Status {
        AVAILABLE,
        RENTED
    }

    public Book(String title) {
        this.title = title;
    }
}