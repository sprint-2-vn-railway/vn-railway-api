package com.example.vn_railway.model.train;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(20)",unique = true)
    private String code;
    private Boolean deleted;
    @ManyToOne
    @JoinColumn(name = "train_id",referencedColumnName = "id")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "type_of_coach_id",referencedColumnName = "id")
    private TypeOfCoach typeOfCoach;
}
