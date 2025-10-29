package com.example.books.repository;

import com.example.books.model.Book;
import com.example.books.model.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Поиск по названию (без учета регистра)
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Поиск по автору (без учета регистра)
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    // Поиск по статусу
    List<Book> findByStatus(BookStatus status);
    
    // Поиск по году издания
    List<Book> findByPublicationYear(Integer year);
    
    // Поиск по диапазону лет
    List<Book> findByPublicationYearBetween(Integer startYear, Integer endYear);
    
    // Поиск по ISBN
    Optional<Book> findByIsbn(String isbn);
    
    // Поиск по названию и автору
    List<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(String title, String author);
    
    // Поиск доступных книг
    @Query("SELECT b FROM Book b WHERE b.status = 'AVAILABLE'")
    List<Book> findAvailableBooks();
    
    // Поиск книг по ключевым словам в названии или описании
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> findByKeyword(@Param("keyword") String keyword);
    
    // Поиск с пагинацией
    Page<Book> findByStatus(BookStatus status, Pageable pageable);
    
    // Поиск по автору с пагинацией
    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    
    // Подсчет книг по статусу
    long countByStatus(BookStatus status);
    
    // Проверка существования книги по ISBN
    boolean existsByIsbn(String isbn);
    
    // Поиск книг с минимальным количеством страниц
    @Query("SELECT b FROM Book b WHERE b.pages >= :minPages ORDER BY b.pages ASC")
    List<Book> findByMinPages(@Param("minPages") Integer minPages);
    
    // Поиск книг с максимальным количеством страниц
    @Query("SELECT b FROM Book b WHERE b.pages <= :maxPages ORDER BY b.pages DESC")
    List<Book> findByMaxPages(@Param("maxPages") Integer maxPages);
}
