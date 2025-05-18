package com.projet.equipement.repository;

import com.projet.equipement.entity.AuthorityRole;
import com.projet.equipement.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRoleRepository extends JpaRepository<AuthorityRole, Long> {
    @Modifying
    @Query("DELETE FROM AuthorityRole re WHERE re.id.tenantId = :tenantId AND re.id.roleId = :roleId")
    void deleteByTenantAndRoleId(@Param("tenantId") String tenantId, @Param("roleId") Long roleId);

    Page<AuthorityRole> findByRole(Role role, Pageable pageable);
}
