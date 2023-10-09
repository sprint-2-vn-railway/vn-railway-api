package com.example.vn_railway.model.train;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(20)")
    private String code;
    private Boolean deleted;
    @ManyToOne
    @JoinColumn(name = "coach_id",referencedColumnName = "id")
    private Coach coach;
}
