package com.projet.equipement.repository;

import com.projet.equipement.entity.RoleEmploye;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleEmployeRepository extends JpaRepository<RoleEmploye, Long> {
    @Modifying
    @Query("DELETE FROM RoleEmploye re WHERE re.id.tenantId = :tenantId AND re.id.employeeId = :employeId")
    void deleteByTenantAndEmployeId(@Param("tenantId") String tenantId, @Param("employeId") Long employeId);

}
