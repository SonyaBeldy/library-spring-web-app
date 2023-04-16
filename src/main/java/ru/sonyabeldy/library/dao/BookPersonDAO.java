package ru.sonyabeldy.library.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.sonyabeldy.library.models.Book;
import ru.sonyabeldy.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookPersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookPersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setBookToPerson(int bookId, int personId) {
        jdbcTemplate.update("INSERT INTO BookPerson VALUES (?, ?)", bookId, personId);
    }

    public void removeBookFromPerson(int bookId) {
        jdbcTemplate.update("DELETE FROM BookPerson WHERE bookId=? ", bookId);
    }

    public List<Book> getBooksFromPerson(int personId) {
        return jdbcTemplate.query("SELECT Book.name, Book.author, Book.year FROM Book JOIN BookPerson ON book.id = BookPerson.bookId JOIN Person ON BookPerson.personid = Person.id WHERE BookPerson.personId=?",
                new Object[]{personId}, new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Person> getPerson(int bookId) {
        return jdbcTemplate.query("SELECT * FROM Person JOIN BookPerson ON Person.id = BookPerson.personid WHERE BookPerson.bookid=?",
                new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
