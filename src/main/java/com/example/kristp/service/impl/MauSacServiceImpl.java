package com.example.kristp.service.impl;

import com.example.kristp.entity.DanhMuc;
import com.example.kristp.entity.MauSac;
import com.example.kristp.entity.Size;
import com.example.kristp.enums.Status;
import com.example.kristp.repository.MauSacRepository;
import com.example.kristp.service.MauSacService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MauSacServiceImpl implements MauSacService {

    @Autowired
    MauSacRepository mauSacRepository;
    @Override
    public ArrayList<MauSac> getAllMauSac() {
            return (ArrayList<MauSac>) mauSacRepository.findAll();
    }

    @Override
    public ArrayList<MauSac> getAllMauSacHD() {
        return (ArrayList<MauSac>) mauSacRepository.findAllMauSac();
    }


    @Override
    public MauSac getMauSacById(int id) {
        return mauSacRepository.findById(id).get();
    }

    @Override
    public MauSac addMauSac(MauSac mauSac) {
        if(timTheoMaMauSac(mauSac.getMaMauSac()) != null){
            return null ;
        }
        mauSac.setTrangThai(Status.ACTIVE);
        return mauSacRepository.save(mauSac);
    }

    @Override
    public MauSac updateMauSac(MauSac mauSac, Integer idMauSac) {

        MauSac mautheoten = timTheoMaMauSac(mauSac.getMaMauSac());
        if(mautheoten != null && !mautheoten.getId().equals(idMauSac)) {
            return null;
        }

        MauSac mauSac1 = getMauSacById(idMauSac);
        mauSac1.setMaMauSac(mauSac.getMaMauSac());
        mauSac1.setMoTa(mauSac.getMoTa());
        mauSac1.setTrangThai(mauSac.getTrangThai());
        return mauSacRepository.save(mauSac1);
    }

    @Override
    public void deleteMauSac(Integer idMauSac) {
        MauSac mauSac = getMauSacById(idMauSac);
        mauSac.setTrangThai(Status.INACTIVE);
        mauSacRepository.save(mauSac);
    }

    @Override
    public MauSac timTheoMaMauSac(String maMauSac) {
        Optional<MauSac> mauSac = mauSacRepository.timKiemMaMauSac(maMauSac);
        if (mauSac.isPresent()) {
            return mauSac.get();
        } else {
            return null;
        }
    }


    @Override
    public Page<MauSac> getPaginationMauSac(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo , 5);
        return mauSacRepository.findAll(pageable);
    }

    @Override
    public Page<MauSac> timTatCaTheoTen(Integer pageNo, String ten) {
        Pageable pageable = PageRequest.of(pageNo , 5);
        return mauSacRepository.findAllByTenLike(pageable,ten);
    }
}
