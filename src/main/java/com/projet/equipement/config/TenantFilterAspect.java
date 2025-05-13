package com.projet.equipement.config;


import com.projet.equipement.entity.TenantContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TenantFilterAspect {

    @PersistenceContext
    private EntityManager em;

//    @Before("execution(* com.projet.equipement.services.*.*(..))")
//    public void before() {
//        System.out.println("Loging for Tenant :");
//    }
    @Pointcut("execution(* com.projet.equipement.services.*.*(..))")
    public void transactionalMethod() {}

    @Around("transactionalMethod()")
    public Object applyTenantFilter(ProceedingJoinPoint pjp) throws Throwable {
        Session session = em.unwrap(Session.class);
        if (session.getEnabledFilter("tenantFilter") == null) {
            Filter filter = session.enableFilter("tenantFilter");
            filter.setParameter("tenantId", TenantContext.getTenantId());
        }
        System.out.println("Set tenant filter for: " + TenantContext.getTenantId());
        return pjp.proceed();
    }
}
