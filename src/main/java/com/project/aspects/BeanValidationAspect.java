package com.project.aspects;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class BeanValidationAspect {

    private final ObjectMapper objectMapper;


    @Pointcut(" execution (* com.project.controller.*.*(..))")
    public void controllerMethods() {

    }

    @Pointcut(" @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping(){

    }

    @Pointcut(" @annotation(org.springframework.web.bind.annotation.PatchMapping)")
    public void patchMapping(){

    }

    @Pointcut(" @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){

    }


    @Around("controllerMethods() && ( postMapping() || putMapping() || patchMapping())")
    public Object aroundPutOrPost(ProceedingJoinPoint jp) throws Throwable {
        log.debug("Beginning an advice around a PUT/POST request mapping to check Bean Validation errors.");
        Object[] args = jp.getArgs();
        if (args != null) {
            log.debug("Number of arguments : " + jp.getArgs().length);

            for (Object arg : args
                    ) {
                if (arg instanceof BindingResult) {
                    if (((BindingResult) arg).hasErrors()) {
                        log.error("Intercepted with validation errors. " +
                                "Will not proceed with controller method's execution");
                        List<ObjectError> allErrors = ((BindingResult) arg).getAllErrors();
                        GenericResponseDto responseDto = new GenericResponseDto();
                        for (ObjectError oe: allErrors
                                ) {
                            if(oe instanceof FieldError){
                                FieldError fe = (FieldError)oe;
                                responseDto.addMessage(fe.getField()+" : "+ fe.getDefaultMessage());
                            }
                        }
                        log.error("JSON payload does not pass validation: {}", objectMapper.writeValueAsString(responseDto));
                        return ResponseEntity.unprocessableEntity().body(responseDto);
                    }
                }
            }
        }
        return jp.proceed();
    }


}

