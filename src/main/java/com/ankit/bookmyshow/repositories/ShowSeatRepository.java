package com.ankit.bookmyshow.repositories;

import com.ankit.bookmyshow.models.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ss from ShowSeat as ss where ss.id IN :ids")
    public List<ShowSeat> findShowSeatsByIdsAndLock(@Param("ids") List<Long> showSeatIds);
}
