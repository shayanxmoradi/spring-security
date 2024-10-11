//package org.example.security;
//
//import com.github.javafaker.Faker;
//import jakarta.validation.constraints.NotBlank;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@RestController
//@RequestMapping("v1/contacts")
//public class ContacController {
//    private final Faker faker = new Faker();
//    private final List<Contact> listContacts = new ArrayList<>();
//
//    // Contact record without ID
//    public record Contact(String name, String phone) {}
//
//    // Initialize the list with 10 random contacts
//    public ContacController() {
//        listContacts.addAll(generateContacts(10));
//    }
//
//    // Generate a list of random contacts
//    private List<Contact> generateContacts(int count) {
//        return IntStream.range(0, count)
//                .mapToObj(_ -> new Contact(faker.name().name(), faker.phoneNumber().phoneNumber()))
//                .toList();
//    }
//
//
//    @PutMapping("/update")
//    public Contact updateContact(@RequestParam String name, @RequestParam String phone, @RequestBody Contact updatedContact) {
//        Contact existingContact = getContactByNameAndPhone(name, phone);
//        listContacts.remove(existingContact);
//        listContacts.add(updatedContact);
//        return updatedContact;
//    }
//
//    // CRUD: Delete a contact by name and phone
//    @DeleteMapping("/delete")
//    public String deleteContact(@RequestParam String name, @RequestParam String phone) {
//        Contact contact = getContactByNameAndPhone(name, phone);
//        listContacts.remove(contact);
//        return "Contact with name " + name + " and phone " + phone + " was deleted.";
//    }
//
//
//    @PostMapping
//    public Contact createContact(@Validated @RequestBody Contact contact) {
//        listContacts.add(contact);
//        return new Contact(contact.name,contact.phone);
//    }
//
//    @GetMapping
//    public List<Contact> getContacts() {
////        List<Contact> listContacts = IntStream.range(0, 10)
////                .mapToObj(_ -> new Contact(
////                                faker.name().name(),
////                                faker.phoneNumber().phoneNumber()
////                        )
////                ).toList();
//        return listContacts;
//    }
//    @GetMapping("/hi")
//    public String hi() {
//        return "hi";
//    }
//
//    record A(Integer a, Object b, Boolean c) {
//    }
////   @Operation(hidden = true)
////    @PostMapping("/dynamic")
////    public String dynamicReq(@RequestBody Map<String, Object> json, ObjectMapper mapper) {
////        A a = mapper.convertValue(json.get("a"), A.class);
////        return a.toString();
////    }
//
//    @GetMapping("/search")
//    public List<Contact> searchContacts(SearchParams searchParams) {
//        return listContacts.stream()
//                .filter(contact ->
//                        (searchParams.name() == null || contact.name().contains(searchParams.name())) &&
//                        (searchParams.phone() == null || contact.phone().contains(searchParams.phone()))
//                )
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping("/param")
//    public List<Contact> getContacts(SearchParams searchParams
//    ) {
//        return null;
//    }
//
//    // not working
//    public record SearchParams(
//           @NotBlank String name,
//          @NotBlank  String phone
//    ) {
//    }
//}