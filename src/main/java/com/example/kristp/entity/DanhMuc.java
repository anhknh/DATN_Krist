package com.example.kristp.entity;

import com.example.kristp.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "danh_muc")
public class DanhMuc extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @NotBlank
    private String tenDanhMuc ;

    @NotBlank
    private String moTa;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "trang_thai")
    private Status trangThai;

    @Override
    public String toString() {
        return "DanhMuc{" +
                "id=" + id +
                ", tenDanhMuc='" + tenDanhMuc + '\'' +
                ", moTa='" + moTa + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
