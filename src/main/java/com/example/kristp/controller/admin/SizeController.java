package com.example.kristp.controller.admin;



import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.Size;
import com.example.kristp.service.SizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quan-ly/")
public class SizeController {
    @Autowired
    private SizeService sizeService ;

    @GetMapping("/list-size")
    private String getAllSize(RedirectAttributes attributes){
        attributes.addFlashAttribute("listSize" ,sizeService.getAllSize());
        return "view-admin/dashbroad/crud-size";
    }

    @GetMapping("/pagination-size")
    private String getPagination(Model model , @RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo){
        Page<Size> sizes = sizeService.getPaginationSize(pageNo);
        model.addAttribute("sizeList" , sizes.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , sizes.getTotalPages());
        model.addAttribute("sizeCre" , new Size() );
        return "view-admin/dashbroad/crud-size";
    }

    @PostMapping("/add-size")
    private String addSize(@Valid @ModelAttribute("size") Size size , BindingResult result , RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("size" , size);
            attributes.addFlashAttribute("message" , "Thêm mới size không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-size";
        }
        else if(sizeService.addSize(size) == null){
            attributes.addFlashAttribute("size" , size);
            attributes.addFlashAttribute("message" , "Tên của size đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-size";
        }
        sizeService.addSize(size);
        attributes.addFlashAttribute("message" , "Thêm mới size thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly/pagination-size";
    }
    @PostMapping("/update-size")
    private String updateSize(@Valid @ModelAttribute("size")Size size , BindingResult result , RedirectAttributes attributes , @RequestParam("id")Integer idSize){
        if(result.hasErrors()){
            attributes.addFlashAttribute("size" , size);
            attributes.addFlashAttribute("message" , "Cập nhật size không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-size";
        }
        else if(sizeService.updateSize(size , idSize) == null){
            attributes.addFlashAttribute("size", size);
            attributes.addFlashAttribute("message" , "Tên của size đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-size";
        }

        sizeService.updateSize(size , idSize) ;
        attributes.addFlashAttribute("message" , "Cập nhật size thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly/pagination-size";
    }
    @GetMapping("/delete-size/{id}")
    private String deleteSize(@PathVariable("id")Integer idSize ,  RedirectAttributes attributes){
        sizeService.deleteSize(idSize);
        attributes.addFlashAttribute("message" , "Thay đổi trạng thái size thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly/pagination-size";
    }

    @GetMapping("/tim-kiem-tat-ca-theo-ten-size")
    private String timKiemTatCaTheoTen(@RequestParam("tenTimKiem")String ten ,@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo, Model model){
        Page<Size> sizes = sizeService.timTatCaTheoTen(pageNo,"%"+ten+"%");
        model.addAttribute("sizeList" , sizes.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , sizes.getTotalPages());
        model.addAttribute("sizeCre" , new Size() );
        return "view-admin/dashbroad/crud-size";
    }


}

