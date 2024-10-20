package com.example.kristp.repository;
import com.example.kristp.entity.ChatLieu;
import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository  extends JpaRepository<Size,Integer>{
    @Query(value = "SELECT sz FROM Size sz where sz.tenSize = :tenSize")
    Optional<Size> timKiemTenSize(@Param("tenSize") String tenSize);

    @Query(value = "select sz from Size sz where sz.tenSize LIKE :ten")
    Page<Size> findAllByTenLike(Pageable pageable, @Param("ten") String ten);
}
