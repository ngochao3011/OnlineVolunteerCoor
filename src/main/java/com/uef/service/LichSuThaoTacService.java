package com.uef.service;

import com.uef.model.LichSuThaoTac;
import com.uef.repository.LichSuRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LichSuThaoTacService {

    @Autowired
    private LichSuRepo lichSuRepo;

    public boolean ghiNhanLichSu(LichSuThaoTac log) {
        return lichSuRepo.save(log);
    }

    public List<LichSuThaoTac> getAll() {
        return lichSuRepo.getAll();
    }
}
