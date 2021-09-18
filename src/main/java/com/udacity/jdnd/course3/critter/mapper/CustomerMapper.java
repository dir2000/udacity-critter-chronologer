package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {
    private PetRepository petRepository;

    public CustomerMapper(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Customer toCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        if (dto.getPetIds() != null) {
            List<Pet> pets = dto
                    .getPetIds()
                    .stream()
                    .map(id -> petRepository.findById(id).orElseThrow(
                            () -> new PetNotFoundException("Pet not found by id " + id)))
                    .collect(Collectors.toList());
            customer.setPets(pets);
        }
        return customer;
    }

    public CustomerDTO tpDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer, dto);
        if (customer.getPets() != null) {
            List<Long> petIds = customer
                    .getPets()
                    .stream()
                    .map(pet -> pet.getId())
                    .collect(Collectors.toList());
        }
        return dto;
    }
}
