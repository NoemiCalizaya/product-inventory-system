package com.BD.Demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BD.Demo.entities.Purchase;
import com.BD.Demo.repositories.PurchaseRepository;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Transactional(readOnly = true)
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Purchase> findById(Long id) {
        return purchaseRepository.findById(id);
    }

    public Purchase savePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public Purchase updatePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }
}
