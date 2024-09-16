package library.util;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "book_loans")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "person_id", nullable = false)
    private Long personId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "loan_date", nullable = false)
    private Date loanDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "return_date")
    private Date returnDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLoan bookLoan = (BookLoan) o;
        return Objects.equals(id, bookLoan.id) && Objects.equals(bookId, bookLoan.bookId) && Objects.equals(personId, bookLoan.personId) && Objects.equals(loanDate, bookLoan.loanDate) && Objects.equals(returnDate, bookLoan.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, personId, loanDate, returnDate);
    }
}
