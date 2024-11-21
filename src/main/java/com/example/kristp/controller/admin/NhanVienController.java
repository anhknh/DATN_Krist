package com.example.kristp.controller.admin;

import com.example.kristp.entity.NhanVien;
import com.example.kristp.service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quan-ly-nhan-vien")
public class NhanVienController {
    @Autowired
    NhanVienService nhanVienService;

    @GetMapping("/list-nhan-vien")
    private String getAllNhanVien(RedirectAttributes attributes){
        attributes.addFlashAttribute("listNV", nhanVienService.getAllNhanVien());
        return "view-admin/dashbroad/crud-nhan-vien";
    }

    @GetMapping("/phan-trang-nhan-vien")
    private String getPhanTrang(Model model, @RequestParam(name = "pageNo", defaultValue = "0")Integer pageNo){
        Page<NhanVien> nhanViens = nhanVienService.getPaginationNhanVien(pageNo);
        model.addAttribute("NVList" , nhanViens.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , nhanViens.getTotalPages());
        model.addAttribute("NVCre" , new NhanVien() );
        return "view-admin/dashbroad/crud-nhan-vien";
    }

    @PostMapping("/add-nhan-vien")
    private String addNhanVien(@Valid @ModelAttribute("nhanvien") NhanVien nhanVien, BindingResult result, RedirectAttributes attributes){
        System.out.println("Thông tin nhân viên: ");
        System.out.println("Mã nhân viên: " + nhanVien.getMaNhanVien());
        System.out.println("Tên nhân viên: " + nhanVien.getTenNhanVien());
        System.out.println("Số điện thoại: " + nhanVien.getSoDienThoai());
        System.out.println("Ngày sinh: " + nhanVien.getNgaySinh());
        System.out.println("Địa chỉ: " + nhanVien.getDiaChi());
        if (nhanVienService.addNhanVien(nhanVien) == null){
            attributes.addFlashAttribute("nhanvien", nhanVien);
            attributes.addFlashAttribute("message", "Mã nhân viên đã tồn tại");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly-nhan-vien/phan-trang-nhan-vien";
        }
        nhanVienService.addNhanVien(nhanVien);
        attributes.addFlashAttribute("message" , "Thêm mới thành công");
        attributes.addFlashAttribute("messageType" , "alert-danger");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly-nhan-vien/phan-trang-nhan-vien";
    }

    @PostMapping("/update-nhan-vien")
    private String updateNV(@Valid @ModelAttribute("nhanvien") NhanVien nhanVien, BindingResult result, RedirectAttributes attributes, @RequestParam("id") Integer idNV){

        System.out.println("Ngày sinh: " + nhanVien.getNgaySinh());
        if (nhanVienService.updateNhanVien(nhanVien, idNV) == null){
            attributes.addFlashAttribute("nhanvien", nhanVien);
            attributes.addFlashAttribute("message", "Mã nhân viên đã tồn tại");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly-nhan-vien/phan-trang-nhan-vien";
        }
        nhanVienService.updateNhanVien(nhanVien, idNV);
        attributes.addFlashAttribute("message" , "Update nhân viên thành công");
        attributes.addFlashAttribute("messageType" , "alert-danger");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly-nhan-vien/phan-trang-nhan-vien";
    }

    @GetMapping("/delete-nhan-vien/{id}")
    private String deleteNV(@PathVariable("id")Integer idNV, RedirectAttributes attributes){
        nhanVienService.deleteNhanVien(idNV);
        attributes.addFlashAttribute("message" , "Đổi trạng thái nhân viên thành công");
        attributes.addFlashAttribute("messageType" , "alert-danger");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly-nhan-vien/phan-trang-nhan-vien";
    }

    @GetMapping("/tim-ma-nhan-vien")
    private String timKiemMaNV(@RequestParam("timKiemMa")String ma, @RequestParam(name = "pageNo", defaultValue = "0")Integer pageNo, Model model){
        Page<NhanVien> nhanViens = nhanVienService.timTatCaTheoMa(pageNo, "%"+ma+"%");
        model.addAttribute("NVList" , nhanViens.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , nhanViens.getTotalPages());
        model.addAttribute("NVCre" , new NhanVien() );
        return "view-admin/dashbroad/crud-nhan-vien";
    }
}
