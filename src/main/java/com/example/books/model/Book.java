package com.example.books.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Название книги не может быть пустым")
    @Size(max = 255, message = "Название книги не может превышать 255 символов")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Автор не может быть пустым")
    @Size(max = 255, message = "Имя автора не может превышать 255 символов")
    @Column(nullable = false)
    private String author;
    
    @Size(max = 1000, message = "Описание не может превышать 1000 символов")
    @Column(length = 1000)
    private String description;
    
    @NotNull(message = "Год издания обязателен")
    @Min(value = 1000, message = "Год издания должен быть больше 1000")
    @Max(value = 2024, message = "Год издания не может быть больше текущего года")
    @Column(nullable = false)
    private Integer publicationYear;
    
    @NotNull(message = "Количество страниц обязательно")
    @Min(value = 1, message = "Количество страниц должно быть больше 0")
    @Column(nullable = false)
    private Integer pages;
    
    @Column(name = "isbn", unique = true)
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$", 
             message = "ISBN должен быть в правильном формате")
    private String isbn;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status = BookStatus.AVAILABLE;
    
    @Column(name = "created_at")
    private LocalDate createdAt;
    
    @Column(name = "updated_at")
    private LocalDate updatedAt;
    
    // Constructors
    public Book() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    
    public Book(String title, String author, String description, Integer publicationYear, Integer pages, String isbn) {
        this();
        this.title = title;
        this.author = author;
        this.description = description;
        this.publicationYear = publicationYear;
        this.pages = pages;
        this.isbn = isbn;
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
        this.updatedAt = LocalDate.now();
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
        this.updatedAt = LocalDate.now();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDate.now();
    }
    
    public Integer getPublicationYear() {
        return publicationYear;
    }
    
    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
        this.updatedAt = LocalDate.now();
    }
    
    public Integer getPages() {
        return pages;
    }
    
    public void setPages(Integer pages) {
        this.pages = pages;
        this.updatedAt = LocalDate.now();
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
        this.updatedAt = LocalDate.now();
    }
    
    public BookStatus getStatus() {
        return status;
    }
    
    public void setStatus(BookStatus status) {
        this.status = status;
        this.updatedAt = LocalDate.now();
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
    
    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                ", pages=" + pages +
                ", isbn='" + isbn + '\'' +
                ", status=" + status +
                '}';
    }
}
