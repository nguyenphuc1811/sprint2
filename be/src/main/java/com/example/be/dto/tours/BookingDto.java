package com.example.be.dto.tours;

public class BookingDto {
    private Integer id;
    private int slot;
    private boolean payment;
    private IToursDto iToursDto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public IToursDto getiToursDto() {
        return iToursDto;
    }

    public void setiToursDto(IToursDto iToursDto) {
        this.iToursDto = iToursDto;
    }
}
