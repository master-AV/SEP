package ftn.sep.webshop.repository;

import ftn.sep.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email = ?1 and u.verified")
    Optional<User> getVerifiedUser(String email);

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.yearlySubscription = true and u.expiresMembership < :dateTime")
    List<User> getUsersWithSubscriptionAndExpiredMembership(@Param("dateTime") LocalDateTime dateTime);

    Optional<User> findById(Long id);

    @Query("select u from User u where u.role.roleName = 'ROLE_USER'")
    List<User> findAllWithRoleUser();

}
