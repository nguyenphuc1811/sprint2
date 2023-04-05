package com.example.be.dto.tours;

public interface IToursDto {
    Integer getId();
    String getDescription();
    String getName();
    String getSchedule();
    String getLocation();
    String getStartDate();
    String getEndDate();
    Double getPrice();
    Double getCost();
    int getSlot();
    String getImg();
    int getRemaining();
    int getLocationId();
}
