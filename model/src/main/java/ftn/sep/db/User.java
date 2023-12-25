package ftn.sep.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name="users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
    @Column(name="expires_membership")
    private LocalDateTime expiresMembership;

    public User(String name, String surname, String email, boolean verified, String password, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.verified = verified;
        this.password = password;
        this.role = role;
        this.expiresMembership = null;
    }
}
