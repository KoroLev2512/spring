package com.example.books.controller;

import com.example.books.dto.CreateBookDto;
import com.example.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "*")
public class DataController {
    
    private final BookService bookService;
    
    @Autowired
    public DataController(BookService bookService) {
        this.bookService = bookService;
    }
    
    // Инициализация тестовых данных
    @PostMapping("/init")
    public ResponseEntity<String> initializeTestData() {
        try {
            List<CreateBookDto> testBooks = Arrays.asList(
                new CreateBookDto(
                    "Война и мир",
                    "Лев Толстой",
                    "Роман-эпопея Льва Николаевича Толстого, описывающий русское общество в эпоху войн против Наполеона в 1805—1812 годах.",
                    1869,
                    1274,
                    "978-5-17-102143-9"
                ),
                new CreateBookDto(
                    "Преступление и наказание",
                    "Фёдор Достоевский",
                    "Социально-психологический и социально-философский роман Фёдора Михайловича Достоевского о преступлении и наказании.",
                    1866,
                    671,
                    "978-5-17-102144-6"
                ),
                new CreateBookDto(
                    "Мастер и Маргарита",
                    "Михаил Булгаков",
                    "Роман Михаила Афанасьевича Булгакова, работа над которым началась в конце 1920-х годов и продолжалась вплоть до смерти писателя.",
                    1967,
                    384,
                    "978-5-17-102145-3"
                ),
                new CreateBookDto(
                    "Евгений Онегин",
                    "Александр Пушкин",
                    "Роман в стихах Александра Сергеевича Пушкина, написанный в 1823—1831 годах, одно из самых значительных произведений русской словесности.",
                    1833,
                    352,
                    "978-5-17-102146-0"
                ),
                new CreateBookDto(
                    "Анна Каренина",
                    "Лев Толстой",
                    "Роман Льва Николаевича Толстого о трагической любви замужней дамы Анны Карениной и офицера Вронского на фоне счастливой семейной жизни дворян Константина Левина и Кити Щербацкой.",
                    1877,
                    864,
                    "978-5-17-102147-7"
                ),
                new CreateBookDto(
                    "Отцы и дети",
                    "Иван Тургенев",
                    "Роман Ивана Сергеевича Тургенева, написанный в 1860—1861 годах и опубликованный в 1862 году.",
                    1862,
                    224,
                    "978-5-17-102148-4"
                ),
                new CreateBookDto(
                    "Мёртвые души",
                    "Николай Гоголь",
                    "Поэма Николая Васильевича Гоголя, жанр которой сам автор обозначил как «поэма».",
                    1842,
                    352,
                    "978-5-17-102149-1"
                ),
                new CreateBookDto(
                    "Герой нашего времени",
                    "Михаил Лермонтов",
                    "Роман Михаила Юрьевича Лермонтова, написанный в 1838—1840 годах.",
                    1840,
                    224,
                    "978-5-17-102150-7"
                )
            );
            
            for (CreateBookDto bookDto : testBooks) {
                bookService.createBook(bookDto);
            }
            
            return ResponseEntity.ok("Тестовые данные успешно инициализированы. Добавлено " + testBooks.size() + " книг.");
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при инициализации данных: " + e.getMessage());
        }
    }
    
    // Очистить все данные
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearAllData() {
        try {
            // Получаем все книги и удаляем их
            bookService.getAllBooks().forEach(book -> {
                try {
                    bookService.deleteBook(book.getId());
                } catch (Exception e) {
                    // Игнорируем ошибки при удалении
                }
            });
            
            return ResponseEntity.ok("Все данные успешно удалены.");
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при удалении данных: " + e.getMessage());
        }
    }
}
