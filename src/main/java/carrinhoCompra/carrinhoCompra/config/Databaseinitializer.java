package carrinhoCompra.carrinhoCompra.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class Databaseinitializer {

    @Autowired
    private DatabaseClient databaseClient;

    @PostConstruct
    public void initializeDatabase() {
        try {
            // Executa o script de criação de tabelas
            executeScript("schema.sql");

            // Executa o script de inserção de dados
            executeScript("data.sql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeScript(String scriptPath) throws Exception {
        ClassPathResource resource = new ClassPathResource(scriptPath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder script = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
            String[] statements = script.toString().split(";\\s*");
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    databaseClient.sql(statement).fetch().rowsUpdated().block();
                }
            }
        }
    }
}
