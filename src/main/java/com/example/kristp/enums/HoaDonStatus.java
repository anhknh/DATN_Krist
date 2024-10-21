package com.example.kristp.enums;


import lombok.Getter;

@Getter
public enum HoaDonStatus {
    HOA_DON_CHO(0),
    DA_THANH_TOAN(1);

    private final int value;

    HoaDonStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static HoaDonStatus fromValue(int value) {
        for (HoaDonStatus status : HoaDonStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
