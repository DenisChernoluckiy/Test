package net.dunice.audit;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static java.util.Arrays.stream;

@Slf4j
@Component
@Aspect
public class AuditAspect {

    @Resource
    private ApplicationContext applicationContext;

    @Pointcut("@annotation(net.dunice.audit.Audit)")
    public void hasAudit() {
    }

    @After("hasAudit()")
    public void logAudit(JoinPoint joinPoint) {

        Arrays.stream(joinPoint.getArgs())
                .forEach(o -> System.out.println("arg value: " + o.toString()));

        Arrays.stream(joinPoint.getArgs()).forEach(System.out::println);

        ServerData auditServerUrl = applicationContext.getBean("serverData", ServerData.class);
        System.out.println("audit server url: " + auditServerUrl.getAuditServerUrl());
        System.out.println("Signature: " + joinPoint.getSignature());
        System.out.println("Kind: " + joinPoint.getKind());
        System.out.println("StaticPart: " + joinPoint.getStaticPart());
        System.out.println("Target: " + joinPoint.getTarget());


        var url = auditServerUrl.getAuditServerUrl();


        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        var personJsonObject = new JSONObject();
        personJsonObject.put("message", stream(joinPoint.getArgs()).findFirst().toString());
        HttpEntity<String> request =
                new HttpEntity<>(personJsonObject.toString(), headers);

        restTemplate.postForEntity(url, request, String.class);
    }
}
