package com.example.kristp.controller.admin;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.service.ChatLieuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/quan-ly/")
public class ChatLieuController {

    @Autowired
    private ChatLieuService chatLieuService ;
//Phần này không dùng
    @GetMapping("/list-chat-lieu")
    private String getAllChatLieu(Model model){
        model.addAttribute("listChatLieu" , chatLieuService.getAllChatLieu());
        return "view-admin/dashbroad/chat-lieu-dashroad";
    }

    @GetMapping("/pagination-chat-lieu")
    private String getPagination(Model model , @RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo){
        Page<ChatLieu> chatLieus = chatLieuService.getPaginationChatLieu(pageNo);
        System.out.println("đã vào đây");
        System.out.println(chatLieus.getContent());
        model.addAttribute("chatLieuList" , chatLieus.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , chatLieus.getTotalPages());
        System.out.println("Totalpage" + chatLieus.getTotalPages());
        model.addAttribute("chatLieuCre" , new ChatLieu() );
        return "view-admin/dashbroad/chat-lieu-dashroad";
    }

    // thay các lớp ở đây thành redirectModel , khi thêm set cứng là trạng thái đang hoạt động , check trùng tên chất liệu
    @PostMapping("/add-chat-lieu")
    private String addChatLieu(@Valid @ModelAttribute("chatlieu")ChatLieu chatLieu , BindingResult result , RedirectAttributes attributes){

        if(result.hasErrors()){
            attributes.addFlashAttribute("chatLieu" , chatLieu);
            attributes.addFlashAttribute("message" , "Thêm mới chất liệu không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-chat-lieu";
        }
        else if(chatLieuService.addChatLieu(chatLieu) == null){
            attributes.addFlashAttribute("chatLieu" , chatLieu);
            attributes.addFlashAttribute("message" , "Tên của chất liệu đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-chat-lieu";
        }
        chatLieuService.addChatLieu(chatLieu);
        attributes.addFlashAttribute("message" , "Thêm mới chất liệu thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly/pagination-chat-lieu";
    }

    @PostMapping("/update-chat-lieu")
    private String updateChatLieu(@Valid @ModelAttribute("chatlieu")ChatLieu chatLieu , BindingResult result ,  RedirectAttributes attributes , @RequestParam("id")Integer idChatLieu){
        if(result.hasErrors()){
            attributes.addFlashAttribute("chatLieu" , chatLieu);
            attributes.addFlashAttribute("message" , "Cập nhật chất liệu không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-chat-lieu";
        }
        else if(chatLieuService.updateChatLieu(chatLieu , idChatLieu) == null){
            attributes.addFlashAttribute("chatLieu" , chatLieu);
            attributes.addFlashAttribute("message" , "Tên của chất liệu đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titleMsg" , "Thất bại");
            return "redirect:/quan-ly/pagination-chat-lieu";
        }

        chatLieuService.updateChatLieu(chatLieu , idChatLieu);
        attributes.addFlashAttribute("message" , "Cập nhật chất liệu thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly/pagination-chat-lieu";
    }

    @GetMapping("/delete-chat-lieu/{id}")
    private String deleteChatLieu(@PathVariable("id")Integer idChatLieu ,  RedirectAttributes attributes){
            chatLieuService.deleteChatLieu(idChatLieu);
            attributes.addFlashAttribute("message" , "Thay đổi trạng thái chất liệu thành công .");
            attributes.addFlashAttribute("messageType" , "alert-success");
            attributes.addFlashAttribute("titleMsg" , "Thành công");
        return "redirect:/quan-ly/pagination-chat-lieu";

    }

    @GetMapping("/tim-kiem-tat-ca-theo-ten-chat-lieu")
    private String timKiemTatCaTheoTen(@RequestParam("tenTimKiem")String ten ,@RequestParam(name = "pageNo" , defaultValue = "0")Integer pageNo, Model model){
        Page<ChatLieu> chatLieus = chatLieuService.timTatCaTheoTen(pageNo,"%"+ten+"%");
        model.addAttribute("chatLieuList" , chatLieus.getContent());
        model.addAttribute("currentPage" , pageNo);
        model.addAttribute("totalPage" , chatLieus.getTotalPages());
        model.addAttribute("chatLieuCre" , new ChatLieu() );
        return "view-admin/dashbroad/chat-lieu-dashroad";
    }
}
