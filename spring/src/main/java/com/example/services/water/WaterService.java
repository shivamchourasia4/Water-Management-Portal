package com.example.services.water;

import java.util.List;

import com.example.model.WaterModel;

public interface WaterService {
    List<WaterModel> getWaterInfo();

    WaterModel getWaterInfoById(String id);

    String WaterInfoUpdate(WaterModel data);

    String waterInfoSave(WaterModel data, String email);

    String waterInfoDelete(String id);

    List<WaterModel> searchWaterModels(String city);
}
