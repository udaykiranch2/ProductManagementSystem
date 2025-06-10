
/**
 * This annotation is used to specify the name of the table to which the annotated entity class is mapped.
 * In this case, the Customer class is mapped to the "customer" table in the database.
 *
 * @author YourName
 * @since 1.0
 */

package com.pmspProject.pmsp.model;

import java.util.List;
import java.util.UUID;

import com.stripe.model.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotBlank(message = "First name is mandatory")
    @Column(name= "FIRST_NAME",nullable = false, columnDefinition = "VARCHAR(50)")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(name= "LAST_NAME",nullable = false, columnDefinition = "VARCHAR(50)")
    private String lastName;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is mandatory")
    @Column(name="EMAIL",nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    @NotBlank(message = "Phone number is mandatory")
    @Column(name = "PHONE_NUMBER", nullable = false, columnDefinition = "VARCHAR(15)")
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    @Column(name = "ADDRESS", nullable = false, columnDefinition = "VARCHAR(255)")
    private String address;

    @OneToMany(mappedBy = "CUSTOMER", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @OneToMany(mappedBy = "CUSTOMER", cascade = CascadeType.ALL)
    private List<PaymentMethod> paymentMethods;
    // Getters and setters
}
