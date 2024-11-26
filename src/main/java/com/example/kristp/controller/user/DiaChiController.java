package com.example.kristp.controller.user;

import com.example.kristp.entity.*;
import com.example.kristp.repository.KhachHangRepository;
import com.example.kristp.service.*;
import com.example.kristp.utils.Authen;
import com.example.kristp.utils.DataUtils;
import com.example.kristp.utils.Pagination;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user-dia-chi/")
public class DiaChiController {
    @Autowired
    private DiaChiService diaChiService;

    @Autowired
    private DanhMucService danhMucService ;

    @Autowired
    private TayAoService tayAoService ;


    @Autowired
    private CoAoService coAoService ;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private GioHangChiTietService gioHangChiTietService;

    @Autowired
    private KhachHangRepository khachHangRepository ;

    // Hiển thị danh sách địa chỉ của khách hàng với phân trang
    @GetMapping("/pagination-dia-chi")
    public String getPagination(Model model,@RequestParam("page") Optional<Integer> page) {
        Pagination pagination = new Pagination();
        Pageable pageable = PageRequest.of(page.orElse(0), 3);
        Page<DiaChi> diachis = diaChiService.getAllActiveDiaChi(pageable); // Giả sử mỗi trang có 5 địa chỉ
        model.addAttribute("diaChiList", diachis.getContent());
        Optional<KhachHang> khachHang = khachHangRepository.findById(Authen.khachHang.getId());
        model.addAttribute("khachHang" , khachHang.get());
        //phân trang
        model.addAttribute("totalPage", diachis.getTotalPages() - 1);
        model.addAttribute("page", diachis);
        model.addAttribute("pagination", pagination.getPage(diachis.getNumber(), diachis.getTotalPages()));
        model.addAttribute("diaChiCre", new DiaChi());
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
        return "view-admin/dashbroad/crud-dia-chi";
    }

    @GetMapping("/find-dia-chi")
    @ResponseBody
    public ResponseEntity<?> getDiaChi(Model model, @RequestParam("id") Integer id) {
        DiaChi  diaChi = diaChiService.getDiaChiById(id);
        return ResponseEntity.ok(diaChi);
    }


    @PostMapping("/add-dia-chi")
    public String addDiaChi(@Valid @ModelAttribute("diaChiCre") DiaChi diaChi, BindingResult result,
                            RedirectAttributes attributes) {
        if(diaChiService.saveDiaChi(diaChi)) {
            attributes.addFlashAttribute("message", "Thêm mới địa chỉ thành công.");
            attributes.addFlashAttribute("messageType", "alert-success");
            attributes.addFlashAttribute("titleMsg", "Thành công");
        } else {
            attributes.addFlashAttribute("message", "Số điện thoại đã tồn tại hoặc dữ liệu không đúng");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
        }
        return "redirect:/user-dia-chi/pagination-dia-chi";
    }

@PostMapping("/update-dia-chi")
private String updateDiaChi(@Valid @ModelAttribute("diaChiCre") DiaChi diaChi, BindingResult result, RedirectAttributes attributes) {

    if(diaChiService.updateDiaChi(diaChi, diaChi.getId())) {
        attributes.addFlashAttribute("message", "Cập nhật địa chỉ thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        attributes.addFlashAttribute("titleMsg", "Thành công");
    } else {
        attributes.addFlashAttribute("message", "Số điện thoại đã tồn tại hoặc dữ liệu không đúng");
        attributes.addFlashAttribute("messageType", "alert-danger");
        attributes.addFlashAttribute("titleMsg", "Thất bại");
    }
    return "redirect:/user-dia-chi/pagination-dia-chi";
}


    // Xóa địa chỉ
    @GetMapping("/delete-dia-chi/{id}")
    public String deleteDiaChi(@PathVariable("id") Integer idDiaChi, RedirectAttributes attributes) {
        diaChiService.deleteDiaChi(idDiaChi);
        attributes.addFlashAttribute("message", "Xóa địa chỉ thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        return "redirect:/user-dia-chi/pagination-dia-chi";
    }
}
