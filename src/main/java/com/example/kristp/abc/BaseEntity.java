package com.example.kristp.abc;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngay_tao", updatable = false)
    private Date ngayTao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngay_sua")
    private Date ngaySua;

    @PrePersist
    protected void onCreate() {
        this.ngayTao = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngaySua = new Date();
    }
}
