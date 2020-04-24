package guru.springframework.sfgpetclinic.model;

/**
 * @author phillner
 * @created 24.4.2020
 */
public class PetType extends BaseEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
