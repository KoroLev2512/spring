package com.example.books.dto;

import com.example.books.model.BookStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class BookDto {
    
    private Long id;
    
    @NotBlank(message = "Название книги не может быть пустым")
    @Size(max = 255, message = "Название книги не может превышать 255 символов")
    private String title;
    
    @NotBlank(message = "Автор не может быть пустым")
    @Size(max = 255, message = "Имя автора не может превышать 255 символов")
    private String author;
    
    @Size(max = 1000, message = "Описание не может превышать 1000 символов")
    private String description;
    
    @NotNull(message = "Год издания обязателен")
    @Min(value = 1000, message = "Год издания должен быть больше 1000")
    @Max(value = 2024, message = "Год издания не может быть больше текущего года")
    private Integer publicationYear;
    
    @NotNull(message = "Количество страниц обязательно")
    @Min(value = 1, message = "Количество страниц должно быть больше 0")
    private Integer pages;
    
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$", 
             message = "ISBN должен быть в правильном формате")
    private String isbn;
    
    private BookStatus status;
    
    private LocalDate createdAt;
    
    private LocalDate updatedAt;
    
    // Constructors
    public BookDto() {}
    
    public BookDto(Long id, String title, String author, String description, 
                   Integer publicationYear, Integer pages, String isbn, BookStatus status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.publicationYear = publicationYear;
        this.pages = pages;
        this.isbn = isbn;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getPublicationYear() {
        return publicationYear;
    }
    
    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }
    
    public Integer getPages() {
        return pages;
    }
    
    public void setPages(Integer pages) {
        this.pages = pages;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public BookStatus getStatus() {
        return status;
    }
    
    public void setStatus(BookStatus status) {
        this.status = status;
    }
    
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
