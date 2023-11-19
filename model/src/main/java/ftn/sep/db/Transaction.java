package ftn.sep.db;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ftn.sep.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name="yearly")
    private boolean yearly;// if yearly - true if monthly - false
    @ManyToMany
    @JoinTable(
            name="transactions_offers",
            joinColumns = @JoinColumn(name="transaction_id"),
            inverseJoinColumns = @JoinColumn(name="offer_id"))
    private List<Offer> offers;
    @Column(name="payment_type")
    private PaymentType paymentType;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
