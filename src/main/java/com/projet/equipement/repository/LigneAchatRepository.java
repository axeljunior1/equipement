package com.projet.equipement.repository;

import com.projet.equipement.entity.LigneAchat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LigneAchatRepository extends JpaRepository<LigneAchat, Long> {

    @Query("select l from LigneAchat l  where l.achat.id = :id")
    Page<LigneAchat> findByAchatId(@Param("id") Long id, Pageable pageable);

}
