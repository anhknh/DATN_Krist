package com.example.kristp.controller.user;


import com.example.kristp.entity.*;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import com.example.kristp.utils.Pagination;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class SanPhamYeuThichController {

    @Autowired
    SanPhamYeuThichService sanPhamYeuThichService;

    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;

    @Autowired
    private CoAoService coAoService ;
    @Autowired
    DataUtils dataUtils;

    @Autowired
    GioHangService gioHangService;

    @Autowired
    private GioHangChiTietService gioHangChiTietService;
    @Autowired
    HoaDonChiTietService hoaDonChiTietService;


    @GetMapping("/view-yeu-thich")
    public String viewYeuThich(@RequestParam("page") Optional<Integer> page,
                               Model model) {
        Pagination pagination = new Pagination();
        Pageable pageable = PageRequest.of(page.orElse(0), 3);
        Page<SanPhamYeuThich> pageYeuThich = sanPhamYeuThichService.pageSanPhamYeuThich(Authen.khachHang, pageable);
        model.addAttribute("totalPage", pageYeuThich.getTotalPages() - 1);
        model.addAttribute("page", pageYeuThich);
        model.addAttribute("pagination", pagination.getPage(pageYeuThich.getNumber(), pageYeuThich.getTotalPages()));
        model.addAttribute("listYeuThich", pageYeuThich.getContent());


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

        model.addAttribute("khachHang1", Authen.khachHang);
        model.addAttribute("hoaDonChiTietService", hoaDonChiTietService);
        model.addAttribute("convertMoney", new DataUtils());
        return "/danh-sach-yeu-thich";
    }


    @GetMapping("/them-san-pham-yeu-thich")
    public String themSanPhamYeuThich(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,
                                      @RequestParam("idSanPham") Integer idSanPham) {
        if (sanPhamYeuThichService.addSanPhamYeuThich(idSanPham)) {
            redirectAttributes.addFlashAttribute("message", "Lưu sản phẩm yêu thích thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Thêm sản phẩm yêu thích thất bại!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" + referer;
    }

    @GetMapping("/xoa-san-pham-yeu-thich")
    public String xoaSanPhamYeuThich(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,
                                     @RequestParam("idSanPham") Integer idSanPham) {
        if (sanPhamYeuThichService.deleteSanPhamYeuThich(idSanPham)) {
            redirectAttributes.addFlashAttribute("message", "Gỡ sản phẩm yêu thích thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Gỡ sản phẩm yêu thích thất bại!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" + referer;
    }
}
