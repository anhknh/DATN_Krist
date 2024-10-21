package com.example.kristp.controller.admin;

import com.example.kristp.entity.HoaDon;
import com.example.kristp.entity.SanPham;
import com.example.kristp.service.*;
import com.example.kristp.utils.Pagination;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                          @RequestParam("page") Optional<Integer> page) {
        Pagination pagination = new Pagination();
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<SanPham> listSanPham = timKiemSanPhamService.timKiemSanPham(tenSanPham,danhMucId,chatLieuId,
                tayAoId,coAoId,mauSacId,sizeId,pageable);
        model.addAttribute("totalPage", listSanPham.getTotalPages() - 1);
        model.addAttribute("page", listSanPham);
        model.addAttribute("pagination", pagination.getPage(listSanPham.getNumber(), listSanPham.getTotalPages()));
        model.addAttribute("listSanPham", listSanPham.getContent());
        //load bill
        List<HoaDon> hoaDons = hoaDonService.findAllHoaDonCho();
        if (hoaDonSelected == null) {
            hoaDonSelected = hoaDons.get(0);
        } else {
            hoaDonSelected = hoaDonService.findHoaDonById(idHoaDon);
        }
        model.addAttribute("listHoaDon", hoaDons);
        model.addAttribute("hoaDonSelected", hoaDonSelected);

        //load filter
        model.addAttribute("listDanhMuc", danhMucService.getAllDanhMucHD());
        model.addAttribute("listChatLieu", chatLieuService.getAllChatLieuHD());
        model.addAttribute("listTayAo", tayAoService.getAllTayAoHD());
        model.addAttribute("listCoAo", coAoService.getAllCoAoHD());
        model.addAttribute("listMauSac", mauSacService.getAllMauSacHD());
        model.addAttribute("listSize", sizeService.getAllSizeHD());
        model.addAttribute("chiTietSanPhamService", chiTietSanPhamService);
        System.out.println(idHoaDon);
        return "view-admin/dashbroad/ban-hang";
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

}
