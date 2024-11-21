package com.example.kristp.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
    private String provinceName;
    private String provinceCode;
    private String districtName;
    private String districtCode;
    private String wardName;
    private String wardCode;
}
