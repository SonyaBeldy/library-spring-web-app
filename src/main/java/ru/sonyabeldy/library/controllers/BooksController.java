package ru.sonyabeldy.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sonyabeldy.library.dao.BookDAO;
import ru.sonyabeldy.library.dao.BookPersonDAO;
import ru.sonyabeldy.library.dao.PersonDAO;
import ru.sonyabeldy.library.models.Book;
import ru.sonyabeldy.library.models.BookPerson;
import ru.sonyabeldy.library.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    private final BookPersonDAO bookPersonDAO;
    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookPersonDAO bookPersonDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookPersonDAO = bookPersonDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/booksIndex";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") Book book) {
        bookDAO.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("people", personDAO.index());

        model.addAttribute("bookPerson", bookPersonDAO.getPerson(id));
        return "books/show";
    }
    @PatchMapping("/{id}")
    public String setPersonToBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        if (bookPersonDAO.getPerson(id).isEmpty()) {
            bookPersonDAO.setBookToPerson(id, person.getId());
        } else {
            bookPersonDAO.removeBookFromPerson(id);
        }
        return "redirect:/books";
    }

//    @PatchMapping("/{id}")
//    public String setPersonToBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
//        if (id==0) {
//            bookDAO.setPerson(id, person.getId());
//        } else {
//            bookDAO.setFree(id);
//        }
//        return "books/show";
//    }
}
