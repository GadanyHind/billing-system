package ma.enset.billingservice.web;

import ma.enset.billingservice.entities.Bill;
import ma.enset.billingservice.feign.CustomerRestClient;
import ma.enset.billingservice.feign.ProductItemRestClient;
import ma.enset.billingservice.model.Customer;
import ma.enset.billingservice.model.Product;
import ma.enset.billingservice.repository.BillRepository;
import ma.enset.billingservice.repository.ProductItemRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;

    public BillingRestController(BillRepository billRepository, ProductItemRepository productRepository,
                                 CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billRepository = billRepository;
        this.productRepository = productRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }

    @GetMapping(path="/fullBill/{id}")
    public Bill getBill(@PathVariable(name= "id") Long id) {
        Bill bill = billRepository.findById(id).get();
        Customer customer= customerRestClient.getCustomerById(bill.getCustomerId());
        bill.setCustomer(customer);
        bill.getProducts().forEach(p->{
            Product product=productItemRestClient.getProductById(p.getProductId());
            p.setProduct(product);
        });
        return bill;
    }

    @GetMapping(path="/customerBill/{customerId}")
    public List<Bill> getBillByCustomerId(@PathVariable(name= "customerId") Long customerId) {
        List<Bill> bills = billRepository.findByCustomerId(customerId);

        // Vérifier si les factures existe
        if (bills.isEmpty()) {
            throw new ResourceNotFoundException("Bills not found for customer id: " + customerId);
        }

        bills.forEach(bill -> {
            Customer customer = customerRestClient.getCustomerById(bill.getCustomerId());
            bill.setCustomer(customer);

            bill.getProducts().forEach(p -> {
                Product product = productItemRestClient.getProductById(p.getProductId());
                p.setProduct(product);
            });
        });
        return bills;
}}
