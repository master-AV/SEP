package ftn.sep.webshop.repository;

import ftn.sep.db.Offer;
import ftn.sep.enums.OfferType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("select o.price from Offer o where o.id = ?1")
    double findOfferPriceById(int id);
    Optional<Offer> findById(Long id);
    Optional<Offer> findByType(OfferType type);
}
