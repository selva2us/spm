package com.ms.spm.services;

import com.ms.spm.entity.Goods;
import com.ms.spm.helper.CSVHelper;
import com.ms.spm.payload.GoodsDto;
import com.ms.spm.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
    @Autowired
    GoodsRepository repository;

    public void save(MultipartFile file) {
        try {
            List<Goods> goods = CSVHelper.csvToGoods(file.getInputStream());
            repository.saveAll(goods);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<Goods> getAllGoods() {
        return repository.findAll();
    }

    public Goods updateGoods(Long id, GoodsDto goods){
        if(repository.findById(id).isPresent()){
            Goods existing = repository.findById(id).get();
            existing.setName(goods.getName());
            existing.setPrice(goods.getPrice());
            existing.setDescription(goods.getDescription());
            existing.setTotalStock(goods.getTotalStock());
            Goods updated = repository.save(existing);
            return updated;
        }else{
            return null;
        }
    }

    public void updatedAllGoods(List<GoodsDto> goodsDtos){
        if(!goodsDtos.isEmpty()){
             goodsDtos.stream().forEach(g -> updateGoods(g.getId(),g));
        }
    }

    public Boolean deleteGood(Long id){
        if(repository.findById(id).isPresent()){
            repository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }


    public Boolean deleteGoods(List<GoodsDto> goodsDtos){
        if(!goodsDtos.isEmpty()){
            goodsDtos.stream().forEach(g -> deleteGood(g.getId()));
            return true;
        }else{
            return false;
        }
    }
}