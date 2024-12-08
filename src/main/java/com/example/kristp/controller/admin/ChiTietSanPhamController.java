package com.example.kristp.controller.admin;

import com.example.kristp.entity.*;
import com.example.kristp.entity.dto.ChiTietSanPhamDto;
import com.example.kristp.entity.dto.ChiTietSanPhamDtoEdit;
import com.example.kristp.entity.dto.ChiTietSanPhamForm;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.ChiTietSanPhamRepository;
import com.example.kristp.service.*;
import com.example.kristp.utils.DataUtils;
import com.example.kristp.utils.FileUpload;
import com.example.kristp.utils.Pagination;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/quan-ly/")
public class ChiTietSanPhamController {

    @Autowired
    SanPhamService sanPhamService;
    @Autowired
    MauSacService mauSacService;
    @Autowired
    TayAoService tayAoService;
    @Autowired
    CoAoService coAoService;
    @Autowired
    SizeService sizeService;
    @Autowired
    FileUpload fileUpload;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    DataUtils dataUtils;


    @GetMapping("/chi-tiet-san-pham")
    public String chiTietSanPham(@RequestParam("idSanPham") Integer idSanPham,
                                 @RequestParam("page") Optional<Integer> page,
                                 Model model) {
        Pagination pagination = new Pagination();
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<ChiTietSanPham> pageChiTiet = chiTietSanPhamService.getPageChiTietSanPham(idSanPham, pageable);
        model.addAttribute("idSanPham", idSanPham);
        model.addAttribute("totalPage", pageChiTiet.getTotalPages() - 1);
        model.addAttribute("page", pageChiTiet);
        model.addAttribute("pagination", pagination.getPage(pageChiTiet.getNumber(), pageChiTiet.getTotalPages()));
        model.addAttribute("listChiTiet", pageChiTiet.getContent());
        model.addAttribute("dataUtils", dataUtils);
        return "view-admin/dashbroad/chi-tiet-san-pham";
    }

    @GetMapping("/them-chi-tiet-san-pham")
    public String themChiTietSanPham(@RequestParam("idSanPham") Integer idSanPham, Model model) {

        SanPham sanPham = sanPhamService.findSanphamById(idSanPham);
        List<MauSac> listMausac = mauSacService.getAllMauSacHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<Size> listSize = sizeService.getAllSizeHD();
        model.addAttribute("listMausac", listMausac);
        model.addAttribute("listTayAo", listTayAo);
        model.addAttribute("listCoAo", listCoAo);
        model.addAttribute("listSize", listSize);
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("dataUtils", dataUtils);
        return "view-admin/dashbroad/form-chi-tiet-san-pham";
    }
    @GetMapping("/edit-chi-tiet-san-pham")
    public String editChiTietSanPham(@RequestParam("idSanPham") Integer idSanPham,
                                     @RequestParam("idChiTiet") Integer idChiTiet,
                                     Model model) {

        SanPham sanPham = sanPhamService.findSanphamById(idSanPham);
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getCTSPById(idChiTiet);
        List<MauSac> listMausac = mauSacService.getAllMauSacHD();
        List<TayAo> listTayAo = tayAoService.getAllTayAoHD();
        List<CoAo> listCoAo = coAoService.getAllCoAoHD();
        List<Size> listSize = sizeService.getAllSizeHD();
        model.addAttribute("listMausac", listMausac);
        model.addAttribute("listTayAo", listTayAo);
        model.addAttribute("listCoAo", listCoAo);
        model.addAttribute("listSize", listSize);
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("chiTietSanPham", chiTietSanPham);
        model.addAttribute("dataUtils", dataUtils);
        return "view-admin/dashbroad/edit-chi-tiet-san-pham";
    }

    @PostMapping("/add-chi-tiet-san-pham")
    public ResponseEntity<String> themChiTietSanPham(
            @RequestParam("idSanPham") Integer idSanPham,
            @ModelAttribute ChiTietSanPhamForm chiTietSanPhamForm
    ) throws IOException {
        List<ChiTietSanPhamDto> chiTietSanPhamDetails = chiTietSanPhamForm.getChiTietSanPhamDetails();
        if(chiTietSanPhamService.addChioTietSanPham(chiTietSanPhamDetails, idSanPham)) {
            return ResponseEntity.ok("Thêm chi tiết sản phẩm thành công!");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update-chi-tiet-san-pham")
    public String updateChiTietSanPham(
            @RequestParam("idChiTiet") Integer idChiTiet,
            @RequestParam("idSanPham") Integer idSanPham,
            @ModelAttribute ChiTietSanPhamDtoEdit chiTietSanPhamDto,
            HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws IOException {

        if (chiTietSanPhamService.updateChiTietSanPham(chiTietSanPhamDto, idChiTiet, idSanPham)) {
            redirectAttributes.addFlashAttribute("message", "Cập nhật Chi tiết thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
            return "redirect:/quan-ly/chi-tiet-san-pham?idSanPham=" +idSanPham;
        } else {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy sản phẩm hoặc chi tiết đã tồng tại.!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
            //get url request
            String referer = request.getHeader("referer");
            //reload page
            return "redirect:" +referer;
        }
    }

    @GetMapping("/change-chi-tiet-san-pham")
    public String changeChiTietSanPham(
            @RequestParam("idChiTiet") Integer idChiTiet,
            @RequestParam("idSanPham") Integer idSanPham,
            HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws IOException {

        if (chiTietSanPhamService.changeStatus(idChiTiet)) {
            redirectAttributes.addFlashAttribute("message", "Đổi trạng thái thành công!");
            redirectAttributes.addFlashAttribute("messageType", "alert-success");
            redirectAttributes.addFlashAttribute("titleMsg", "Thành công");
            return "redirect:/quan-ly/chi-tiet-san-pham?idSanPham=" +idSanPham;
        } else {
            redirectAttributes.addFlashAttribute("message", "Không đủ số lượng!");
            redirectAttributes.addFlashAttribute("messageType", "alert-danger");
            redirectAttributes.addFlashAttribute("titleMsg", "Thất bại");
            //get url request
            String referer = request.getHeader("referer");
            //reload page
            return "redirect:" +referer;
        }
    }

    //modal add don hang

    @GetMapping("/fetch-san-pham")
    @ResponseBody
    public ResponseEntity<?> getAllChiTietSanPham(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ChiTietSanPham> productPage = chiTietSanPhamRepository.findAllByTrangThai(Status.ACTIVE,pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", productPage.getContent());
        response.put("totalPages", productPage.getTotalPages());
        response.put("totalElements", productPage.getTotalElements());
        response.put("currentPage", productPage.getNumber() + 1); // Bắt đầu từ 1

        return ResponseEntity.ok(response);
    }

}
