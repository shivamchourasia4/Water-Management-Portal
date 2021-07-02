package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.model.SearchModel;
import com.example.model.WaterModel;
import com.example.services.water.WaterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin
public class WaterController {

    @Autowired
    private WaterService waterService;

    @PostMapping("/addInfo")
    public ResponseEntity<Map<String, String>> save(@RequestBody WaterModel waterModel, HttpServletRequest request) {
        Map<String, String> resp = new HashMap<>();
        try {

            String email = (String) request.getAttribute("email");

            String status = waterService.waterInfoSave(waterModel, email);

            resp.put("message", status);
            if (status == "Bad Request") {
                return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            resp.put("message", "Bad Request");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/admin")
    public List<WaterModel> get() {
        return waterService.getWaterInfo();
    }

    @GetMapping(value = "/getWaterInfo/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {

        WaterModel waterModel = waterService.getWaterInfoById(id);

        if (waterModel == null) {
            Map<String, String> resp = new HashMap<>();
            resp.put("message", "Requested Info Not Found");
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(waterModel, HttpStatus.OK);
    }

    @PutMapping(value = "/admin/{id}")
    public ResponseEntity<Map<String, String>> put(@RequestBody WaterModel waterModel, @PathVariable("id") String id) {

        Map<String, String> resp = new HashMap<>();
        try {

            WaterModel updWaterModel = waterService.getWaterInfoById(id);

            if (updWaterModel == null) {
                resp.put("message", "Bad Request");
                return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
            }

            updWaterModel.setCity(waterModel.getCity());
            updWaterModel.setDuration(waterModel.getDuration());
            updWaterModel.setLocation(waterModel.getLocation());
            updWaterModel.setWaterDesc(waterModel.getWaterDesc());
            updWaterModel.setWaterPressure(waterModel.getWaterPressure());

            // System.out.println(updWaterModel);

            resp.put("message", waterService.WaterInfoUpdate(updWaterModel));

            return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);
        } catch (DataIntegrityViolationException e) {
            resp.put("message", "Bad Request");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/admin/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("id") String id) {

        Map<String, String> resp = new HashMap<>();

        String status = waterService.waterInfoDelete(id);

        if (status != "WaterInfo deleted") {
            resp.put("message", status);
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        resp.put("message", status);
        return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/searchWaterInfo")
    public List<WaterModel> search(@RequestBody SearchModel searchModel) {

        String city = searchModel.getCity();
        return waterService.searchWaterModels(city);
    }

}
