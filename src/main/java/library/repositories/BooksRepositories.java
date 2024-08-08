package library.repositories;


import library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepositories extends JpaRepository<Book, Long>,
        PagingAndSortingRepository<Book, Long> {
    List<Book> findByNameStartingWith(String name);
}