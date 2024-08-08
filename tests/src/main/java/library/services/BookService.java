package library.services;

import jakarta.transaction.Transactional;
import library.models.Book;
import library.models.Person;
import library.repositories.BooksRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BooksRepositories booksRepositories;

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear) {
            return booksRepositories.findAll(Sort.by("year"));
        } else {
            return booksRepositories.findAll();
        }
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepositories.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepositories.save(book);
    }

    @Transactional
    public void update(int id, Book bookUpdated) {
        bookUpdated.setId(id);
        Book oldBook = booksRepositories.findById(id).get();
        bookUpdated.setOwner(oldBook.getOwner());
        booksRepositories.save(bookUpdated);
    }

    @Transactional
    public void delete(int id) {
        booksRepositories.deleteById(id);
    }

    public Person getBookOwner(int id) {
        return booksRepositories.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void release(int id) {
        booksRepositories.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                });
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        booksRepositories.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setTakenAt(new Date());
                });
    }

    public List<Book> findAll(int page, int book_per_page, boolean sortByYear) {
        Pageable pageable;
        if (sortByYear) {
            pageable = PageRequest.of(page, book_per_page, Sort.by("year"));
        } else {
            pageable = PageRequest.of(page, book_per_page);
        }

        return booksRepositories.findAll(pageable).getContent();
    }

    public List<Book> searchByName(String query) {
        return booksRepositories.findByNameStartingWith(query);
    }
}