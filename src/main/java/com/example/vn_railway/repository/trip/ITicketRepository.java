package com.example.vn_railway.repository.trip;

import com.example.vn_railway.dto.train_dto.ISeatResponse;
import com.example.vn_railway.dto.train_dto.ITicketDto;
import com.example.vn_railway.dto.train_dto.TicketResponse;
import com.example.vn_railway.model.trip.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {
    @Transactional
    @Query(value = " call insert_ticket( " +
            " :#{#seatResponse.userName}, " +
            " :currentPrice, " +
            " :#{#seatResponse.trainId}, " +
            " :#{#seatResponse.coachId}, " +
            " :#{#seatResponse.seatCode}, " +
            " :#{#seatResponse.firstTripId}," +
            " :#{#seatResponse.lastTripId}) ", nativeQuery = true)
    List<ITicketDto> createTicket(@Param("seatResponse") ISeatResponse seatResponse,
                                  @Param("currentPrice") Double currentPrice);
}
