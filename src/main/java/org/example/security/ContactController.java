package org.example.security;


import com.github.javafaker.Faker;
import jakarta.validation.constraints.NotBlank;
import lombok.With;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("v1/contact")
public class ContactController {

    private final Faker faker = new Faker();
    private final List<Contact> listContacts = new ArrayList<>();
@With
    public record Contact(String name, String phone) {}

    public ContactController() {
        listContacts.addAll(generateContacts(20));
    }

    private List<Contact> generateContacts(int count) {
        return IntStream.range(0, count)
                .mapToObj(_ -> new Contact(faker.name().name(), faker.phoneNumber().phoneNumber()))
                .toList();
    }

    @PostMapping
    public Contact createContact(@RequestBody Contact newContact) {
        listContacts.add(newContact);
        return newContact;
    }

//    @GetMapping("/create")
//    public List<Contact> getContacts() {
//        return listContacts;
//    }

    @GetMapping("/search")
    public Contact getContactByNameAndPhone(@RequestParam String name, @RequestParam String phone) {
        return  searchContacts(new SearchParams(name,phone)).stream().findFirst().get();
    }

    @PutMapping("/update")
    public Contact updateContact(@RequestParam String name, @RequestParam String phone, @RequestBody Contact updatedContact) {
        Contact existingContact = getContactByNameAndPhone(name, phone);
        listContacts.remove(existingContact);
        listContacts.add(updatedContact);
        return updatedContact;
    }

    @DeleteMapping("/delete")
    public String deleteContact(@RequestParam String name, @RequestParam String phone) {
        Contact contact = getContactByNameAndPhone(name, phone);
        listContacts.remove(contact);
        return "Contact with name " + name + " and phone " + phone + " was deleted.";
    }

    @GetMapping("/filter")
    public List<Contact> searchContacts(@RequestParam(required = false) String name, @RequestParam(required = false) String phone) {
        return listContacts.stream()
                .filter(contact ->
                        (name == null || contact.name().contains(name)) &&
                        (phone == null || contact.phone().contains(phone))
                )
                .collect(Collectors.toList());
    }

    @GetMapping("/searchz")
    public List<Contact> searchContacts(SearchParams searchParams) {
        return listContacts.stream()
                .filter(contact ->
                        (searchParams.name() == null || contact.name().contains(searchParams.name())) &&
                        (searchParams.phone() == null || contact.phone().contains(searchParams.phone()))
                )
                .collect(Collectors.toList());
    }

    @GetMapping("/param")
    public List<Contact> getContacts(SearchParams searchParams
    ) {
        return null;
    }

    // not working
    public record SearchParams(
            @NotBlank String name,
            @NotBlank  String phone
    ) {
    }

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }
}