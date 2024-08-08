package library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "name between 2 - 30 char")
    @Column(name = "title")
    private String name;

    @Min(value = 0, message = "Age not < 0")
    @Column(name = "year")
    private int year;

    @Column(name = "author")
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Author between 2 - 30 char")
    private String author;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean isOverdue;
}