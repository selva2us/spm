package com.ms.spm.repository;

import com.ms.spm.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods,Long> {
}
