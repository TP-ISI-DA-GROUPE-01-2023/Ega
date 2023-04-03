package Ega.api.client.Entities;

import java.time.LocalDate;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String accountNumber;
    private String type;
    private LocalDate creationDate;
    private double balance;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @JsonProperty("clientId")
    public Integer getClientId() {
        return client.getId();
    }

    public void generateAccountNumber() {
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456";
        StringBuilder accountNumber = new StringBuilder();
        Random random = new Random();

        // Génère 5 caractères alphanumériques aléatoires
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(alphanumeric.length());
            accountNumber.append(alphanumeric.charAt(index));
        }

        // Concatène l'année de création du compte
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        accountNumber.append(year);

        this.setAccountNumber(accountNumber.toString());
    }

}
