package com.smartcity.profil;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;  // CET IMPORT MANQUAIT !

@Configuration
public class GraphQLProvider {

    @Bean
    public GraphQL graphQL() throws Exception {
        String sdl = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("graphql/schema.graphqls").getInputStream(),
                        StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);

        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("profils", env -> getAllProfils())
                        .dataFetcher("profil", env -> {
                            String id = env.getArgument("id");
                            return getProfilById(id);
                        }))
                .build();

        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        return GraphQL.newGraphQL(schema).build();
    }

    private List<Profil> getAllProfils() {
        return List.of(
                new Profil("1", "Ahmed", "Karim", List.of("bus", "métro"), false),
                new Profil("2", "Sara", "Leila", List.of("vélo"), true),
                new Profil("3", "Luc", "Martin", List.of("voiture"), false)
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