package com.example.vn_railway.service.trip;

import com.example.vn_railway.dto.train_dto.ISeatResponse;
import com.example.vn_railway.dto.train_dto.TicketResponse;

public interface ITicketService {
    TicketResponse createTicket(ISeatResponse seatResponse);
}
