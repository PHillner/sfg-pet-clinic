package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OwnerMapServiceTest {

    OwnerMapService ownerMapService;

    final Long ownerId = 1L;
    final String lastName = "Leppänen";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void testFindAll() {
        Set<Owner> ownerSet = ownerMapService.findAll();
        assertEquals(1, ownerSet.size());
    }

    @Test
    void testFindById() {
        Owner owner = ownerMapService.findById(ownerId);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void testSaveExistingId() {
        Long id2 = 2L;
        Owner owner2 = Owner.builder().id(id2).build();
        Owner savedOwner = ownerMapService.save(owner2);
        assertEquals(owner2.getId(), savedOwner.getId());
    }

    @Test
    void testSaveNoId() {
        Owner savedOwner = ownerMapService.save(Owner.builder().build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void testDelete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void testDeleteById() {
        ownerMapService.deleteById(ownerId);
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void testFindByLastName() {
        Owner ownerWithLastName = ownerMapService.findByLastName(lastName);
        assertNotNull(ownerWithLastName);
        assertEquals(lastName, ownerWithLastName.getLastName());
        assertEquals(ownerId, ownerWithLastName.getId());
    }

    @Test
    void testFindByLastNameNotFound() {
        Owner ownerNull = ownerMapService.findByLastName("foo");
        assertNull(ownerNull);
    }
}
