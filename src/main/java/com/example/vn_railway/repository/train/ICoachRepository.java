package com.example.vn_railway.repository.train;

import com.example.vn_railway.dto.train_dto.ICoachDto;
import com.example.vn_railway.model.train.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICoachRepository extends JpaRepository<Coach,Long> {

    @Query(value = " select  c.id as coachId, " +
            "                c.`code` as coachCode, " +
            "                toc.`name` as typeOfCoachName, \n" +
            "                toc.price as typeOfCoachPrice " +
            " from coach c\n" +
            " join type_of_coach toc on toc.id = c.type_of_coach_id \n" +
            " where c.deleted = false " +
            " and c.train_id = :trainId "
            ,nativeQuery = true)
    List<ICoachDto> getCoachByTrainId(@Param("trainId")Long id);
}
