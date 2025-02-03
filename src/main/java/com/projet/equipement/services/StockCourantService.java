package com.projet.equipement.services;


import com.projet.equipement.entity.StockCourant;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.StockCourantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StockCourantService {

    private final StockCourantRepository stockCourantRepository;

    public StockCourantService(StockCourantRepository stockCourantRepository) {
        this.stockCourantRepository = stockCourantRepository;
    }

    public Page<StockCourant> getStockCourant(Pageable pageable) {
        return stockCourantRepository.findAll(pageable);
    }


    public List<StockCourant> getStockCourantByIds(List<Long> ids) {
        return stockCourantRepository.findAllById(ids);
    }

    /**
     *
     * @param id c'est l'id du produit
     * @return {StockCourant}
     */
    public StockCourant getStockCourantById(Long id) {
        return stockCourantRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("StockCourant", id)
        );
    }


}
