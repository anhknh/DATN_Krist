package com.example.kristp.service.impl;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.repository.ChatLieuRepository;
import com.example.kristp.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
}
