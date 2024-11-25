package com.example.kristp.controller.admin;

import com.example.kristp.entity.*;
import com.example.kristp.service.*;
import com.example.kristp.utils.Pagination;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    KhachHangService khachHangService;
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
        model.addAttribute("khachHangCre", new KhachHang());

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
        model.addAttribute("tongTienSauGiam", banHangService.findTongTienKhuyenMai(hoaDonSelected));
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
//        if (hoaDonService.taoHoaDon()) {
//            redirectAttributes.addFlashAttribute("message", "Tạo hóa đơn chờ thành công!");
//            redirectAttributes.addFlashAttribute("messageType", "alert-success");
//            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
//        } else {
//            redirectAttributes.addFlashAttribute("message", "Hóa đơn chờ đã đạt giới hạn!");
//            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
//            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
//        }
        hoaDonService.taoHoaDon();

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
                              @RequestParam(value = "chiTietSanPhamId", required = false) Integer chiTietSanPhamId,
                              @RequestParam(value = "qrCode", required = false) String qrCode,
                              @RequestParam("soLuong") Integer soLuong,
                              RedirectAttributes redirectAttributes) {
        System.out.println("qrcode: " + qrCode);

        if (hoaDonSelected == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn hóa đơn thao tác!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");

        } else {
            banHangService.addGioHang(hoaDonSelected, chiTietSanPhamId, qrCode, soLuong);
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }

    @PostMapping("/them-khach-hang")
    public String themKhachHang(HttpServletRequest request, Model model,
                                RedirectAttributes redirectAttributes,
                                @RequestParam(value = "soDienThoai", required = false) String soDienThoai,
                                @RequestParam(value = "tenKhachHang", required = false) String tenKhachHang) {

        // Kiểm tra xem có hóa đơn đang chọn không
        if (hoaDonSelected == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn hóa đơn thao tác!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
            redirectAttributes.addFlashAttribute("soDienThoai", soDienThoai);
            redirectAttributes.addFlashAttribute("tenKhachHang", tenKhachHang);
        } else {
            try {

                // Kiểm tra nếu khách hàng đã tồn tại theo số điện thoại
                KhachHang khachHang = khachHangService.findBySoDienThoai(soDienThoai);
                if (khachHang != null) {
                    // Nếu đã có khách hàng, điền tên khách hàng
                    tenKhachHang = khachHang.getTenKhachHang();
                } else {
                    // Nếu chưa có khách hàng, tạo mới khách hàng
                    KhachHang khachHangMoi = new KhachHang();
                    // Kiểm tra nếu số điện thoại và tên khách hàng không được nhập
//                    if (soDienThoai == null || soDienThoai.trim().isEmpty()) {
//                        khachHangMoi.setSoDienThoai("Trống");  // Gán mặc định nếu không nhập số điện thoại
//                    }
//
//                    if (tenKhachHang == null || tenKhachHang.trim().isEmpty()) {
//                        khachHangMoi.setTenKhachHang("Khách vãng lai");  // Gán mặc định nếu không nhập tên khách hàng
//                    }
                    khachHangMoi.setSoDienThoai(soDienThoai);
                    khachHangMoi.setTenKhachHang(tenKhachHang);
                    khachHangService.addKhachHang(khachHangMoi);
                }
                // Thêm khách hàng vào hóa đơn
                banHangService.addKhachHang(null, soDienThoai, tenKhachHang, hoaDonSelected);
                model.addAttribute("message", "Thêm khách hàng thành công!");
                model.addAttribute("messageType", "alert-success");
                model.addAttribute("titleMsg", "Thành công");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "Đã xảy ra lỗi: " + e.getMessage());
                redirectAttributes.addFlashAttribute("messageType", "alert-danger");
                redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
                redirectAttributes.addFlashAttribute("soDienThoai", soDienThoai);
                redirectAttributes.addFlashAttribute("tenKhachHang", tenKhachHang);
            }
        }
        KhachHang khachHangCre = new KhachHang();
        khachHangCre.setSoDienThoai(soDienThoai);
        khachHangCre.setTenKhachHang(tenKhachHang);
        model.addAttribute("khachHangCre", khachHangCre);

        // Reload trang hiện tại
        String referer = request.getHeader("referer");
        return "redirect:" + referer;
    }

    @GetMapping("/kiem-tra-khach-hang")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> kiemTraKhachHang(@RequestParam("soDienThoai") String soDienThoai,
                                                                HttpServletRequest request, Model model,
                                                                RedirectAttributes redirectAttributes) {
        Map<String, Object> response = new HashMap<>();

        // Tìm khách hàng theo số điện thoại
        KhachHang khachHang = khachHangService.findBySoDienThoai(soDienThoai);

        if (khachHang != null) {
            response.put("exists", true);
            response.put("tenKhachHang", khachHang.getTenKhachHang());

        } else {
            response.put("exists", false);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/them-nhanh-khach-hang")
    private String addKH(
            @Valid @ModelAttribute("khachHang") KhachHang khachHang,
            BindingResult result,
            RedirectAttributes attributes) {

        // Kiểm tra lỗi validation
        if (result.hasErrors()) {
            attributes.addFlashAttribute("khachHang", khachHang);
            attributes.addFlashAttribute("message", "Thêm mới khách hàng không thành công. Vui lòng kiểm tra thông tin nhập.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly/ban-hang";
        }
        // Kiểm tra độ dài số điện thoại
        String soDienThoai = khachHang.getSoDienThoai();
        if (!soDienThoai.matches("\\d{10,13}")) { // Regex chỉ chấp nhận số từ 10 đến 13 ký tự
            attributes.addFlashAttribute("khachHang", khachHang);
            attributes.addFlashAttribute("message", "Số điện thoại phải có từ 10 đến 13 chữ số.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly/ban-hang";
        }

        // Kiểm tra số điện thoại đã tồn tại
        if (khachHangService.isSoDienThoaiExists(khachHang.getSoDienThoai())) {
            attributes.addFlashAttribute("khachHang", khachHang);
            attributes.addFlashAttribute("message", "Số điện thoại đã tồn tại.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly/ban-hang";
        }

        // Lưu khách hàng
        khachHangService.addKhachHang(khachHang);
        attributes.addFlashAttribute("message", "Thêm mới khách hàng thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        attributes.addFlashAttribute("titleMsg", "Thành công");
        return "redirect:/quan-ly/ban-hang";
    }


    @GetMapping("/add-khuyen-mai")
    public String addKhuyenMai(HttpServletRequest request,
                               @RequestParam Integer idKhuyenMai,
                               Model model, RedirectAttributes redirectAttributes) {
        if (banHangService.addKhuyenMai(idKhuyenMai, hoaDonSelected)) {
            redirectAttributes.addFlashAttribute("message", "Áp dụng khuyến mại thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Áp dụng khuyến mại thất bại!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }

    @GetMapping("/xoa-gio-hang")
    public String xoaGioHang(HttpServletRequest request,
                               @RequestParam Integer inHoaDonChiTiet,
                               Model model, RedirectAttributes redirectAttributes) {
        if (banHangService.xoaSanPhamGioHang(inHoaDonChiTiet)) {
            redirectAttributes.addFlashAttribute("message", "Xóa sản phẩm thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Xóa sản phẩm thất bại!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }

    @PostMapping("/cap-nhat-gio-hang-hoa-don")
    public String capNhatGioHangHoaDon(HttpServletRequest request,
                             @RequestParam("inHoaDonChiTiet") Integer inHoaDonChiTiet,
                             @RequestParam("soLuong") Integer soLuong,
                             Model model, RedirectAttributes redirectAttributes) {
        if (banHangService.updateSoLuongGioHang(inHoaDonChiTiet, soLuong)) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật sản phẩm trong giỏ hàng thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            redirectAttributes.addFlashAttribute("message", "Cập nhật sản phẩm trong giỏ hàng thất bại!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" +referer;
    }

}
