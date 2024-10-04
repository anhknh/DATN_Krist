package com.example.kristp.controller;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.SanPham;
import com.example.kristp.service.ChatLieuService;
import com.example.kristp.service.DanhMucService;
import com.example.kristp.service.SanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class SanPhamController {
    @Autowired
    SanPhamService sanPhamServiec;
    @Autowired
    DanhMucService danhMucService;
    @Autowired
    ChatLieuService chatLieuService;


    @GetMapping("/getAllProduct")
    public String getAll(Model model) {
        List<SanPham> sanPhamList = sanPhamServiec.findAllSanPham();
        model.addAttribute("sanPhamList", sanPhamList);
        return "SanPham";
    }

    @GetMapping("/addSanPhamFrom")
    public String addSanPhamFrom(Model model) {
        List<SanPham> sanPhamList = sanPhamServiec.findAllSanPham();
        model.addAttribute("sanPhamList", sanPhamList);

        List<DanhMuc> danhMucSanPhamList = danhMucService.getAllDanhMuc();
        model.addAttribute("danhMucSanPhamList", danhMucSanPhamList);

        List<ChatLieu> thuongHieuList = chatLieuService.getAllChatLieu();
        model.addAttribute("chatLieuList", thuongHieuList);

        model.addAttribute("sanPham", new SanPham());

        model.addAttribute("danhMucSanPham", new DanhMuc());

        model.addAttribute("thuongHieu", new ChatLieu());
        return "SanPham-add-update";
    }

    @PostMapping("/addSanPham")
    public String addSanPham(@ModelAttribute @Valid SanPham sanPham, RedirectAttributes redirectAttributes, Model model) throws IOException {

        if (true) {
            if (sanPhamServiec.isTenExists(sanPham.getTenSanPham().trim())) {
                model.addAttribute("errorMessage", "Tên sản phẩm đã tồn tại.");
                List<SanPham> sanPhamList = sanPhamServiec.findAllSanPham();
                model.addAttribute("sanPhamList", sanPhamList);
                List<DanhMuc> danhMucSanPhamList = danhMucService.getAllDanhMuc();
                model.addAttribute("danhMucSanPhamList", danhMucSanPhamList);
                List<ChatLieu> chatLieuList = chatLieuService.getAllChatLieu();
                model.addAttribute("thuongHieuList", chatLieuList);
                model.addAttribute("sanPham", new SanPham());
                return "SanPham-add-update";
            }
            redirectAttributes.addFlashAttribute("successMessage", "Thêm thành công.");
            sanPhamServiec.add(sanPham);
            return "redirect:/SanPham";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Thêm thất bại.");
        return "redirect:/SanPham";
    }

    @GetMapping("/updateSanPhamFrom/{id}")
    public String updateProductFrom(Model model, @PathVariable Integer id) {
        SanPham sanPham = sanPhamServiec.findSanphamById(id);

        List<DanhMuc> danhMucSanPhamList = danhMucService.getAllDanhMuc();
        model.addAttribute("danhMucSanPhamList", danhMucSanPhamList);

        List<ChatLieu> thuongHieuList = chatLieuService.getAllChatLieu();
        model.addAttribute("chatLieuList", thuongHieuList);

        model.addAttribute("danhMucSanPham", new DanhMuc());

        model.addAttribute("thuongHieu", new ChatLieu());

        model.addAttribute("sanPham", sanPham);
        return "SanPham-add-update";
    }

    @PostMapping("/updateSanPham/{id}")
    public String updateProduct(@PathVariable Integer id, @ModelAttribute @Valid SanPham sanPham, RedirectAttributes redirectAttributes) throws IOException {

        SanPham sanPhamId = sanPhamServiec.findSanphamById(id);

        if (sanPhamId == null) {
            return "redirect:/SanPham";
        }
        sanPhamId.setDanhMuc(sanPham.getDanhMuc());
        sanPhamId.setChatLieu(sanPham.getChatLieu());
        sanPhamId.setTenSanPham(sanPham.getTenSanPham());
        sanPhamId.setMoTa(sanPham.getMoTa());
        sanPhamId.setTrangThai(sanPham.getTrangThai());

        if (true) {
            redirectAttributes.addFlashAttribute("successMessage", "Update thành công.");
            sanPhamServiec.update(sanPham, id);
            return "redirect:/SanPham";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Update không thành công.");
        return "redirect:/SanPham";
    }

    @GetMapping("/deleteSanPham/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            sanPhamServiec.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được xóa thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sản phẩm. Vui lòng thử lại sau.");
            e.printStackTrace();
        }
        return "redirect:/SanPham";
    }
}
