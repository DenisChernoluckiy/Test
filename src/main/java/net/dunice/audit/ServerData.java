package net.dunice.audit;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ServerData {

    private final Environment environment;

    public ServerData(Environment environment) {
        this.environment = environment;
    }

    public String getAuditServerUrl() {
        return environment.getProperty("audit.server.url");
    }
}
