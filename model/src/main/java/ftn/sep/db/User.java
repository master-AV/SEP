package ftn.sep.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="users")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;
    @Column(name="email")
    private String email;
    @Column(name="verified")
    private boolean verified;
    @Column(name="password")
    private String password;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Transaction> transactions;
    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @Column(name="pin")
    protected String pin;

    @Column(name="locked_until")
    protected LocalDateTime lockedUntil;


    @Column(name="failed_attempts", nullable = false)
    protected Integer failedAttempts;

    public User(){
        this.verified = true; //change after testing
        this.lockedUntil = null;
        this.failedAttempts = 0;
    }
}
