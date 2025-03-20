package com.BD.Demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BD.Demo.entities.Batch;
import com.BD.Demo.repositories.BatchRepository;

@Service
public class BatchService {
    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @Transactional(readOnly = true)
    public List<Batch> findAll() {
        return batchRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Batch> findById(Long id) {
        return batchRepository.findById(id);
    }

    public Batch saveBatch(Batch batch) {
        return batchRepository.save(batch);
    }

    public Batch updateBatch(Batch batch) {
        return batchRepository.save(batch);
    }

    public void deleteBatch(Long id) {
        batchRepository.deleteById(id);
    }

    public List<Batch> getLowStockProducts(int threshold) {
        return batchRepository.findLowStockProducts(threshold);
    }

}
