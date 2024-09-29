package com.example.kristp.service;

import com.example.kristp.entity.ChatLieu;


import java.util.ArrayList;

public interface ChatLieuService {
    ArrayList<ChatLieu> getAllChatLieu();
    ChatLieu getChatlieuById(int id);
}
