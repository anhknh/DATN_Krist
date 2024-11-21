package com.example.kristp.scheduler;

import com.example.kristp.service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KhuyenMaiScheduler {
    @Autowired
    private KhuyenMaiService khuyenMaiService;

    // Chạy mỗi ngày vào lúc 00:00
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateStatus() {
        khuyenMaiService.updateKhuyenMaiStatus();
    }
}
