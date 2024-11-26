package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.payment.VNPayRequest;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/dat-hang/")
public class DatHangController {
    @Autowired
    private DiaChiService diaChiService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private TaiKhoanService taiKhoanService;
    @Autowired
    private GioHangChiTietService gioHangChiTietService;
    @Autowired
    KhuyenMaiService khuyenMaiService;
    @Autowired
    DatHangService datHangService;
    @Autowired
    VNpayService vnpayService;

    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;

    @Autowired
    private CoAoService coAoService ;

    @Autowired
    GioHangService gioHangService;

    //khai báo biến toàn cục
    List<String> listProductDetailSelectedInCart = null;
    Float totalPrice = 0f;
    Integer idDiaChiSelected = null;
    String thanhToanSelected = null;
    KhuyenMai khuyenMaiSelected = null;
    Float phiVanChuyen = 0f;

    @GetMapping("/dia-chi-giao-hang")
    public String helloDiachigiaohang(Model model, @RequestParam(name = "productCheck", required = false) List<String> productCheck) {
        //lưu biến toàn cục những sản phẩm đã chọn
        listProductDetailSelectedInCart = productCheck;
        ArrayList<GioHangChiTiet> listCartDetailItem = new ArrayList<>();
        for (int i = 0; i < productCheck.size(); i++) {
            Integer idCartDetailItem = Integer.parseInt(productCheck.get(i));
            listCartDetailItem.add(gioHangChiTietService.getCartItemByCartItemId(idCartDetailItem));
        }
        //lưu biến toàn cục tổng tiền sản phẩm đã chọn
        totalPrice = 0f;
        for (GioHangChiTiet gioHangChiTiet : listCartDetailItem) {
            totalPrice = totalPrice + (gioHangChiTiet.getChiTietSanPham().getDonGia() * gioHangChiTiet.getSoLuong());
        }

        //danh sách sản phẩm đã chọn
        model.addAttribute("listSpSelected", listCartDetailItem);
        //tổng tiền
        model.addAttribute("totalPrice", totalPrice);

        //hiển thị địa chỉ
        List<DiaChi> diachis = diaChiService.getAllActiveDiaChiByUser(Authen.khachHang);
        model.addAttribute("diaChiList", diachis);
        model.addAttribute("diaChiCre", new DiaChi());

        //Hiển thị khuyến mại
        model.addAttribute("listKM", khuyenMaiService.getAllKhuyenMai());
        //hàm format
//        Các dữ liệu cần cho header
// Hiển thị danh mục
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
        model.addAttribute("convertMoney", new DataUtils());
        // các hàm cho phần header
        return "dia-chi-giao-hang";
    }

