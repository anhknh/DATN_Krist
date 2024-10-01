package com.example.kristp.entity;


import com.example.kristp.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tai_khoan")
public class TaiKhoan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tenDangNhap;

    private String matKhau;

    private String email;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "trang_thai")
    private Status trangThai;

    private String chucVu;
    // Getter và Setter cho tenDangNhap

}
