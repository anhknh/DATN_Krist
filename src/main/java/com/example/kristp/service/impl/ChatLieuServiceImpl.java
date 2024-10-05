package com.example.kristp.service.impl;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.ChatLieuRepository;
import com.example.kristp.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (timTheoTen(chatLieu.getTenChatLieu()) != null) {
            return null;
        }
        ChatLieu chatLieu1 = getChatlieuById(idChatLieu);
        chatLieu1.setTenChatLieu(chatLieu.getTenChatLieu());
        chatLieu1.setMoTa(chatLieu.getMoTa());
        chatLieu1.setTrangThai(chatLieu.getTrangThai());
        return chatLieuRepository.save(chatLieu1);
    }

    @Override
    public void deleteChatLieu(Integer idChatLieu) {
        chatLieuRepository.deleteById(idChatLieu);
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


}
