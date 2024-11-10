package ma.enset.inventory;

import ma.enset.inventory.entities.Product;
import ma.enset.inventory.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}


	@Bean
	CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration config) {
		config.exposeIdsFor(Product.class);
		return args -> {
			productRepository.save(new Product(null, "Ordinateur", 12000, 5));
			productRepository.save(new Product(null,"Telephone",10000,12));
			productRepository.save(new Product(null,"Smart Watch",5000,8));

			productRepository.findAll().forEach(p->{
				System.out.println(p.toString());
			});
		};
	}

}
