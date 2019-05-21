package com.antazri.model.bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "loan")
@NamedQueries({
        @NamedQuery(name = "Loan.findAll", query = "SELECT l FROM Loan l"),
        @NamedQuery(name = "Loan.findByMember", query = "SELECT l FROM Loan l WHERE l.member.id = :id"),
        @NamedQuery(name = "Loan.findByBook", query = "SELECT l FROM Loan l " +
                            "JOIN FETCH Book b ON l.book.id = b.id WHERE b.id = :id"),
        @NamedQuery(name = "Loan.countByBook", query = "SELECT COUNT (l) FROM Loan l " +
                "JOIN FETCH Book b ON l.book.id = b.id WHERE b.id = :id")
})
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_sequence")
    @SequenceGenerator(name = "loan_sequence", sequenceName = "loan_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "date_begin", nullable = false)
    private Date dateBegin;

    @Column(name = "date_end", nullable = false)
    private Date dateEnd;

    @Column(name = "date_return", nullable = false)
    private Date dateReturn;

    @Column(name = "extended", nullable = false)
    private boolean extended;

    @Column(name = "returned", nullable = false)
    private boolean returned;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumns({
            @JoinColumn(name = "member_id", referencedColumnName = "id")
    })
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumns({
            @JoinColumn(name = "book_id", referencedColumnName = "id")
    })
    private Book book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
