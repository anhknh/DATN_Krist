package com.example.kristp.controller;

import com.example.kristp.entity.*;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.ChatLieuRepository;
import com.example.kristp.repository.DanhMucRepository;
import com.example.kristp.service.ChatLieuService;
import com.example.kristp.service.ChiTietSanPhamService;
import com.example.kristp.service.DanhMucService;
import com.example.kristp.service.SanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/quan-ly-san-pham/")
public class SanPhamController {
    @Autowired
    SanPhamService sanPhamService;
    @Autowired
    DanhMucService danhMucService;
    @Autowired
    ChatLieuService chatLieuService;

    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private ChatLieuRepository chatLieuRepository;


    @GetMapping("/list-san-pham")
    private String getAllCoAo(Model model){
        List<SanPham> sanPhamList = sanPhamService.findAllSanPham();
        model.addAttribute("sanPhamList", sanPhamList);
        return "view-admin/dashbroad/crud-san-pham";
    }

    @GetMapping("/pagination-san-pham")
    private String getPagination(Model model , @RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo){
        Page<SanPham> coaos = sanPhamService.getPaginationSanPham(pageNo);
        model.addAttribute("sanPhamList" , coaos.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , coaos.getTotalPages());
        model.addAttribute("danhMucList", danhMucService.getAllDanhMuc());
        model.addAttribute("chatLieuList", chatLieuService.getAllChatLieu());
        model.addAttribute("sanPhamCre" , new SanPham() );
        return "view-admin/dashbroad/crud-san-pham";
    }


//    @GetMapping("/addSanPhamFrom")
//    public String addSanPhamFrom(Model model) {
//        List<SanPham> sanPhamList = sanPhamServiec.findAllSanPham();
//        model.addAttribute("sanPhamList", sanPhamList);
//
//        List<DanhMuc> danhMucSanPhamList = danhMucService.getAllDanhMuc();
//        model.addAttribute("danhMucSanPhamList", danhMucSanPhamList);
//
//        List<ChatLieu> thuongHieuList = chatLieuService.getAllChatLieu();
//        model.addAttribute("chatLieuList", thuongHieuList);
//
//        model.addAttribute("sanPham", new SanPham());
//
//        model.addAttribute("danhMucSanPham", new DanhMuc());
//
//        model.addAttribute("thuongHieu", new ChatLieu());
//        return "SanPham-add-update";
//    }


