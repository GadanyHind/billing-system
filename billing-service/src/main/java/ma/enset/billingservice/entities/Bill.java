package ma.enset.billingservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.billingservice.model.Customer;

import java.util.Collection;
import java.util.Date;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Bill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date billingDate;
    @OneToMany(mappedBy = "bill")
    private Collection<ProductItem> products;
    private Long customerId;
    @Transient
    private Customer customer;

    public double getTotalPrice() {
        double somme=0;
        for (ProductItem productItem : products) {
            somme+=productItem.getAmount();
        }
        return somme;
    }
}
