package com.projet.equipement.specifications;

import com.projet.equipement.entity.Produit;
import org.springframework.data.jpa.domain.Specification;

public class ProduitSpecification {

    public static Specification<Produit> hasDescription(String description) {
        return (root, query, criteriaBuilder) -> description == null ?
                null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }

    public static Specification<Produit> isActif(Boolean actif) {
        return (root, query, criteriaBuilder) ->
                (actif == null) ? null : criteriaBuilder.equal(root.get("actif"), actif);
    }
    public static Specification<Produit> hasNom(String nom) {
        return (root, query, criteriaBuilder) -> nom == null ?
                null :
                criteriaBuilder.like(criteriaBuilder.lower(root.get("nom")), "%" + nom.toLowerCase() + "%");
    }

    public static Specification<Produit> hasStockBetween(Integer min, Integer max) {
        return (root, query, criteriaBuilder) -> {
            if (min == null && max == null) {
                return null;
            } else if (min == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("stockInitial"), max);
            } else if (max == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("stockInitial"), min);
            } else {
                return criteriaBuilder.between(root.get("stockInitial"), min, max);
            }
        };
    }

    public static Specification<Produit> hasPrixBetween(Double min, Double max) {
        return (root, query, criteriaBuilder) -> {
            if (min == null && max == null) {
                return null;
            } else if (min == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("prixVente"), max);
            } else if (max == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("prixVente"), min);
            } else {
                return criteriaBuilder.between(root.get("prixVente"), min, max);
            }
        };
    }


}
