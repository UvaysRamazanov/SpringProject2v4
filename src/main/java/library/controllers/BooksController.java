package library.controllers;

import jakarta.validation.Valid;
import library.models.Book;
import library.models.Person;
import library.services.BooksService;
import library.services.PeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BooksController {

    private final BooksService bookService;
    private final PeopleService peopleService;

    @GetMapping
    public String showAll(Model model, @RequestParam(value = "page", required = false) Integer page,
                          @RequestParam(value = "books_per_page", required = false) Integer books_per_page,
                          @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {

        if ((page == null) || (books_per_page == null)) {
            model.addAttribute("books", bookService.findAll(sortByYear));
        } else {
            model.addAttribute("books", bookService.findAll(page, books_per_page, sortByYear));
        }
        return "bookShowAll";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));
        Person bookOwner = bookService.getBookOwner(id);
        if (bookOwner != null) {
            model.addAttribute("owner", bookOwner);
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "bookShow";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "bookNew";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "bookNew";
        } else {
            bookService.save(book);
            return "redirect:/book";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("book", bookService.findOne(id));
        return "bookEdit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "bookEdit";
        } else {
            bookService.update(id, book);
            return "redirect:/book";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/book/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookService.assign(id, selectedPerson);
        return "redirect:/book/" + id;
    }

    @GetMapping("/search")
    public String search() {
        return "bookSearch";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam() String query) {
        model.addAttribute("books", bookService.searchByName(query));
        return "bookSearch";
    }
}