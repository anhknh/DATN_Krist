package com.example.kristp.controller.admin;


import com.example.kristp.entity.MauSac;
import com.example.kristp.entity.Size;
import com.example.kristp.entity.TayAo;
import com.example.kristp.service.TayAoService;
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
public class TayAoController {
    @Autowired
    TayAoService tayAoService ;

    @GetMapping("/list-tay-ao")
    private String getAllTayAo(RedirectAttributes attributes){
        attributes.addFlashAttribute("listTayAo" ,tayAoService.getAllTayAo());
        return "view-admin/dashbroad/crud-tay-ao";
    }

    @GetMapping("/pagination-tay-ao")
    private String getPagination(Model model , @RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo){
        Page<TayAo> tayaos = tayAoService.getPaginationTayAo(pageNo);
        model.addAttribute("tayAoList" , tayaos.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , tayaos.getTotalPages());
        model.addAttribute("tayAoCre" , new TayAo() );
        return "view-admin/dashbroad/crud-tay-ao";
    }

    @PostMapping("/add-tay-ao")
    private String addTayAo(@Valid @ModelAttribute("tayAo") TayAo tayAo , BindingResult result , RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("tayAo" , tayAo);
            attributes.addFlashAttribute("message" , "Thêm mới tay ao không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-tay-ao";
        }
        else if(tayAoService.addTayAo(tayAo) == null){
            attributes.addFlashAttribute("tayAo" , tayAo);
            attributes.addFlashAttribute("message" , "Tên của tay áo đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-tay-ao";
        }
        tayAoService.addTayAo(tayAo);
        attributes.addFlashAttribute("message" , "Thêm mới tay áo thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly/pagination-tay-ao";
    }

    @PostMapping("/update-tay-ao")
    private String updateSize(@Valid @ModelAttribute("tayAo")TayAo tayAo , BindingResult result , RedirectAttributes attributes , @RequestParam("id")Integer idTayAo){
        if(result.hasErrors()){
            attributes.addFlashAttribute("tayAo" , tayAo);
            attributes.addFlashAttribute("message" , "Cập nhật tay á0 không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-tay-ao";
        }
        else if(tayAoService.updateTayAo(tayAo , idTayAo) == null){
            attributes.addFlashAttribute("tayAo", tayAo);
            attributes.addFlashAttribute("message" , "Tên của tay áo đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-tay-ao";
        }

        tayAoService.updateTayAo(tayAo , idTayAo) ;
        attributes.addFlashAttribute("message" , "Cập nhật tay áo thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly/pagination-tay-ao";
    }

    @GetMapping("/delete-tay-ao/{id}")
    private String deleteTayAo(@PathVariable("id")Integer idTayAo ,  RedirectAttributes attributes){
        tayAoService.deleteTayAo(idTayAo);
        attributes.addFlashAttribute("message" , "Thay đổi trạng thái tay áo thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly/pagination-tay-ao";

    }

    @GetMapping("/tim-kiem-tat-ca-theo-ten-tay-ao")
    private String timKiemTatCaTheoTen(@RequestParam("tenTimKiem")String ten ,@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo, Model model){
        Page<TayAo> tayaos= tayAoService.timTatCaTheoTen(pageNo,"%"+ten+"%");
        model.addAttribute("tayAoList" , tayaos.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , tayaos.getTotalPages());
        model.addAttribute("tayAoCre" , new TayAo() );
        return "view-admin/dashbroad/crud-tay-ao";
    }
}
