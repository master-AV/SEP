package ftn.sep.db;

import ftn.sep.enums.OfferType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name="offer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="offer_type")
    private OfferType type;
    @Column(name="monthly_price")
    private double monthlyPrice;
    @Column(name="yearly_price")
    private double yearlyPrice;
}
