package com.example.books.model;

public enum BookStatus {
    AVAILABLE("Доступна"),
    BORROWED("Взята"),
    RESERVED("Зарезервирована"),
    MAINTENANCE("На обслуживании");
    
    private final String description;
    
    BookStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
