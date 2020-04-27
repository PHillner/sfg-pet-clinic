package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {

        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Micke");
        owner1.setLastName("Långnäbb");
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Kalle");
        owner2.setLastName("Katala");
        ownerService.save(owner2);

        System.out.println("Loaded Owners. Yay!");

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Ville");
        vet1.setLastName("Veikkonen");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("Per");
        vet2.setLastName("Veterinär");
        vetService.save(vet2);

        System.out.println("Loaded Vets. Go wild!");
    }
}
