package com.example.kristp.controller.admin;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.service.ChatLieuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ChatLieuController {

    @Autowired
    private ChatLieuService chatLieuService ;

    @GetMapping("/list-chat-lieu")
    private String getAllChatLieu(Model model){
        model.addAttribute("listChatLieu" , chatLieuService.getAllChatLieu());
        return "";
    }

    // thay các lớp ở đây thành redirectModel , khi thêm set cứng là trạng thái đang hoạt động , check trùng tên chất liệu
    @PostMapping("/add-chat-lieu")
    private String addChatLieu(@Valid @ModelAttribute("chatlieu")ChatLieu chatLieu , BindingResult result , RedirectAttributes attributes){

        if(result.hasErrors()){
            attributes.addFlashAttribute("chatLieu" , chatLieu);
            attributes.addFlashAttribute("message" , "Thêm mới chất liệu không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titileMsg" , "Thất bại");
            return "chatLieuForm";
        }
        else if(chatLieuService.addChatLieu(chatLieu) != null){
            attributes.addFlashAttribute("chatLieu" , chatLieu);
            attributes.addFlashAttribute("message" , "Tên của chất liệu đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titileMsg" , "Thất bại");
            return "chatLieuForm";
        }
        chatLieuService.addChatLieu(chatLieu);
        attributes.addFlashAttribute("message" , "Thêm mới chất liệu thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titileMsg" , "Thành công");
        return "themThanhCong";
    }

    @PostMapping("/update-chat-lieu")
    private String updateChatLieu(@Valid @ModelAttribute("chatlieu")ChatLieu chatLieu , BindingResult result ,  RedirectAttributes attributes , @RequestParam("id")Integer idChatLieu){
        if(result.hasErrors()){
            attributes.addFlashAttribute("chatLieu" , chatLieu);
            attributes.addFlashAttribute("message" , "Cập nhật chất liệu không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titileMsg" , "Thất bại");
            return "chatLieuForm";
        }
        else if(chatLieuService.updateChatLieu(chatLieu , idChatLieu) != null){
            attributes.addFlashAttribute("chatLieu" , chatLieu);
            attributes.addFlashAttribute("message" , "Tên của chất liệu đã tồn tại .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titileMsg" , "Thất bại");
            return "chatLieuForm";
        }

        chatLieuService.updateChatLieu(chatLieu , idChatLieu);
        attributes.addFlashAttribute("message" , "Cập nhật chất liệu thành công .");
        attributes.addFlashAttribute("messageType" , "alert-success");
        attributes.addFlashAttribute("titileMsg" , "Thành công");
        return "capNhatThanhCong";
    }
    
    @PostMapping("/delete-chat-lieu/{id}")
    private String deleteChatLieu(@PathVariable("id")Integer idChatLieu ,  RedirectAttributes attributes){
        try {
            chatLieuService.deleteChatLieu(idChatLieu);
            attributes.addFlashAttribute("message" , "Xóa chất liệu thành công .");
            attributes.addFlashAttribute("messageType" , "alert-success");
            attributes.addFlashAttribute("titileMsg" , "Thành công");
            return "redirect/trangChatLieu";
        }catch (Exception e){
            attributes.addFlashAttribute("message" , "Xóa chất liệu không thành công .");
            attributes.addFlashAttribute("messageType" , "alert-danger");
            attributes.addFlashAttribute("titileMsg" , "Thất bại");
            return "redirect/trangChatLieu";
        }
    }
}
