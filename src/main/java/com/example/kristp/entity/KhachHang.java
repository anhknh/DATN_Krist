package com.example.kristp.entity;


import com.example.kristp.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "khach_hang")

public class KhachHang extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tenKhachHang;
    @Column(name = "sdt_kh")
    private String soDienThoai;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "trang_thai")
    private Status trangThai;

    @OneToOne(cascade = CascadeType.ALL) // Thêm cascade
    @JoinColumn(name = "id_tai_khoan")
    private TaiKhoan taiKhoan;

//    @Column(name = "sdt_kh")
//    private String sdtKh;


}
