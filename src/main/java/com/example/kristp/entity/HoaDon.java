package com.example.kristp.entity;

import com.example.kristp.enums.HoaDonStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoa_don")
public class HoaDon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String maHoaDon;
    @ManyToOne
    @JoinColumn(name = "id_dia_chi")
    private DiaChi diaChi;
    @Column(name = "ngay_dat_hang")
    private Date ngayDatHang;
    @Column(name = "tong_tien")
    private BigDecimal tongTien;
    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private HoaDonStatus trangThai;
    @Enumerated(EnumType.STRING)
    private HoaDonStatus trangThaiThanhToan;
    private float phiVanChuyen;
    private String hinhThucThanhToan;
    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien nhanVien;
    @ManyToOne
    @JoinColumn(name = "id_khuyen_mai")
    private KhuyenMai khuyenMai;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;



    @Override
    public String toString() {
        return "HoaDon{" +
                "id=" + id +
                ", diaChi=" + diaChi +
                ", ngayDatHang=" + ngayDatHang +
                ", tongTien=" + tongTien +
                ", trangThai=" + trangThai +
                ", trangThaiThanhToan='" + trangThaiThanhToan + '\'' +
                ", phiVanChuyen=" + phiVanChuyen +
                ", hinhThucThanhToan='" + hinhThucThanhToan + '\'' +
                ", nhanVien=" + nhanVien +
                ", khachHang=" + khachHang +
                ", khuyenMai=" + khuyenMai +
                '}';
    }
}
