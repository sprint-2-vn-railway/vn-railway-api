package com.example.vn_railway.repository.trip;

import com.example.vn_railway.model.trip.Distance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDistanceRepository extends JpaRepository<Distance, Long> {
}
