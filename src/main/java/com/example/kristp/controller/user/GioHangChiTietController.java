package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.repository.GioHangChiTietRepository;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user/")
public class GioHangChiTietController {
    @Autowired
    private GioHangChiTietService gioHangChiTietService;
    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;
    @Autowired
    HoaDonService hoaDonService;
    @Autowired
    BanHangService banHangService;
    @Autowired
    KhuyenMaiService khuyenMaiService;
    @Autowired
    GioHangService gioHangService;
    @Autowired
    DataUtils dataUtils;

    HoaDon hoaDonSelected = null;

    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;

    @Autowired
    private CoAoService coAoService ;

    // Hiển thị giỏ hàng chi tiết
    @GetMapping("/gio-hang-chi-tiet")
    public String showGioHangChiTiet(Model model, @RequestParam(value = "idHoaDon", required = false) Integer idHoaDon) {
        GioHang gioHang = gioHangService.findGioHangByKhachHangId(Authen.khachHang);
        List<GioHangChiTiet> gioHangChiTietList = gioHangChiTietService.getGioHangChiTietByGioHangId(gioHang.getId());
        model.addAttribute("gioHangChiTietList", gioHangChiTietList);
        List<HoaDon> hoaDons = hoaDonService.findAllHoaDonCho();
        System.out.println(hoaDons.get(0).getId());
        if (hoaDonSelected == null) {
            hoaDonSelected = hoaDons.get(0);

        } else {
            if (idHoaDon != null) {
                hoaDonSelected = hoaDonService.findHoaDonById(idHoaDon);
            }
        }
        model.addAttribute("hoaDonSelected", hoaDonSelected);

        //khuyến mại
        model.addAttribute("listKM", khuyenMaiService.getAllKhuyenMai());
        //hàm format

        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        model.addAttribute("listDanhMuc" , danhMucs);
        model.addAttribute("listCoAo" , listCoAo);
        model.addAttribute("listTayAo" , listTayAo);

        float tongTien = 0;
        ArrayList<GioHangChiTiet> gioHangChiTietListHeader = null;
        if(Authen.khachHang != null) {
            GioHang gioHangHeader = gioHangService.findGioHangByKhachHangId(Authen.khachHang);
            gioHangChiTietListHeader = gioHangChiTietService.getAllGioHangChiTiet(gioHangHeader.getId());
            for (GioHangChiTiet gioHangChiTiet : gioHangChiTietListHeader) {
                tongTien = tongTien + (gioHangChiTiet.getChiTietSanPham().getDonGia() * gioHangChiTiet.getSoLuong());
            }
            model.addAttribute("totalCartItem", gioHangService.countCartItem()); // trả ra tổng số lượng giỏ hàng chi tiết theo user
        }

        model.addAttribute("tongTien", tongTien);
        model.addAttribute("gioHangChiTietListHeader", gioHangChiTietListHeader);

        model.addAttribute("khachHang", Authen.khachHang);
        //hàm format
        model.addAttribute("convertMoney", new DataUtils());

        return "gio-hang";
    }

    @PostMapping("/cap-nhat-so-luong")
    @ResponseBody
    public ResponseEntity<?> capNhatSoLuong(@RequestBody Map<String, Object> payload) {
        Integer id = Integer.valueOf(payload.get("id").toString());
        Integer increment = Integer.valueOf(payload.get("increment").toString());

        // Cập nhật số lượng
        int newQuantity = gioHangChiTietService.capNhatSoLuong(id, increment);

        return ResponseEntity.ok(Map.of("newQuantity", newQuantity));
    }

    // Xóa giỏ hàng chi tiết
    @GetMapping("/xoa-gio-hang-chi-tiet/{id}")
    public String deleteGioHangChiTiet(@PathVariable Integer id,
                                       HttpServletRequest request,
                                       Model model) {
        gioHangChiTietService.deleteGioHangChiTiet(id);
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" + referer;
    }

    @PostMapping("/dat-hang")
    public String datHang(@RequestParam(value = "selectedIds", required = false) List<String> selectedIds, RedirectAttributes attributes,
                                     HttpServletRequest request) {
        if (selectedIds == null || selectedIds.isEmpty()) {
            attributes.addFlashAttribute("message", "Chưa chọn sản phẩm thao tác");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            //get url request
            String referer = request.getHeader("referer");
            //reload page
            return "redirect:" + referer;
        }

        // chuyền danh sách id đã chọn sang trang đặt hàng
        String productCheckParam = String.join(",", selectedIds);
        return "redirect:/user/dia-chi-giao-hang?productCheck=" + productCheckParam;
    }
}
