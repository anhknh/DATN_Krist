package com.example.kristp.controller.admin;

import com.example.kristp.entity.CoAo;
import com.example.kristp.entity.TayAo;
import com.example.kristp.service.CoAoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quan-ly-co-ao/")
public class CoAoController {
    @Autowired
    CoAoService coAoService ;
    @GetMapping("/list-co-ao")
    private String getAllCoAo(RedirectAttributes attributes){
        attributes.addFlashAttribute("listCoAo" ,coAoService.getAllCoAo());
        return "view-admin/dashbroad/crud-co-ao";
    }

    @GetMapping("/pagination-co-ao")
    private String getPagination(Model model , @RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo){
        Page<CoAo> coaos = coAoService.getPaginationCoAo(pageNo);
        model.addAttribute("coAoList" , coaos.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , coaos.getTotalPages());
        model.addAttribute("coAoCre" , new CoAo() );
        return "view-admin/dashbroad/crud-co-ao";
    }

    @PostMapping("/add-co-ao")
    private String addCoAo(@Valid @ModelAttribute("coAo") CoAo coAo , BindingResult result , RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("coAo" , coAo);
            attributes.addFlashAttribute("message" , "Thêm mới cổ ao không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly-co-ao/pagination-co-ao";
        }
        else if(coAoService.addCoAo(coAo) == null){
            attributes.addFlashAttribute("coAo" , coAo);
            attributes.addFlashAttribute("message" , "Tên của cổ áo đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly-co-ao/pagination-co-ao";
        }
        coAoService.addCoAo(coAo);
        attributes.addFlashAttribute("message" , "Thêm mới cổ áo thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly-co-ao/pagination-co-ao";
    }

    @PostMapping("/update-co-ao")
    private String updateCoAo(@Valid @ModelAttribute("coAo")CoAo coAo , BindingResult result , RedirectAttributes attributes , @RequestParam("id")Integer idCoAo){
        if(result.hasErrors()){
            attributes.addFlashAttribute("coAo" , coAo);
            attributes.addFlashAttribute("message" , "Cập nhật cổ á0 không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly-co-ao/pagination-co-ao";
        }
        else if(coAoService.updateCoAo(coAo , idCoAo) == null){
            attributes.addFlashAttribute("coAo", coAo);
            attributes.addFlashAttribute("message" , "Tên của cổ áo đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly-co-ao/pagination-co-ao";
        }

        coAoService.updateCoAo(coAo , idCoAo) ;
        attributes.addFlashAttribute("message" , "Cập nhật cổ áo thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly-co-ao/pagination-co-ao";
    }

    @GetMapping("/delete-co-ao/{id}")
    private String deleteTayAo(@PathVariable("id")Integer idCoAo ,  RedirectAttributes attributes){
        coAoService.deleteCoAo(idCoAo);
        attributes.addFlashAttribute("message" , "Xóa cổ áo thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly-co-ao/pagination-co-ao";

    }
}
