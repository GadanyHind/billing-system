package ma.enset.customerservice;

import ma.enset.customerservice.entities.Customer;
import ma.enset.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(CustomerRepository customerRepository){
		return args -> {
			customerRepository.save(new Customer(null,"Hind", "gadanyhind@gmail.com"));
			customerRepository.save(new Customer(null,"Wissal", "gadanywissal@gmail.com"));
			customerRepository.save(new Customer(null,"Khadija", "karimikhadija@gmail.com"));
			customerRepository.save(new Customer(null,"Hassan", "gadanyhassan@gmail.com"));
			customerRepository.findAll().forEach(customer -> {
				System.out.println(customer.toString());
			});

		};
	}

}
