package ru.sonyabeldy.library.models;

public class BookPerson {

    private int bookId;
    private int personId;

    public BookPerson() {
    }

    public BookPerson(int bookId, int personId) {
        this.bookId = bookId;
        this.personId = personId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
