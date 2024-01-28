package ftn.sep.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="paid_date")
    private LocalDateTime paidDate;
    @OneToOne
    @JoinColumn(name = "offer_id", referencedColumnName = "id")
    private Offer offer;
    @Column(name="payment_method")
    private String paymentMethod;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Transaction(LocalDateTime paidDate, Offer offer, String paymentMethod, User user) {
        this.paidDate = paidDate;
        this.offer = offer;
        this.paymentMethod = paymentMethod;
        this.user = user;
    }
}
