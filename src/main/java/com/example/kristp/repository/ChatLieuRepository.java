package com.example.kristp.repository;

import com.example.kristp.entity.ChatLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu, Integer> {
    @Query(value = "SELECT cl FROM ChatLieu cl where cl.tenChatLieu = :ten")
    public Optional<ChatLieu> timKiemChatLieuTheoTen(@Param("ten") String tenChatLieu);


    @Query(value = "select cl from ChatLieu cl where cl.tenChatLieu LIKE :ten")
    Page<ChatLieu> findAllByTenLike(Pageable pageable, @Param("ten") String ten);

    @Query("from ChatLieu cl where  cl.trangThai = 1")
    List<ChatLieu> findAllChatLieu();
}
