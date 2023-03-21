package net.dunice.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan
public class AuditAutoConfiguration {

    @Autowired
    private final Environment environment;


    private final ServerData serverData;

    public AuditAutoConfiguration(Environment environment, ServerData serverData) {
        this.environment = environment;
        this.serverData = serverData;
    }

    public Environment getEnvironment() {
        return environment;
    }

    @Bean
    public ServerData getServerUrl() {
        return serverData;
    }
}
