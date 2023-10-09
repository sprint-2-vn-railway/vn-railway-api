package com.example.vn_railway.repository.trip;

import com.example.vn_railway.dto.ISeatDto;
import com.example.vn_railway.model.trip.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ISeatRepository extends JpaRepository<Seat,Long> {
    @Query(value = "  call get_seat(:coachId,:firstTripId,:lastTripId)  ",nativeQuery = true)
    List<ISeatDto> findAllSeatByCoachId(@Param("coachId") Long coachId,
                                        @Param("firstTripId") Long firstTripId,
                                        @Param("lastTripId") Long lastTripId);
}
