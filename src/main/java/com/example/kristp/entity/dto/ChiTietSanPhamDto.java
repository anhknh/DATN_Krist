package com.example.kristp.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietSanPhamDto {
    private Integer idMauSac;
    private Integer idSize;
    private Integer idTayAo;
    private Integer idCoAo;
    private String qrCode;
    private Float donGia;
    private Integer soLuong;
    private MultipartFile anhSanPham;
}
