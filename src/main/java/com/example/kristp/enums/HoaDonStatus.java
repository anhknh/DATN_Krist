package com.example.kristp.enums;


import lombok.Getter;

@Getter
public enum HoaDonStatus {
    CHO_XAC_NHAN(0) ,
    DANG_XU_LY(1),
    DANG_GIAO_HANG(2),
    HOAN_TAT(3),
    HOA_DON_CHO(4),
    DA_THANH_TOAN(5),
    CHUA_THANH_TOAN(6),
    DA_HUY(7);

    private final int value;

    HoaDonStatus(int value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return this.name(); // Sử dụng tên của enum
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
