package Ega.api.client.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Ega.api.client.Entities.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
