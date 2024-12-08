package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.TayAo;
import com.example.kristp.service.CoAoService;
import com.example.kristp.service.DanhMucService;
import com.example.kristp.service.TayAoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

@Controller
@RequestMapping("/user/")
public class ProductDetailController {

    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    SanPhamService sanPhamService;
    @Autowired
    DataUtils dataUtils;
    @Autowired
    private DanhMucService danhMucService;

    @Autowired
    private TayAoService tayAoService;

    @Autowired
    private CoAoService coAoService;

    @Autowired
    GioHangService gioHangService;
    @Autowired
    GioHangChiTietService gioHangChiTietService;
    @Autowired
    DanhGiaService danhGiaService;
    @Autowired
    SanPhamYeuThichService sanPhamYeuThichService;


    Integer idProductPage = 0;

    @GetMapping("/chi-tiet-san-pham")
    public String chiTetSanPham(@RequestParam("idSanPham") Integer idSanPham, Model model) {
        idProductPage = idSanPham;
        model.addAttribute("sanPham", sanPhamService.findSanphamById(idSanPham));
        model.addAttribute("listCTSP", chiTietSanPhamService.getProductDetailsByProductId(idSanPham));
        model.addAttribute("dataUtils", dataUtils);
        model.addAttribute("ListColorProductDetail", chiTietSanPhamService.getColorsByProductId(idSanPham));
        model.addAttribute("ListSizeProductDetail", chiTietSanPhamService.getSizesByProductId(idSanPham));


        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        model.addAttribute("listDanhMuc", danhMucs);
        model.addAttribute("listCoAo", listCoAo);
        model.addAttribute("listTayAo", listTayAo);

        List<ChiTietSanPham> chiTietSanPhamList = chiTietSanPhamService.getAllCTSP();
        model.addAttribute("chiTietSanPhamList", chiTietSanPhamList);

        float tongTien = 0;
        ArrayList<GioHangChiTiet> gioHangChiTietList = null;
        if (Authen.khachHang != null) {
            GioHang gioHang = gioHangService.findGioHangByKhachHangId(Authen.khachHang);
            gioHangChiTietList = gioHangChiTietService.getAllGioHangChiTiet(gioHang.getId());
            for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
                tongTien = tongTien + (gioHangChiTiet.getChiTietSanPham().getDonGia() * gioHangChiTiet.getSoLuong());
            }
            model.addAttribute("totalCartItem", gioHangService.countCartItem()); // trả ra tổng số lượng giỏ hàng chi tiết theo user
        }
        Integer limit = 999; // Mặc định hiển thị 3 đánh giá
        Page<DanhGia> listDanhGia  = danhGiaService.getDanhGiaBySanPham(idSanPham, limit);

        model.addAttribute("tongTien", tongTien);
        model.addAttribute("gioHangChiTietList", gioHangChiTietList);

        model.addAttribute("khachHang", Authen.khachHang);
        //hàm format
        model.addAttribute("convertMoney", dataUtils);
        model.addAttribute("listDanhGia", listDanhGia.getContent());
        model.addAttribute("sanPhamYeuThichService", sanPhamYeuThichService);

        return "view/product-detail/Product-detail";
    }

    @GetMapping("/product-detail/find-size")
    public ResponseEntity<List<Integer>> getSizesByColor(@RequestParam Integer colorId) {
        List<Integer> sizes = chiTietSanPhamService.getSizeIdByProductIdAndColorId(idProductPage, colorId);
        return ResponseEntity.ok(sizes);
    }

    @GetMapping("/product-detail/find-detail")
    public ResponseEntity<ChiTietSanPham> getProductDetails(@RequestParam Integer sizeId, @RequestParam Integer colorId) {
        ChiTietSanPham details = chiTietSanPhamService.getByColorAndSize(colorId, sizeId, idProductPage);
        return ResponseEntity.ok(details);
    }
}
