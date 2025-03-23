package com.microservices.inventory.service;

import com.microservices.inventory.dto.PurchaseDTO;
import com.microservices.inventory.entity.Purchase;
import com.microservices.inventory.repository.PurchaseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Transactional
    public PurchaseDTO createPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseDTO, purchase);
        
        Purchase savedPurchase = purchaseRepository.save(purchase);
        
        PurchaseDTO responseDTO = new PurchaseDTO();
        BeanUtils.copyProperties(savedPurchase, responseDTO);
        return responseDTO;
    }
}
