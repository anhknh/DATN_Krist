package com.example.kristp.repository;
;
import com.example.kristp.entity.MauSac;
import com.example.kristp.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface MauSacRepository  extends JpaRepository<MauSac, Integer> {
    @Query(value = "SELECT ms FROM MauSac ms where ms.maMauSac = :maMauSac")
    public Optional<MauSac> timKiemMaMauSac(@Param("maMauSac") String maMauSac);
}
