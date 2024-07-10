package library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+", message = "The name must match the pattern F... N... Otchestvo...")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 0, message = "Age not < 0")
    @Max(value = 2022, message = "Age not > 2022")
    @Column(name = "year_of_birth")
    private int year;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;
}