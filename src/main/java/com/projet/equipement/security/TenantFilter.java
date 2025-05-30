package com.projet.equipement.security;

import com.projet.equipement.entity.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class TenantFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(TenantFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String tenantId = request.getHeader("X-Tenant-ID");
        logger.info("Header X-Tenant-ID reçu : {}", tenantId);

        if (tenantId != null) {
            TenantContext.setTenantId(tenantId);
            logger.info("TenantId '{}' défini dans le contexte", tenantId);
        } else {
            logger.info("Aucun X-Tenant-ID trouvé dans les headers de la requête");
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            logger.info("Nettoyage du TenantContext pour tenant '{}'", TenantContext.getTenantId());
            TenantContext.clear();
        }
    }
}