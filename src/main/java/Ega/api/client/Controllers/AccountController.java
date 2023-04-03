package Ega.api.client.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Ega.api.client.Entities.Account;
import Ega.api.client.Entities.Client;
import Ega.api.client.Services.AccountService;
import Ega.api.client.Services.ClientService;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<Account> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/accounts/{id}")
    public Account get(@PathVariable Integer id) {
        Account account = accountService.get(id);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        return account;
    }

    @PostMapping("/accounts")
    public Account save(@RequestParam("clientId") Integer clientId, @RequestBody Account accountDetails) {
        Client client = clientService.get(clientId);
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }
        if (accountDetails.getType().toLowerCase().equals("epargne")) {
            accountDetails.setType("Epargne");
        } else if (accountDetails.getType().toLowerCase().equals("courant")) {
            accountDetails.setType("Courant");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect account type");
        }
        accountDetails.setBalance(0);
        accountDetails.setClient(client);
        return accountService.save(accountDetails);
    }

    @PutMapping("/accounts/deposit/{id}")
    public Account deposit(@PathVariable Integer id, @RequestParam("amount") Double amount) {
        Account account = accountService.get(id);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        account.setBalance(account.getBalance() + amount);
        return accountService.save(account);
    }

    @PutMapping("/accounts/withdraw/{id}")
    public Account withdraw(@PathVariable Integer id, @RequestParam("amount") Double amount) {
        Account account = accountService.get(id);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }

        if (account.getBalance() < amount) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
        return accountService.save(account);
    }

    @PutMapping("/accounts/transfer")
    public List<Account> transfert(@RequestParam("senderAccountId") Integer senderAccountId,
            @RequestParam("receiverAccountId") Integer receiverAccountId, @RequestParam("amount") Double amount) {

        Account senderAccount = accountService.get(senderAccountId);
        if (senderAccount == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender's account not found");
        }
        Account receiverAccount = accountService.get(receiverAccountId);
        if (receiverAccount == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiver's account not found");
        }

        if (senderAccount.getBalance() < amount) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Insufficient balance");
        }
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        accountService.save(senderAccount);
        accountService.save(receiverAccount);
        List<Account> accounts = new ArrayList<>();
        accounts.add(senderAccount);
        accounts.add(receiverAccount);
        return accounts;
    }

    @PutMapping("/accounts/{id}")
    public Account update(@PathVariable Integer id,
            @RequestParam("clientId") Integer clientId, @RequestBody Account accountDetails) {
        Account account = accountService.get(id);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        Client client = clientService.get(clientId);
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }
        account.setClient(client);
        if (accountDetails.getType().toLowerCase() == "epargne") {
            account.setType("Epargne");
        } else if (accountDetails.getType().toLowerCase() != "courant") {
            account.setType("Courant");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect account type");
        }

        if (accountDetails.getType() != null) {
            account.setType(accountDetails.getType());
        }

        if (accountDetails.getCreationDate() != null) {
            account.setCreationDate(accountDetails.getCreationDate());
        }

        account.setBalance(accountDetails.getBalance());

        return accountService.save(account);
    }

    @DeleteMapping("accounts/{id}")
    public void delete(@PathVariable Integer id) {
        Account account = accountService.get(id);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        accountService.delete(id);
    }
}
