package com.antazri.model.bean;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "book")
@NamedQueries({
        @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
        @NamedQuery(name = "Book.findByCategory", query = "SELECT b FROM Book b " +
                "JOIN FETCH Category c ON c.id = b.category.id WHERE c.id = :id"),
        @NamedQuery(name = "Book.findByAuthor", query = "SELECT b FROM Book b " +
                "JOIN FETCH Author a ON a.id = b.author.id WHERE a.id = :id"),
        @NamedQuery(name = "Book.findByTitle", query = "SELECT b FROM Book b WHERE lower(b.title) LIKE :title")
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "publication_date", nullable = false)
    private Date publicationDate;

    @Column(name = "copies", nullable = false)
    private int copies;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumns({
            @JoinColumn(name = "category_id", referencedColumnName = "id")
    })
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumns({
            @JoinColumn(name = "author_id", referencedColumnName = "id")
    })
    private Author author;

    @OneToMany(mappedBy = "book",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Loan> loans;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
