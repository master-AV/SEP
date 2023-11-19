package ftn.sep.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.persistence.*;
@Entity
@Table(name="address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="street")
    private String street;
    @Column(name="number")
    private String number;
    @Column(name="city")
    private String city;
    @Column(name="zip_code")
    private String zipCode;
}
