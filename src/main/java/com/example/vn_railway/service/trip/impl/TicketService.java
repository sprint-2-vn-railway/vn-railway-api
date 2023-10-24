package com.example.vn_railway.service.trip.impl;

import com.example.vn_railway.common.ChangeTimeStamp;
import com.example.vn_railway.dto.train_dto.ISeatResponse;
import com.example.vn_railway.dto.train_dto.ITicketDto;
import com.example.vn_railway.dto.train_dto.TicketResponse;
import com.example.vn_railway.repository.trip.ITicketRepository;
import com.example.vn_railway.service.trip.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    private final ITicketRepository ticketRepository;
    @Override
    public TicketResponse createTicket(ISeatResponse seatResponse) {
        Double p = seatResponse.getPrice() * seatResponse.getTotalDistance();
        List<ITicketDto> ticketDtoList = ticketRepository.createTicket(seatResponse,
                seatResponse.getTotalDistance()*seatResponse.getPrice());
        if(ticketDtoList == null || ticketDtoList.size() == 0) return null;
        return new TicketResponse(
                ticketDtoList.get(0).getTrainCode(),
                ticketDtoList.get(0).getTrainName(),
                ticketDtoList.get(0).getCoachCode(),
                ticketDtoList.get(0).getTypeOfCoachName(),
                ticketDtoList.get(0).getSeatCode(),
                ChangeTimeStamp.handleChangeFormatTimeStampToString(ticketDtoList.get(0).getStartDate()),
                ChangeTimeStamp.handleChangeFormatTimeStampToString(ticketDtoList.get(ticketDtoList.size() - 1).getEndDate()),
                ticketDtoList.get(0).getCustomerName(),
                ticketDtoList.get(0).getPrice(),
                ticketDtoList.get(0).getFromStation(),
                ticketDtoList.get(ticketDtoList.size() - 1).getToStation()
        );
    }
}
