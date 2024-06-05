package org.cynic.gql_api;

import java.time.Clock;
import org.cynic.gql_api.framework.coercing.OffsetDateTimeCoercing;
import org.cynic.gql_api.framework.coercing.VoidCoercing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.graphql.GraphQlAutoConfiguration;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.boot.autoconfigure.graphql.servlet.GraphQlWebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.graphql.data.federation.FederationSchemaFactory;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@SpringBootConfiguration
@ImportAutoConfiguration({
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class,
    TransactionAutoConfiguration.class,
    JpaRepositoriesAutoConfiguration.class,
    LiquibaseAutoConfiguration.class,

    JacksonAutoConfiguration.class,

    HttpMessageConvertersAutoConfiguration.class,

    ServletWebServerFactoryAutoConfiguration.class,
    DispatcherServletAutoConfiguration.class,
    WebMvcAutoConfiguration.class,

    GraphQlAutoConfiguration.class,
    GraphQlWebMvcAutoConfiguration.class
})
@ComponentScan(excludeFilters = {
    @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {TypeExcludeFilter.class}),
    @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {AutoConfigurationExcludeFilter.class})
})
@EnableJpaRepositories
@EntityScan("org.cynic.logistics.domain.entity")
public class Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    public Configuration() {
        Package pkg = getClass().getPackage();

        LOGGER.info(Constants.AUDIT_MARKER,
            "[{}-{}] STARTED",
            pkg.getImplementationTitle(),
            pkg.getImplementationVersion()
        );
    }

    @Bean
    public Clock clock() {
        return Clock.system(Constants.SYSTEM_ZONE_ID);
    }

    @Bean
    public GraphQlSourceBuilderCustomizer customizer(FederationSchemaFactory factory) {
        return builder -> builder
            .schemaFactory(factory::createGraphQLSchema);
    }

    @Bean
    public FederationSchemaFactory federationSchemaFactory() {
        return new FederationSchemaFactory();
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return builder -> builder
            .scalar(VoidCoercing.SCALAR_TYPE)
            .scalar(OffsetDateTimeCoercing.SCALAR_TYPE);
    }
}