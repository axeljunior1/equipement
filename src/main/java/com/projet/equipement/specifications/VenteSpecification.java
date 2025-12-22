package com.projet.equipement.specifications;


import com.projet.equipement.dto.vente.VenteFilterDto;
import com.projet.equipement.entity.Vente;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class VenteSpecification {

    public static Specification<Vente> filter(VenteFilterDto f) {
        return (root, query, cb) -> {

            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            // 🔢 Numéro de vente (exact ou partiel)
            if (f.getVenteId() != null) {
                if (Boolean.TRUE.equals(f.getVenteIdPartiel())) {
                    predicates.add(
                            cb.equal(root.get("id"), f.getVenteId())

                    );
                } else {
                    predicates.add(
                            cb.equal(root.get("id"), f.getVenteId())
                    );
                }
            }

            // 💰 Montant total (plage)
            if (f.getMontantMin() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("montantTotal"),
                                f.getMontantMin()
                        )
                );
            }

            if (f.getMontantMax() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("montantTotal"),
                                f.getMontantMax()
                        )
                );
            }

            // 👤 Client
            if (f.getClientId() != null) {
                predicates.add(
                        cb.equal(
                                root.get("client").get("id"),
                                f.getClientId()
                        )
                );
            }

            // 🧑‍💼 Employé
            if (f.getEmployeId() != null) {
                predicates.add(
                        cb.equal(
                                root.get("employe").get("id"),
                                f.getEmployeId()
                        )
                );
            }

            // ⭐ ÉTAT DE LA VENTE (clé métier)
            if (f.getEtatVenteId() != null) {
                predicates.add(
                        cb.equal(
                                root.get("etat").get("id"),
                                f.getEtatVenteId()
                        )
                );
            }

            // 🟢 Actif / supprimé
            if (f.getActif() != null) {
                predicates.add(
                        cb.equal(
                                root.get("actif"),
                                f.getActif()
                        )
                );
            }

            // 📅 Date de création (plage)
            if (f.getCreatedAtFrom() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("createdAt"),
                                f.getCreatedAtFrom()
                        )
                );
            }

            if (f.getCreatedAtTo() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("createdAt"),
                                f.getCreatedAtTo()
                        )
                );
            }
            // 📦 Filtre par produit contenu dans la vente
            if (f.getProduitId() != null) {

                Join<Object, Object> ligneVenteJoin =
                        root.join("ligneVentes", JoinType.INNER);

                Join<Object, Object> produitJoin =
                        ligneVenteJoin.join("produit", JoinType.INNER);

                predicates.add(
                        cb.equal(
                                produitJoin.get("id"),
                                f.getProduitId()
                        )
                );
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
