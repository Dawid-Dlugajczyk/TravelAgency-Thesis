package com.finalproject.travelagency.repository;


import com.finalproject.travelagency.model.MealType;
import com.finalproject.travelagency.model.Tour;
import com.finalproject.travelagency.model.TourType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    Optional<Tour> findTourById(Long id);

    @Query("SELECT t FROM Tour t WHERE " +
            "(:countries IS NULL OR t.country IN :countries) " +
            "AND (:cities IS NULL OR t.city IN :cities) " +
            "AND (:meals IS NULL OR t.meal IN :meals) " +
            "AND ((:hotelName IS NULL OR t.hotelName LIKE CONCAT('%', :hotelName, '%')) OR (:hotelName = '')) " +
            "AND ((:arrivalDate IS NULL OR t.arrivalDate IS NOT NULL AND t.arrivalDate <= :arrivalDate)" +
            "AND (:departureDate IS NULL OR t.departureDate IS NOT NULL AND t.departureDate >= :departureDate))" +
            "AND (:types IS NULL OR t.type IN :types) " +
            "AND ((:name IS NULL OR t.name LIKE CONCAT('%', :name, '%')) OR (:name = '' )) " +
            "AND ((:minPrice IS NULL OR t.price >= :minPrice)" +
            "AND (:maxPrice IS NULL OR t.price <= :maxPrice))"+
            "AND ((:minNumOfDays IS NULL OR t.numberOfDays >= COALESCE(:minNumOfDays, t.numberOfDays))" +
            "AND (:maxNumOfDays IS NULL OR t.numberOfDays <= COALESCE(:maxNumOfDays, t.numberOfDays)))")
    List<Tour> findByFilters(
            @Param("countries") List<String> countries,
            @Param("cities") List<String> cities,
            @Param("departureDate") LocalDate departureDate,
            @Param("meals") List<MealType> meals,
            @Param("hotelName") String hotelName,
            @Param("arrivalDate") LocalDate arrivalDate,
            @Param("types") List<TourType> types,
            @Param("name") String name,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minNumOfDays") Integer minNumOfDays,
            @Param("maxNumOfDays") Integer maxNumOfDays);


    @Query("SELECT t FROM Tour t JOIN t.reservations r GROUP BY t ORDER BY COUNT(r) DESC")
    List<Object[]> findTopReservedTours();

    @Query("SELECT t.country, t.city, COUNT(r) FROM Tour t JOIN t.reservations r GROUP BY t.country, t.city ORDER BY COUNT(r) DESC")
    List<Object[]> findTopDestinations();

    @Query("SELECT YEAR(r.bookingDate), MONTH(r.bookingDate), COUNT(r) " +
            "FROM Tour t " +
            "JOIN t.reservations r " +
            "GROUP BY YEAR(r.bookingDate), MONTH(r.bookingDate) " +
            "ORDER BY COUNT(r) DESC")
    List<Object[]> findYearMonthWithMostReservations();

    @Query("SELECT t.numberOfDays, COUNT(r) FROM Tour t JOIN t.reservations r GROUP BY t.numberOfDays ORDER BY COUNT(r) DESC")
    List<Object[]> findMostPopularNumberOfDays();

    List<Tour> findByDepartureDateLessThanEqual(LocalDate currentDate);
}

