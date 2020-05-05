package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();

        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType cat = new PetType();
        cat.setName("Cat");
        cat = petTypeService.save(cat);

        PetType dog = new PetType();
        dog.setName("Dog");
        dog = petTypeService.save(dog);

        PetType bird = new PetType();
        bird.setName("Bird");
        bird = petTypeService.save(bird);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        radiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        surgery = specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        dentistry = specialityService.save(dentistry);

        System.out.print("Loading owners... ");

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

        Visit birdVisit = new Visit();
        birdVisit.setPet(kallesPet);
        birdVisit.setDate(LocalDate.now());
        birdVisit.setDescription("Birds yearly vaccination");
        visitService.save(birdVisit);

        System.out.println("Done. Almost ready...");
        System.out.print("Loading vets...   ");

        Vet vet1 = new Vet();
        vet1.setFirstName("Ville");
        vet1.setLastName("Veikkonen");
        vet1.getSpecialities().add(dentistry);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Per");
        vet2.setLastName("Veterinär");
        vet2.getSpecialities().add(radiology);
        vet2.getSpecialities().add(surgery);
        vetService.save(vet2);

        System.out.println("Done. Go wild!");
    }
}
