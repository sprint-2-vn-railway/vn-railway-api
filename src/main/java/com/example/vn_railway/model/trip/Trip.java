package com.example.vn_railway.model.trip;

import com.example.vn_railway.model.train.Train;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String startDate;
    private String endDate;
    @ManyToOne
    @JoinColumn(name = "distance_id",referencedColumnName = "id")
    private Distance distance;
    @ManyToOne
    @JoinColumn(name = "train_id",referencedColumnName = "id")
    private Train train;

}
