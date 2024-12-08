package com.example.kristp.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietSanPhamForm {
    private List<ChiTietSanPhamDto> chiTietSanPhamDetails;
}
