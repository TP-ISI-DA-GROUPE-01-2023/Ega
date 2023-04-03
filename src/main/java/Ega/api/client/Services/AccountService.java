package Ega.api.client.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Ega.api.client.Entities.Account;
import Ega.api.client.Repositories.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account get(Integer id) {
        return accountRepository.findById(id)
                .orElse(null);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }

}
