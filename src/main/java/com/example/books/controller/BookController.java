package com.example.books.controller;

import com.example.books.dto.BookDto;
import com.example.books.dto.CreateBookDto;
import com.example.books.model.BookStatus;
import com.example.books.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {
    
    private final BookService bookService;
    
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    // Получить все книги
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
    
    // Получить все книги с пагинацией
    @GetMapping("/paginated")
    public ResponseEntity<Page<BookDto>> getAllBooksPaginated(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<BookDto> books = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(books);
    }
    
    // Получить книгу по ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }
    
    // Создать новую книгу
    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody CreateBookDto createBookDto) {
        BookDto createdBook = bookService.createBook(createBookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }
    
    // Обновить книгу
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, 
                                              @Valid @RequestBody CreateBookDto updateBookDto) {
        BookDto updatedBook = bookService.updateBook(id, updateBookDto);
        return ResponseEntity.ok(updatedBook);
    }
    
    // Удалить книгу
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    
    // Поиск книг по названию
    @GetMapping("/search/title")
    public ResponseEntity<List<BookDto>> searchBooksByTitle(@RequestParam String title) {
        List<BookDto> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }
    
    // Поиск книг по автору
    @GetMapping("/search/author")
    public ResponseEntity<List<BookDto>> searchBooksByAuthor(@RequestParam String author) {
        List<BookDto> books = bookService.searchBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }
    
    // Поиск книг по статусу
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookDto>> getBooksByStatus(@PathVariable BookStatus status) {
        List<BookDto> books = bookService.getBooksByStatus(status);
        return ResponseEntity.ok(books);
    }
    
    // Получить доступные книги
    @GetMapping("/available")
    public ResponseEntity<List<BookDto>> getAvailableBooks() {
        List<BookDto> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(books);
    }
    
    // Поиск по ключевым словам
    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchBooksByKeyword(@RequestParam String keyword) {
        List<BookDto> books = bookService.searchBooksByKeyword(keyword);
        return ResponseEntity.ok(books);
    }
    
    // Изменить статус книги
    @PatchMapping("/{id}/status")
    public ResponseEntity<BookDto> changeBookStatus(@PathVariable Long id, 
                                                   @RequestParam BookStatus status) {
        BookDto updatedBook = bookService.changeBookStatus(id, status);
        return ResponseEntity.ok(updatedBook);
    }
    
    // Получить статистику
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getBookStatistics() {
        Map<String, Long> statistics = bookService.getBookStatistics();
        return ResponseEntity.ok(statistics);
    }
    
    // Получить все возможные статусы книг
    @GetMapping("/statuses")
    public ResponseEntity<BookStatus[]> getAllStatuses() {
        return ResponseEntity.ok(BookStatus.values());
    }
}
