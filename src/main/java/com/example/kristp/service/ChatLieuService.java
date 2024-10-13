package com.example.kristp.service;

import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;

public interface ChatLieuService {
    ArrayList<ChatLieu> getAllChatLieu();
    ChatLieu getChatlieuById(int id);

    ChatLieu addChatLieu(ChatLieu chatLieu);

    ChatLieu updateChatLieu(ChatLieu chatLieu , Integer idChatLieu);

    void deleteChatLieu(Integer idChatLieu);

    ChatLieu timTheoTen(String tenChatLieu);
    Page<ChatLieu> getPaginationChatLieu(Integer pageNo);

    Page<ChatLieu> timTatCaTheoTen(Integer pageNo, String ten);
}
