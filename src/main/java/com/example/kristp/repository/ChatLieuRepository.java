package com.example.kristp.repository;

import com.example.kristp.entity.ChatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu, Integer> {
    @Query(value = "SELECT cl.tenChatLieu FROM ChatLieu cl where cl.tenChatLieu = :ten")
    public Optional<ChatLieu> timKiemChatLieuTheoTen(@Param("ten") String tenChatLieu);

}
