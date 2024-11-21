package com.example.kristp.service.impl;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.ChatLieuRepository;
import com.example.kristp.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatLieuServiceImpl implements ChatLieuService {
    @Autowired
    ChatLieuRepository chatLieuRepository;

    @Override
    public ArrayList<ChatLieu> getAllChatLieu() {
        return (ArrayList<ChatLieu>) chatLieuRepository.findAll();
    }

    @Override
    public ArrayList<ChatLieu> getAllChatLieuHD() {
        return (ArrayList<ChatLieu>) chatLieuRepository.findAllChatLieu();
    }

    @Override
    public ChatLieu getChatlieuById(int id) {
        return chatLieuRepository.findById(id).get();
    }

    @Override
    public ChatLieu addChatLieu(ChatLieu chatLieu) {
        if (timTheoTen(chatLieu.getTenChatLieu()) != null) {
            return null;
        }
        chatLieu.setTrangThai(Status.ACTIVE);
        return chatLieuRepository.save(chatLieu);
    }

    @Override
    public ChatLieu updateChatLieu(ChatLieu chatLieu, Integer idChatLieu) {
       ChatLieu chatlieutheoten = timTheoTen(chatLieu.getTenChatLieu());
        if(chatlieutheoten != null && !chatlieutheoten.getId().equals(idChatLieu)) {
            return null;
        }

        ChatLieu chatLieu1 = getChatlieuById(idChatLieu);
        chatLieu1.setTenChatLieu(chatLieu.getTenChatLieu());
        chatLieu1.setMoTa(chatLieu.getMoTa());
        return chatLieuRepository.save(chatLieu1);

    }

    @Override
    public void deleteChatLieu(Integer idChatLieu) {
        ChatLieu chatLieu = getChatlieuById(idChatLieu);
        // Chuyển trạng thái giữa ACTIVE và INACTIVE
        if (chatLieu.getTrangThai() == Status.INACTIVE) {
            chatLieu.setTrangThai(Status.ACTIVE);
        } else {
            chatLieu.setTrangThai(Status.INACTIVE);
        }
        chatLieuRepository.save(chatLieu);
    }

    @Override
    public ChatLieu timTheoTen(String tenChatLieu) {
        Optional<ChatLieu> chatLieu = chatLieuRepository.timKiemChatLieuTheoTen(tenChatLieu);
        if (chatLieu.isPresent()) {
            return chatLieu.get();
        } else {
            return null;
        }
    }


//    Phân trang cho phần quản lý chất liệu thay cho get all
    @Override
    public Page<ChatLieu> getPaginationChatLieu(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo , 5);
        return chatLieuRepository.findAll(pageable);
    }
// tim kiem thong tin chat lieu theo ten
    @Override
    public Page<ChatLieu> timTatCaTheoTen(Integer pageNo,String ten) {
        Pageable pageable = PageRequest.of(pageNo , 5);
        return chatLieuRepository.findAllByTenLike(pageable,ten);
    }


}