    @PostMapping("/add-san-pham")
    private String addSanPham(@Valid @ModelAttribute("sanPham") SanPham sanPham,
                              BindingResult result, RedirectAttributes attributes,
    @RequestParam("idChatLieu") Integer idChatLieu,@RequestParam("idDanhMuc") Integer idDanhMuc) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("sanPham", sanPham);
            attributes.addFlashAttribute("message", "Thêm mới sản phẩm không thành công.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly-san-pham/pagination-san-pham";
        }
        // Kiểm tra tên sản phẩm đã tồn tại
        if (sanPhamService.isTenExists(sanPham.getTenSanPham().trim())) {
            attributes.addFlashAttribute("sanPham", sanPham);
            attributes.addFlashAttribute("message", "Tên của sản phẩm đã tồn tại.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly-san-pham/pagination-san-pham";
        }

        // Nếu tên chưa tồn tại, thêm sản phẩm mới
        sanPham.setChatLieu(chatLieuService.getChatlieuById(idChatLieu));
        sanPham.setDanhMuc(danhMucService.getDanhmucById(idDanhMuc));
        sanPham.setTrangThai(Status.ACTIVE);
        sanPhamService.addSanPham(sanPham);
        attributes.addFlashAttribute("message", "Thêm mới sản phẩm thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        attributes.addFlashAttribute("titleMsg", "Thành công");
        return "redirect:/quan-ly-san-pham/pagination-san-pham";
    }


    @PostMapping("/update-san-pham")
    private String updateDanhMuc(@Valid @ModelAttribute("sanPham")SanPham sanPham , BindingResult result , RedirectAttributes attributes ,
                                 @RequestParam("id")Integer idSanPham,
                                 @RequestParam("idChatLieu") Integer idChatLieu,
                                 @RequestParam("idDanhMuc") Integer idDanhMuc){
        if (result.hasErrors()) {
            attributes.addFlashAttribute("sanPham", sanPham);
            attributes.addFlashAttribute("message", "Cập nhật sản phẩm không thành công.");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly-san-pham/pagination-san-pham";
        }
        // Đảm bảo rằng các giá trị được thiết lập trước khi cập nhật
        if (idChatLieu != null) {
            sanPham.setChatLieu(chatLieuService.getChatlieuById(idChatLieu));
        } else {
            // Xử lý trường hợp không có idChatLieu
            attributes.addFlashAttribute("message", "Chưa chọn chất liệu!");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly-san-pham/pagination-san-pham"; // Chuyển hướng trở lại nếu có lỗi
        }

        if (idDanhMuc != null) {
            sanPham.setDanhMuc(danhMucService.getDanhmucById(idDanhMuc));
        } else {
            // Xử lý trường hợp không có idDanhMuc
            attributes.addFlashAttribute("message", "Chưa chọn danh mục!");
            attributes.addFlashAttribute("messageType", "alert-danger");
            attributes.addFlashAttribute("titleMsg", "Thất bại");
            return "redirect:/quan-ly-san-pham/pagination-san-pham"; // Chuyển hướng trở lại nếu có lỗi
        }

        // Cập nhật sản phẩm nếu không trùng lặp
        sanPhamService.updateSanPham(sanPham,idSanPham);
        attributes.addFlashAttribute("message", "Cập nhật sản phẩm thành công.");
        attributes.addFlashAttribute("messageType", "alert-success");
        attributes.addFlashAttribute("titleMsg", "Thành công");
        return "redirect:/quan-ly-san-pham/pagination-san-pham";
    }


    @GetMapping("/delete-san-pham/{id}")
    private String deleteSanPham(@PathVariable("id")Integer idSanPham ,  RedirectAttributes attributes){
        sanPhamService.deleteSanPham(idSanPham);
        attributes.addFlashAttribute("message" , "Đổi trạng thái sản phẩm thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly-san-pham/pagination-san-pham";

    }

    @GetMapping("/tim-kiem-tat-ca-theo-ten")
    private String timKiemTatCaTheoTen(@RequestParam("tenTimKiem")String ten ,@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo, Model model){
        Page<SanPham> coaos = sanPhamService.timTatCaTheoTen(pageNo,"%"+ten+"%");
        model.addAttribute("sanPhamList" , coaos.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , coaos.getTotalPages());
        model.addAttribute("danhMucList", danhMucRepository.findAll());
        model.addAttribute("chatLieuList", chatLieuRepository.findAll());
        model.addAttribute("sanPhamCre" , new SanPham() );
        return "view-admin/dashbroad/crud-san-pham";
    }

//    @PostMapping("/addSanPham")
//    public String addSanPham(@ModelAttribute @Valid SanPham sanPham, RedirectAttributes redirectAttributes, Model model) throws IOException {
//
//        if (true) {
//            if (sanPhamServiec.isTenExists(sanPham.getTenSanPham().trim())) {
//                model.addAttribute("errorMessage", "Tên sản phẩm đã tồn tại.");
//                List<SanPham> sanPhamList = sanPhamServiec.findAllSanPham();
//                model.addAttribute("sanPhamList", sanPhamList);
//                List<DanhMuc> danhMucSanPhamList = danhMucService.getAllDanhMuc();
//                model.addAttribute("danhMucSanPhamList", danhMucSanPhamList);
//                List<ChatLieu> chatLieuList = chatLieuService.getAllChatLieu();
//                model.addAttribute("thuongHieuList", chatLieuList);
//                model.addAttribute("sanPham", new SanPham());
//                return "SanPham-add-update";
//            }
//            redirectAttributes.addFlashAttribute("successMessage", "Thêm thành công.");
//            sanPhamServiec.add(sanPham);
//            return "redirect:/SanPham";
//        }
//
//        redirectAttributes.addFlashAttribute("errorMessage", "Thêm thất bại.");
//        return "redirect:/SanPham";
//    }

//    @GetMapping("/find-san-pham")
//    @ResponseBody
//    public SanPham getSizesByProductId(@RequestParam Integer productId) {
//        return sanPhamServiec.findSanphamById(productId);
//    }
//
//    @GetMapping("/updateSanPhamFrom/{id}")
//    public String updateProductFrom(Model model, @PathVariable Integer id) {
//        SanPham sanPham = sanPhamServiec.findSanphamById(id);
//
//        List<DanhMuc> danhMucSanPhamList = danhMucService.getAllDanhMuc();
//        model.addAttribute("danhMucSanPhamList", danhMucSanPhamList);
//
//        List<ChatLieu> thuongHieuList = chatLieuService.getAllChatLieu();
//        model.addAttribute("chatLieuList", thuongHieuList);
//
//        model.addAttribute("danhMucSanPham", new DanhMuc());
//
//        model.addAttribute("thuongHieu", new ChatLieu());
//
//        model.addAttribute("sanPham", sanPham);
//        return "SanPham-add-update";
//    }
//
//    @PostMapping("/updateSanPham/{id}")
//    public String updateProduct(@PathVariable Integer id, @ModelAttribute @Valid SanPham sanPham, RedirectAttributes redirectAttributes) throws IOException {
//
//        SanPham sanPhamId = sanPhamServiec.findSanphamById(id);
//
//        if (sanPhamId == null) {
//            return "redirect:/SanPham";
//        }
//        sanPhamId.setDanhMuc(sanPham.getDanhMuc());
//        sanPhamId.setChatLieu(sanPham.getChatLieu());
//        sanPhamId.setTenSanPham(sanPham.getTenSanPham());
//        sanPhamId.setMoTa(sanPham.getMoTa());
//        sanPhamId.setTrangThai(sanPham.getTrangThai());
//
//        if (true) {
//            redirectAttributes.addFlashAttribute("successMessage", "Update thành công.");
//            sanPhamServiec.update(sanPham, id);
//            return "redirect:/SanPham";
//        }
//        redirectAttributes.addFlashAttribute("successMessage", "Update không thành công.");
//        return "redirect:/SanPham";
//    }
//
//    @GetMapping("/deleteSanPham/{id}")
//    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
//        try {
//            sanPhamServiec.delete(id);
//            redirectAttributes.addFlashAttribute("successMessage", "Sản phẩm đã được xóa thành công.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sản phẩm. Vui lòng thử lại sau.");
//            e.printStackTrace();
//        }
//        return "redirect:/SanPham";
//    }
}
