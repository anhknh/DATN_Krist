package com.example.kristp.entity;


import com.example.kristp.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "co_ao")
public class CoAo extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String kieuCoAo ;

    private String moTa;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "trang_thai")
    private Status trangThai;
}
