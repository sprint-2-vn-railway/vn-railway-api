package com.example.vn_railway.model.trip;

import com.example.vn_railway.model.train.Coach;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(20)",unique = true)
    private String code;
    @ManyToOne
    @JoinColumn(name = "coach_id",referencedColumnName = "id")
    private Coach coach;
    @ManyToOne
    @JoinColumn(name = "ticket_id",referencedColumnName = "id")
    private Ticket ticket;
    @ManyToOne
    @JoinColumn(name = "trip_id",referencedColumnName = "id")
    private Trip trip;
}
