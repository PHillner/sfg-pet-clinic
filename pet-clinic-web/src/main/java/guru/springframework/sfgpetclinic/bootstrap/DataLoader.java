package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType cat = new PetType();
        cat.setName("Cat");
        cat = petTypeService.save(cat);

        PetType dog = new PetType();
        dog.setName("Dog");
        dog = petTypeService.save(dog);

        PetType bird = new PetType();
        bird.setName("Bird");
        bird = petTypeService.save(bird);

        Owner owner1 = new Owner();
        owner1.setFirstName("Micke");
        owner1.setLastName("Långnäbb");
        owner1.setAddress("1st Street");
        owner1.setCity("City 1");
        owner1.setTelephone("123123123");

        Pet mickesPet = new Pet();
        mickesPet.setName("Kitty");
        mickesPet.setPetType(cat);
        mickesPet.setBirthDate(LocalDate.now());
        mickesPet.setOwner(owner1);
        owner1.getPets().add(mickesPet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Kalle");
        owner2.setLastName("Katala");
        owner2.setAddress("21st Street");
        owner2.setCity("City 9");
        owner2.setTelephone("123456789");

        Pet kallesPet = new Pet();
        kallesPet.setName("Birdie");
        kallesPet.setPetType(bird);
        kallesPet.setBirthDate(LocalDate.now().minusMonths(4));
        kallesPet.setOwner(owner2);
        owner2.getPets().add(kallesPet);

        ownerService.save(owner2);

        System.out.println("Loaded Owners. Almost ready...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Ville");
        vet1.setLastName("Veikkonen");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Per");
        vet2.setLastName("Veterinär");
        vetService.save(vet2);

        System.out.println("Loaded Vets. Go wild!");
    }
}
