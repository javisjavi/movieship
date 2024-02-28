package com.onemore.msnaves.naves.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NavesAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(NavesAspect.class);

    @Before("execution(* com.onemore.msnaves.naves.controller.NavesApi.getNaveById(..)) && args(id)")
    public void beforeGetNaveById(JoinPoint joinPoint, Integer id) {
        if (id < 0) {
            logger.warn("Se solicitó una nave con ID negativo: {}", id);
        }
    }
    
}
