package com.projet.equipement.repository;

import com.projet.equipement.entity.Achat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchatRepository extends JpaRepository<Achat, Long> {

    @Query("select a from Achat a where a.actif=true")
    Page<Achat> findAllPage(Pageable pageable);
}
