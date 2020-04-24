package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Vet;

import java.util.Set;

/**
 * @author phillner
 * @created 24.4.2020
 */
public interface VetService {

    Vet findById(Long id);

    Vet save(Vet vet);

    Set<Vet> findAll();
}
