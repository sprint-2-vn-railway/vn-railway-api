package com.example.vn_railway.controller.train;

import com.example.vn_railway.model.train.Train;
import com.example.vn_railway.service.train.ITrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/train")
public class TrainController {
    private final ITrainService trainService;
    @GetMapping("/list")
    public ResponseEntity<?> getAllTrain(){
        List<Train> trainList = trainService.findAllTrain();
        if (trainList == null || trainList.size() == 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trainList);
    };

}
