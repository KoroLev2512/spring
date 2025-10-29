package com.example.books.service;

import com.example.books.dto.BookDto;
import com.example.books.dto.CreateBookDto;
import com.example.books.exception.BookAlreadyExistsException;
import com.example.books.exception.BookNotFoundException;
import com.example.books.model.Book;
import com.example.books.model.BookStatus;
import com.example.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {
    
    private final BookRepository bookRepository;
    
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    // Получить все книги
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Получить все книги с пагинацией
    @Transactional(readOnly = true)
    public Page<BookDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::convertToDto);
    }
    
    // Получить книгу по ID
    @Transactional(readOnly = true)
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с ID " + id + " не найдена"));
        return convertToDto(book);
    }
    
    // Создать новую книгу
    public BookDto createBook(CreateBookDto createBookDto) {
        // Проверяем, не существует ли уже книга с таким ISBN
        if (createBookDto.getIsbn() != null && !createBookDto.getIsbn().isEmpty()) {
            if (bookRepository.existsByIsbn(createBookDto.getIsbn())) {
                throw new BookAlreadyExistsException("Книга с ISBN " + createBookDto.getIsbn() + " уже существует");
            }
        }
        
        Book book = convertToEntity(createBookDto);
        book.setCreatedAt(LocalDate.now());
        book.setUpdatedAt(LocalDate.now());
        
        Book savedBook = bookRepository.save(book);
        return convertToDto(savedBook);
    }
    
    // Обновить книгу
    public BookDto updateBook(Long id, CreateBookDto updateBookDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с ID " + id + " не найдена"));
        
        // Проверяем ISBN на уникальность, если он изменился
        if (updateBookDto.getIsbn() != null && !updateBookDto.getIsbn().isEmpty()) {
            if (!updateBookDto.getIsbn().equals(existingBook.getIsbn()) && 
                bookRepository.existsByIsbn(updateBookDto.getIsbn())) {
                throw new BookAlreadyExistsException("Книга с ISBN " + updateBookDto.getIsbn() + " уже существует");
            }
        }
        
        // Обновляем поля
        existingBook.setTitle(updateBookDto.getTitle());
        existingBook.setAuthor(updateBookDto.getAuthor());
        existingBook.setDescription(updateBookDto.getDescription());
        existingBook.setPublicationYear(updateBookDto.getPublicationYear());
        existingBook.setPages(updateBookDto.getPages());
        existingBook.setIsbn(updateBookDto.getIsbn());
        existingBook.setStatus(updateBookDto.getStatus());
        existingBook.setUpdatedAt(LocalDate.now());
        
        Book updatedBook = bookRepository.save(existingBook);
        return convertToDto(updatedBook);
    }
    
    // Удалить книгу
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Книга с ID " + id + " не найдена");
        }
        bookRepository.deleteById(id);
    }
    
    // Поиск книг по названию
    @Transactional(readOnly = true)
    public List<BookDto> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Поиск книг по автору
    @Transactional(readOnly = true)
    public List<BookDto> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Поиск книг по статусу
    @Transactional(readOnly = true)
    public List<BookDto> getBooksByStatus(BookStatus status) {
        return bookRepository.findByStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Поиск доступных книг
    @Transactional(readOnly = true)
    public List<BookDto> getAvailableBooks() {
        return bookRepository.findAvailableBooks().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Поиск по ключевым словам
    @Transactional(readOnly = true)
    public List<BookDto> searchBooksByKeyword(String keyword) {
        return bookRepository.findByKeyword(keyword).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    // Изменить статус книги
    public BookDto changeBookStatus(Long id, BookStatus newStatus) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга с ID " + id + " не найдена"));
        
        book.setStatus(newStatus);
        book.setUpdatedAt(LocalDate.now());
        
        Book updatedBook = bookRepository.save(book);
        return convertToDto(updatedBook);
    }
    
    // Получить статистику
    @Transactional(readOnly = true)
    public Map<String, Long> getBookStatistics() {
        Map<String, Long> statistics = new HashMap<>();
        statistics.put("total", bookRepository.count());
        statistics.put("available", bookRepository.countByStatus(BookStatus.AVAILABLE));
        statistics.put("borrowed", bookRepository.countByStatus(BookStatus.BORROWED));
        statistics.put("reserved", bookRepository.countByStatus(BookStatus.RESERVED));
        statistics.put("maintenance", bookRepository.countByStatus(BookStatus.MAINTENANCE));
        return statistics;
    }
    
    // Конвертация Entity в DTO
    private BookDto convertToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setPages(book.getPages());
        dto.setIsbn(book.getIsbn());
        dto.setStatus(book.getStatus());
        dto.setCreatedAt(book.getCreatedAt());
        dto.setUpdatedAt(book.getUpdatedAt());
        return dto;
    }
    
    // Конвертация DTO в Entity
    private Book convertToEntity(CreateBookDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setDescription(dto.getDescription());
        book.setPublicationYear(dto.getPublicationYear());
        book.setPages(dto.getPages());
        book.setIsbn(dto.getIsbn());
        book.setStatus(dto.getStatus());
        return book;
    }
}
