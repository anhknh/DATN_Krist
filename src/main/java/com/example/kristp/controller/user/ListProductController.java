package com.example.kristp.controller.user;


import com.example.kristp.entity.*;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import com.example.kristp.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/")
public class ListProductController {
    @Autowired
    private SanPhamService sanPhamService ;
    @Autowired
    GioHangService gioHangService;
    @Autowired
    TimKiemSanPhamService timKiemSanPhamService;
    @Autowired
    DataUtils dataUtils;
    @Autowired
    DanhMucService danhMucService;
    @Autowired
    ChatLieuService chatLieuService;
    @Autowired
    TayAoService tayAoService;
    @Autowired
    CoAoService coAoService;
    @Autowired
    MauSacService mauSacService;
    @Autowired
    SizeService sizeService;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    GioHangChiTietService gioHangChiTietService;


    @GetMapping("/danh-sach-san-pham")
    public String danhSachPham(Model model,
                               @RequestParam(value = "tenSanPham", required = false) String tenSanPham,
                               @RequestParam(value = "danhMucId", required = false) List<Integer> danhMucId,
                               @RequestParam(value = "chatLieuId", required = false) List<Integer>  chatLieuId,
                               @RequestParam(value = "tayAoId", required = false) List<Integer>  tayAoId,
                               @RequestParam(value = "coAoId", required = false) List<Integer>  coAoId,
                               @RequestParam(value = "mauSacId", required = false) List<Integer>  mauSacId,
                               @RequestParam(value = "sizeId", required = false) List<Integer>  sizeId,
                               @RequestParam("page") Optional<Integer> page) {


        Pagination pagination = new Pagination();
        Pageable pageable = PageRequest.of(page.orElse(0), 12);
        Page<SanPham> listSanPham = timKiemSanPhamService.timKiemSanPham(tenSanPham,danhMucId,chatLieuId,
                tayAoId,coAoId,mauSacId,sizeId,pageable);
        model.addAttribute("totalPage", listSanPham.getTotalPages() - 1);
        model.addAttribute("page", listSanPham);
        model.addAttribute("pagination", pagination.getPage(listSanPham.getNumber(), listSanPham.getTotalPages()));
        model.addAttribute("productList", listSanPham.getContent());
        model.addAttribute("totalCartItem", gioHangService.countCartItem()); // trả ra tổng số lượng giỏ hàng chi tiết theo user
        model.addAttribute("dataUtils", dataUtils);

        //load filter
        model.addAttribute("listDanhMuc", danhMucService.getAllDanhMucHD());
        model.addAttribute("listChatLieu", chatLieuService.getAllChatLieuHD());
        model.addAttribute("listTayAo", tayAoService.getAllTayAoHD());
        model.addAttribute("listCoAo", coAoService.getAllCoAoHD());
        model.addAttribute("listMauSac", mauSacService.getAllMauSacHD());
        model.addAttribute("listSize", sizeService.getAllSizeHD());
        model.addAttribute("chiTietSanPhamService", chiTietSanPhamService);

        List<ChiTietSanPham> chiTietSanPhamList = chiTietSanPhamService.getAllCTSP();
        model.addAttribute("chiTietSanPhamList", chiTietSanPhamList);
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();


        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);

        float tongTien = 0;
        ArrayList<GioHangChiTiet> gioHangChiTietList = null;
        if(Authen.khachHang != null) {
            GioHang gioHang = gioHangService.findGioHangByKhachHangId(Authen.khachHang);
            gioHangChiTietList = gioHangChiTietService.getAllGioHangChiTiet(gioHang.getId());
            for (GioHangChiTiet gioHangChiTiet : gioHangChiTietList) {
                tongTien = tongTien + (gioHangChiTiet.getChiTietSanPham().getDonGia() * gioHangChiTiet.getSoLuong());
            }
            model.addAttribute("totalCartItem", gioHangService.countCartItem()); // trả ra tổng số lượng giỏ hàng chi tiết theo user
        }

        model.addAttribute("tongTien", tongTien);
        model.addAttribute("gioHangChiTietList", gioHangChiTietList);

        model.addAttribute("khachHang", Authen.khachHang);
        //hàm format
        model.addAttribute("convertMoney", dataUtils);
        return "view/list-product/list-product-page";
    }
}
