package Ega.api.client.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import Ega.api.client.Entities.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
