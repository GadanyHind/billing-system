package ma.enset.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }


    //@Bean
    RouteLocator routesLocator(RouteLocatorBuilder builder){

        //static
       /* return builder.routes()
                .route((r)->r.path("/customers/**").uri("http://localhost:8081/"))
                .route((r)->r.path("/products/**").uri("http://localhost:8082/"))
                .build(); */

        return builder.routes()
                .route((r)->r.path("/customers/**").uri("lb://customer-service"))
                .route((r)->r.path("/products/**").uri("lb://inventory-service"))
                .build();
    }

   //Dynamique
   //À chaque fois que tu reçois une requête, regarde dans l'URI pour trouver le nom du microservice.
   //Ensuite, prends l'URI du microservice et redirige la requête vers celui-ci.
   //Je n'ai pas besoin de modifier cette configuration
   // je peux ajouter autant de microservices que je veux et les consulter directement via le navigateur
   //Consultation : localhost:8888(port gateway)/NOM-DE-SERVICE/customers or products or ...
   @Bean
   DiscoveryClientRouteDefinitionLocator definitionLocator(
           ReactiveDiscoveryClient rdc,
           DiscoveryLocatorProperties properties) {
    return new DiscoveryClientRouteDefinitionLocator(rdc, properties);
   }

}