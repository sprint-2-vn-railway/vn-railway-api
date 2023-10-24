package com.example.vn_railway.repository.trip;

import com.example.vn_railway.dto.train_dto.ISeatDto;
import com.example.vn_railway.dto.train_dto.ISeatResponse;
import com.example.vn_railway.dto.train_dto.SeatPayload;
import com.example.vn_railway.model.trip.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ISeatRepository extends JpaRepository<Seat, Long> {
    @Query(value = "  call get_seat(:coachId,:firstTripId,:lastTripId)  ", nativeQuery = true)
    List<ISeatDto> findAllSeatByCoachId(@Param("coachId") Long coachId,
                                        @Param("firstTripId") Long firstTripId,
                                        @Param("lastTripId") Long lastTripId);

    @Transactional
    @Modifying
    @Query(value =
            " UPDATE seat s\n" +
                    "SET user_id = \n" +
                    "    CASE\n" +
                    "        WHEN user_id IS NULL THEN :userId \n" +
                    "        WHEN user_id = :userId THEN NULL\n" +
                    "        ELSE user_id \n" +
                    "    END\n" +
                    "WHERE s.trip_id BETWEEN :#{#seatPayload.firstTripId} AND :#{#seatPayload.lastTripId}\n" +
                    "    AND s.code = :#{#seatPayload.seatCode} \n" +
                    "    AND s.coach_id = :#{#seatPayload.coachId}  " +
                    "    AND s.available = false ", nativeQuery = true)
    Integer updateSelectedSeat(@Param("seatPayload") SeatPayload seatPayload,
                               @Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = " UPDATE seat s " +
            "   SET user_id = null" +
            "   WHERE s.trip_id BETWEEN :#{#seatPayload.firstTripId} AND :#{#seatPayload.lastTripId}" +
            "    AND s.`code` = :#{#seatPayload.seatCode}" +
            "    AND s.coach_id = :#{#seatPayload.coachId}" +
            "    AND user_id = :userId ", nativeQuery = true)
    Integer clearSelectedSeatByUserId(@Param("seatPayload") SeatPayload seatPayload,
                                      @Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = " UPDATE seat s SET user_id = null ", nativeQuery = true)
    void ClearAllUserIdInSeatEntity();

    @Query(value = " call get_seat_by_code(:coachId,:firstTripId,:lastTripId,:seatCode) ", nativeQuery = true)
    ISeatDto getSeatByCoachIdAndCode(@Param("coachId") Long coachId,
                                     @Param("firstTripId") Long firstTripId,
                                     @Param("lastTripId") Long lastTripId,
                                     @Param("seatCode") String seatCode);

    @Transactional
    @Modifying
    @Query(value = " UPDATE seat s SET user_id = null WHERE user_id = :userId ",nativeQuery = true)
    int clearUserIdInSeatEntity(@Param("userId") Long userId);

    @Query(value = " call get_all_seat_temporary_by_user_name(:userName) ", nativeQuery = true)
    List<ISeatResponse> getAllSeatByUserName(@Param("userName") String userName);

}
