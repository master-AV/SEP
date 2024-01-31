package ftn.sep.webshop.repository;

import ftn.sep.db.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t ORDER BY t.paidDate")
    List<Transaction> findAllOrderByPaidDate();

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId ORDER BY t.paidDate")
    List<Transaction> findAllByUserIdOrderByPaidDate(@Param("userId") Long userId);
}
