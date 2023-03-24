package com.example.be.dto.tours;

import com.example.be.model.tours.TourGuide;

public interface IToursDto {
    Integer getId();
    String getDescription();
    String getName();
    String getSchedule();
    String getLocation();
    String getStartDate();
    String getEndDate();
    Double getPrice();
    TourGuide getTourGuide();
    Double getCost();
    int getSlot();
    int getRemaining();
}
