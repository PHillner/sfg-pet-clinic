package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"/","/index","/index.html"})
    public String listOwners(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

    public String initFindForm(Model model) {
        model.addAttribute("owner", new Owner());
        return "owners/findOwners";
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", new Owner());
        return "owners/findOwners";
    }

    @GetMapping("")
    public String processFindForm(Model model, Owner owner, BindingResult result) {
        // Allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // Empty string signifies broadest possible search
        }

        List<Owner> results = ownerService.findAllByLastNameLike(owner.getLastName());

        if (results.isEmpty()) {
            // None found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // One found
            owner = results.iterator().next();
            return String.format("redirect:/owners/%s", owner.getId());
        } else {
            // Multiple found
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(ownerId));
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid  Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            Owner ownerSaved = ownerService.save(owner);
            return String.format("redirect:/owners/%d", ownerSaved.getId());
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("ownerId") Long ownerId, Model model) {
        Owner ownerFound = ownerService.findById(ownerId);
        if (ownerFound == null) {
            // Invalid owner
            return String.format("redirect:/owners/%d", ownerId);
        }
        model.addAttribute("owner", ownerFound);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@PathVariable("ownerId") Long ownerId, @Valid  Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            owner.setId(ownerId);
            Owner ownerSaved = ownerService.save(owner);
            return String.format("redirect:/owners/%d", ownerSaved.getId());
        }
    }
}
