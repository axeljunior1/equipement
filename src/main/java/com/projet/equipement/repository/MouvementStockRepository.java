package com.projet.equipement.repository;

import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.TypeMouvementStock;
import com.projet.equipement.entity.enumeration.TypeMouvement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock,  Long> {

    Optional<MouvementStock> findByIdEvenementOrigineAndIdLigneOrigine( Integer idEvenementOrigine, Integer idLigneOrigine );

    List<MouvementStock> findByIdEvenementOrigine(Integer idEvenementOrigine);


    @Query("select mv from MouvementStock mv where mv.produit.id = :id")
    Page<MouvementStock> findAllMouvementStockByProductId(@Param("id") Long id, Pageable pageable);

    @Query("select mv from MouvementStock mv where mv.produit.id = :id")
    List<MouvementStock> findAllMouvementStockByProductIdList(@Param("id") Long id);

    List<MouvementStock> findByProduitAndTypeMouvement(Produit produit, TypeMouvementStock typeMouvementStock);

    Optional<MouvementStock> findByReference(String reference);

    void deleteByReference(String reference);

    void deleteByIdEvenementOrigineAndIdLigneOrigine(Integer idEvenementOrigine, Integer idLigneOrigine);

    @Modifying
    @Transactional
    @Query("delete from MouvementStock ms where ms.idEvenementOrigine = :idEveOri and ms.typeMouvement.code = :typeMvtCode ")
    void deleteByIdEvenementOrigineAndTypeMouvementCode(@Param("idEveOri") Long idEveOri, @Param("typeMvtCode") String typeMvtCode);

    @Query("select ms from MouvementStock ms where ms.idEvenementOrigine = :idEveOri and ms.typeMouvement.code = :typeMvtCode ")
    List<MouvementStock> findByIdEvenementOrigineAndTypeMouvementCode(@Param("idEveOri") Long idEveOri, @Param("typeMvtCode") String typeMvtCode);


}

