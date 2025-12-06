package com.smartcity.profil;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Configuration
public class GraphQLProvider {

    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeRegistry = schemaParser.parse(
            new ClassPathResource("graphql/schema.graphqls").getFile());

        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
            .type("Query", builder -> builder
                .dataFetcher("profils", env -> getAllProfils())
                .dataFetcher("profil", env -> getProfilById(env.getArgument("id"))))
            .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, wiring);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private List<Profil> getAllProfils() {
        return List.of(
            new Profil("1", "Ahmed", "Karim", List.of("bus", "métro"), false),
            new Profil("2", "Sara", "Leila", List.of("vélo"), true)
        );
    }

    private Profil getProfilById(String id) {
        return getAllProfils().stream()
            .filter(p -> p.id().equals(id))
            .findFirst()
            .orElse(null);
    }

    public record Profil(String id, String nom, String prenom, List<String> preferencesTransport, Boolean alerteSante) {}
}