package Ega.api.client.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Ega.api.client.Entities.Client;
import Ega.api.client.Services.ClientService;

@RestController()
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/clients/{id}")
    public Client get(@PathVariable Integer id) {
        Client client = clientService.get(id);
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }
        return client;
    }

    @PostMapping("/clients")
    public Client save(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping("/clients/{id}")
    public Client update(@PathVariable Integer id, @RequestBody Client clientDetails) {
        Client client = clientService.get(id);
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }
        if (clientDetails.getLastName() != null) {
            client.setLastName(clientDetails.getLastName());
        }
        if (clientDetails.getFirstName() != null) {
            client.setFirstName(clientDetails.getFirstName());
        }
        if (clientDetails.getBirthDate() != null) {
            client.setBirthDate(clientDetails.getBirthDate());
        }
        if (clientDetails.getSex() != null) {
            client.setSex(clientDetails.getSex());
        }
        if (clientDetails.getAdress() != null) {
            client.setAdress(clientDetails.getAdress());
        }
        if (clientDetails.getPhoneNumber() != null) {
            client.setPhoneNumber(clientDetails.getPhoneNumber());
        }
        if (clientDetails.getEmail() != null) {
            client.setEmail(clientDetails.getEmail());
        }
        if (clientDetails.getNationality() != null) {
            client.setNationality(clientDetails.getNationality());
        }
        return clientService.save(client);
    }

    @DeleteMapping("/clients/{id}")
    public void delete(@PathVariable Integer id) {
        clientService.delete(id);
    }

}