    @PostMapping("/add-dia-chi-dat-hang")
    public String addDiaChi(@Valid @ModelAttribute("diaChiCre") DiaChi diaChi, BindingResult result,
                            RedirectAttributes attributes, HttpServletRequest request) {

        if(diaChiService.saveDiaChi(diaChi)) {
            attributes.addFlashAttribute("message", "Thêm mới địa chỉ thành công.");
            attributes.addFlashAttribute("messageType", "alert-success");
            attributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            attributes.addFlashAttribute("message", "Số điện thoại đã tồn tại hoặc dữ liệu không đúng");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" + referer;
    }

    @PostMapping("/update-dia-chi-dat-hang")
    private String updateDiaChi(@Valid @ModelAttribute("diaChiCre") DiaChi diaChi, BindingResult result,
                                RedirectAttributes attributes, HttpServletRequest request) {
        if(diaChiService.updateDiaChi(diaChi, diaChi.getId())) {
            attributes.addFlashAttribute("message", "Cập nhật địa chỉ thành công.");
            attributes.addFlashAttribute("messageType", "alert-success");
            attributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            attributes.addFlashAttribute("message", "Số điện thoại đã tồn tại hoặc dữ liệu không đúng");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" + referer;
    }

    // Xóa địa chỉ
    @GetMapping("/delete-dia-chi-dat-hang/{id}")
    public String deleteDiaChi(@PathVariable("id") Integer idDiaChi, RedirectAttributes attributes,
                               HttpServletRequest request) {
        diaChiService.deleteDiaChi(idDiaChi);
        attributes.addFlashAttribute("message", "Xóa địa chỉ thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        attributes.addFlashAttribute("titleMsg", "Thành công");
        //get url request
        String referer = request.getHeader("referer");
        //reload page

        List<DanhMuc> danhMucs = danhMucService.getAllDanhMucHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();

        attributes.addFlashAttribute("listDanhMuc" , danhMucs);
        attributes.addFlashAttribute("listCoAo" , listCoAo);
        attributes.addFlashAttribute("listTayAo" , listTayAo);

        return "redirect:" + referer;
    }


    @PostMapping("/chon-di-chi")
    public String chonDiaChi(@RequestParam(value = "diaChiSelected", required = false) Integer diaChiSelected,
                             @RequestParam(value = "shipFee", required = false) Float ship,
                             RedirectAttributes attributes,
                             HttpServletRequest request) {
        idDiaChiSelected = diaChiSelected;
        phiVanChuyen = ship;
        return "redirect:/dat-hang/phuong-thuc-thanh-toan";
    }

    @GetMapping("/phuong-thuc-thanh-toan")
    public String helloPhuongthucthanhtoan(Model model) {
        ArrayList<GioHangChiTiet> listCartDetailItem = new ArrayList<>();
        for (int i = 0; i < listProductDetailSelectedInCart.size(); i++) {
            Integer idCartDetailItem = Integer.parseInt(listProductDetailSelectedInCart.get(i));
            listCartDetailItem.add(gioHangChiTietService.getCartItemByCartItemId(idCartDetailItem));
        }

        //danh sách sản phẩm đã chọn
        model.addAttribute("listSpSelected", listCartDetailItem);
        //tổng tiền
        model.addAttribute("totalPrice", totalPrice);


        //Hiển thị khuyến mại
        model.addAttribute("listKM", khuyenMaiService.getAllKhuyenMai());
        //khuyến mại đã chọn
        model.addAttribute("khuyenMaiSelected", khuyenMaiSelected);
        //phí vận chuyển
        model.addAttribute("phiVanChuyen", phiVanChuyen);
        //hàm format

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
        model.addAttribute("convertMoney", new DataUtils());
        return "phuong-thuc-thanh-toan";
    }

    @PostMapping("/chon-thanh-toan")
    public String chonThanhToan(@RequestParam(value = "paymentMethod", required = false) String paymentMethod, RedirectAttributes attributes,
                                HttpServletRequest request) {
        thanhToanSelected = paymentMethod;
        return "redirect:/dat-hang/review";
    }

    @GetMapping("/add-khuyen-mai")
    public String addKhuyenMai(HttpServletRequest request,
                               @RequestParam Integer idKhuyenMai,
                               Model model, RedirectAttributes redirectAttributes) {
        khuyenMaiSelected = khuyenMaiService.getKhuyenMaiById(idKhuyenMai);
        //khuyến mại đã chọn
        model.addAttribute("khuyenMaiSelected", khuyenMaiSelected);

        //get url request
        String referer = request.getHeader("referer");
        //reload page
        return "redirect:" + referer;
    }

    @GetMapping("/review")
    public String helloReview(Model model) {
        ArrayList<GioHangChiTiet> listCartDetailItem = new ArrayList<>();
        for (int i = 0; i < listProductDetailSelectedInCart.size(); i++) {
            Integer idCartDetailItem = Integer.parseInt(listProductDetailSelectedInCart.get(i));
            listCartDetailItem.add(gioHangChiTietService.getCartItemByCartItemId(idCartDetailItem));
        }

        //danh sách sản phẩm đã chọn
        model.addAttribute("listSpSelected", listCartDetailItem);
        //tổng tiền
        model.addAttribute("totalPrice", totalPrice);

        //hiển thị địa chỉ đã chọn
        DiaChi diaChiselected = diaChiService.getDiaChiById(idDiaChiSelected);
        model.addAttribute("diaChi", diaChiselected);
        //khuyến mại đã chọn
        model.addAttribute("khuyenMaiSelected", khuyenMaiSelected);
        //hàm format
        //phương thức thanh toán đã chọn
        model.addAttribute("payMethod", thanhToanSelected);
        //phis vaanj chuyen
        model.addAttribute("phiVanChuyen", phiVanChuyen);
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
        model.addAttribute("convertMoney", new DataUtils());

        return "review";
    }

    @GetMapping("/dat-hang-online")
    public String datHangOnline(Model model, HttpServletRequest reqs,
                                HttpServletResponse response, RedirectAttributes attributes) throws Exception {
        ArrayList<GioHangChiTiet> listCartDetailItem = new ArrayList<>();
//        dành cho header
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
        model.addAttribute("convertMoney", new DataUtils());
//        kết thúc dành cho header
        for (int i = 0; i < listProductDetailSelectedInCart.size(); i++) {
            Integer idCartDetailItem = Integer.parseInt(listProductDetailSelectedInCart.get(i));
            listCartDetailItem.add(gioHangChiTietService.getCartItemByCartItemId(idCartDetailItem));
        }
        if (thanhToanSelected.equals("offline")) {
            boolean check;
            if(khuyenMaiSelected == null) {
                 check = datHangService.datHangOnline(listCartDetailItem, idDiaChiSelected, null, thanhToanSelected, totalPrice, phiVanChuyen);
            } else {
                 check = datHangService.datHangOnline(listCartDetailItem, idDiaChiSelected, khuyenMaiSelected.getId(), thanhToanSelected, totalPrice, phiVanChuyen);
            }
            if (check) {

                return "SuscessOrder";
            } else {
                attributes.addFlashAttribute("message", "Vui lòng kiểm tra lại số lượng.");
                attributes.addFlashAttribute("messageType", "alert-danger");
                attributes.addFlashAttribute("titleMsg", "Thất bại");

                return "FailOrder";
            }
        } else {
            VNPayRequest request = new VNPayRequest();
            float amount = (float) DataUtils.calculatorTotal2(totalPrice, khuyenMaiSelected, phiVanChuyen);
            DecimalFormat df = new DecimalFormat("#.##"); // Định dạng số thập phân với tối đa hai chữ số sau dấu chấm
            String formattedTotalAmount = df.format(amount);
            request.setAmount(formattedTotalAmount);
            //request.setBankCode("NCB");
//            request.setOrderIdSuccess(String.valueOf(idOrder));
            // Gán các giá trị khác cho request
            String paymentUrl = vnpayService.createPayment(request, reqs);
            response.sendRedirect(paymentUrl);
        }

        return "FailOrder";
    }

    @GetMapping("/payment-info")
    public String paymentInfo(Model model, RedirectAttributes attributes,
                              @RequestParam("vnp_ResponseCode") String status,
                              @RequestParam("vnp_BankCode") String bankCode,
                              @RequestParam("vnp_Amount") String amount,
                              @RequestParam("vnp_OrderInfo") String vnp_OrderInfo

    ) {
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
        model.addAttribute("convertMoney", new DataUtils());


        if (status.equals("00")) {
            ArrayList<GioHangChiTiet> listCartDetailItem = new ArrayList<>();
            for (int i = 0; i < listProductDetailSelectedInCart.size(); i++) {
                Integer idCartDetailItem = Integer.parseInt(listProductDetailSelectedInCart.get(i));
                listCartDetailItem.add(gioHangChiTietService.getCartItemByCartItemId(idCartDetailItem));
            }
            boolean check;
            if(khuyenMaiSelected == null) {
                check = datHangService.datHangOnline(listCartDetailItem, idDiaChiSelected, null, thanhToanSelected, totalPrice, phiVanChuyen);
            } else {
                check = datHangService.datHangOnline(listCartDetailItem, idDiaChiSelected, khuyenMaiSelected.getId(), thanhToanSelected, totalPrice, phiVanChuyen);
            }
            if(check) {
                totalPrice = 0f;

                return "SuscessOrder";
            } else {
                attributes.addFlashAttribute("message", "Vui lòng kiểm tra lại số lượng.");
                attributes.addFlashAttribute("messageType", "alert-danger");
                attributes.addFlashAttribute("titleMsg", "Thất bại");

                return "FailOrder";
            }
        }
        totalPrice = 0f;

        return "FailOrder";
    }

}
