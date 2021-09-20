package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {
    private CustomerRepository customerRepository;
    private PetRepository petRepository;

    public PetService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public Pet savePet(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        Customer customer = savedPet.getCustomer();
        List<Pet> pets = customer.getPets();
        if (pets != null) {
            pets = new ArrayList<>();
        }
        if (!pets.contains(pet)) {
            pets.add(pet);
        }
        customer.setPets(pets);
        customerRepository.save(customer);
        return savedPet;

    }

    public Pet getPet(long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Pet not found by id" + petId));
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        return petRepository.findAllByCustomerId(ownerId);
    }
}
