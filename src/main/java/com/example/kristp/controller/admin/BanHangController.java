package com.example.kristp.controller.admin;

import com.example.kristp.entity.*;
import com.example.kristp.service.*;
import com.example.kristp.utils.Pagination;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quan-ly")
public class BanHangController {

    @Autowired
    TimKiemSanPhamService timKiemSanPhamService;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
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
    HoaDonService hoaDonService;
    @Autowired
    HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    BanHangService banHangService;
    @Autowired
    KhuyenMaiService khuyenMaiService;
    HoaDon hoaDonSelected = null;


    @GetMapping("/ban-hang")
    public String banHang(Model model,
                          @RequestParam(value = "idHoaDon", required = false) Integer idHoaDon,
                          @RequestParam(value = "tenSanPham", required = false) String tenSanPham,
                          @RequestParam(value = "danhMucId", required = false) List<Integer> danhMucId,
                          @RequestParam(value = "chatLieuId", required = false) List<Integer>  chatLieuId,
                          @RequestParam(value = "tayAoId", required = false) List<Integer>  tayAoId,
                          @RequestParam(value = "coAoId", required = false) List<Integer>  coAoId,
                          @RequestParam(value = "mauSacId", required = false) List<Integer>  mauSacId,
                          @RequestParam(value = "sizeId", required = false) List<Integer>  sizeId,
                          @RequestParam("pageHd") Optional<Integer> pageHd,
                          @RequestParam("page") Optional<Integer> page) {
        Pagination pagination = new Pagination();
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Pageable pageablehd = PageRequest.of(pageHd.orElse(0), 5);
        Page<SanPham> listSanPham = timKiemSanPhamService.timKiemSanPham(tenSanPham,danhMucId,chatLieuId,
                tayAoId,coAoId,mauSacId,sizeId,pageable);
        //load bill
        List<HoaDon> hoaDons = hoaDonService.findAllHoaDonCho();
        if (hoaDonSelected == null) {
            hoaDonSelected = hoaDons.get(0);
        } else {
            if(idHoaDon != null) {
                hoaDonSelected = hoaDonService.findHoaDonById(idHoaDon);
            }
        }
        model.addAttribute("listHoaDon", hoaDons);
        model.addAttribute("hoaDonSelected", hoaDonSelected);

        Page<HoaDonChiTiet> listHoaDonChiTiet = hoaDonChiTietService.getHoaDonChiTietByHoaDon(hoaDonSelected, pageablehd);
        model.addAttribute("totalPage", listSanPham.getTotalPages() - 1);
        model.addAttribute("page", listSanPham);
        model.addAttribute("pagination", pagination.getPage(listSanPham.getNumber(), listSanPham.getTotalPages()));
        model.addAttribute("listSanPham", listSanPham.getContent());

        //page hoa don

        model.addAttribute("totalPageHd", listHoaDonChiTiet.getTotalPages() - 1);
        model.addAttribute("pagehd", listHoaDonChiTiet);
        model.addAttribute("paginationHd", pagination.getPage(listHoaDonChiTiet.getNumber(), listHoaDonChiTiet.getTotalPages()));
        model.addAttribute("listHoaDonChiTiet", listHoaDonChiTiet.getContent());

        //load filter
        model.addAttribute("listDanhMuc", danhMucService.getAllDanhMucHD());
        model.addAttribute("listChatLieu", chatLieuService.getAllChatLieuHD());
        model.addAttribute("listTayAo", tayAoService.getAllTayAoHD());
        model.addAttribute("listCoAo", coAoService.getAllCoAoHD());
        model.addAttribute("listMauSac", mauSacService.getAllMauSacHD());
        model.addAttribute("listSize", sizeService.getAllSizeHD());
        model.addAttribute("chiTietSanPhamService", chiTietSanPhamService);
        //thông tin hóa đơn
        model.addAttribute("tongTien", banHangService.getTongTien(hoaDonSelected));
        //khuyến mại
        model.addAttribute("listKM", khuyenMaiService.getAllKhuyenMai());
        return "view-admin/dashbroad/ban-hang";
    }

    // Trả về chi tiet san pham theo productId
    @GetMapping("/chi-tiet-san-pham/{productId}")
    @ResponseBody
    public List<ChiTietSanPham> getCTSPByProductId(@PathVariable Integer productId) {
        return chiTietSanPhamService.getProductDetailsByProductId(productId);
    }

    // Trả về danh sách màu sắc theo productId
    @GetMapping("/colors/{productId}")
    @ResponseBody
    public List<MauSac> getColorsByProductId(@PathVariable Integer productId) {
        return chiTietSanPhamService.getColorsByProductId(productId);
    }

    // Trả về danh sách size theo productId
    @GetMapping("/sizes/{productId}")
    @ResponseBody
    public List<Size> getSizesByProductId(@PathVariable Integer productId) {
        return chiTietSanPhamService.getSizesByProductId(productId);
    }

    // Trả về chi tiết sản phẩm theo productId, colorId và sizeId
    @GetMapping("/details/{productId}/{colorId}/{sizeId}")
    @ResponseBody
    public ChiTietSanPham getProductDetailByColorAndSize(@PathVariable Integer productId,
                                                         @PathVariable Integer colorId,
                                                         @PathVariable Integer sizeId) {
        return chiTietSanPhamService.getProductDetailByColorAndSize(productId, colorId, sizeId);
    }


    @GetMapping("/tao-hoa-don")
    public String taoHoaDon(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (hoaDonService.taoHoaDon()) {
            redirectAttributes.addFlashAttribute("message", "Tạo hóa đơn chờ thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Hóa đơn chờ đã đạt giới hạn!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }


        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }

    @GetMapping("/thanh-toan-hoa-don")
    public String thanhToanHoaDon(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (hoaDonService.thanhToanHoaDon(hoaDonSelected)) {
            redirectAttributes.addFlashAttribute("message", "Thanh toán hóa đơn thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Thanh toán hóa đơn thất bại!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }


        return "redirect:/quan-ly/ban-hang";
    }

    @PostMapping("/them-gio-hang")
    public String themGioHang(HttpServletRequest request, Model model,
                              @RequestParam("chiTietSanPhamId") Integer chiTietSanPhamId,
                              @RequestParam("soLuong") Integer soLuong,
                              RedirectAttributes redirectAttributes) {


        if (hoaDonSelected == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn hóa đơn thao tác!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");

        } else {
            banHangService.addGioHang(hoaDonSelected, chiTietSanPhamId, soLuong);
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }

}
