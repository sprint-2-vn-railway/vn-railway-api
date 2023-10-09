package com.example.vn_railway.repository.train;

import com.example.vn_railway.dto.ITrainDto;
import com.example.vn_railway.model.train.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ITrainRepository extends JpaRepository<Train, Long> {

    @Query(value = " call get_train_by_date_and_station(  :fromStationFirst," +
                                                        " :toStationFirst," +
                                                        " :fromStationSecond , " +
                                                        " :toStationSecond , " +
                                                        " :startDate) ",nativeQuery = true)
    List<ITrainDto> findAllTrain(@Param("fromStationFirst") String fromStationFirst,
                                 @Param("toStationFirst") String toStationFirst,
                                 @Param("fromStationSecond") String fromStationSecond,
                                 @Param("toStationSecond") String toStationSecond,
                                 @Param("startDate") LocalDate startDate);
}
