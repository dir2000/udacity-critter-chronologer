package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {
    private CustomerRepository customerRepository;

    public PetMapper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Pet toPet(PetDTO dto) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(dto, pet);
        long ownerId = dto.getOwnerId();
        if (ownerId != 0) {
            Customer customer =customerRepository.findById(ownerId).orElseThrow(
                () -> new CustomerNotFoundException("Customer not found by id " + ownerId));
            pet.setCustomer(customer);
        }
        return pet;
    }

    public PetDTO tpDTO(Pet pet) {
        PetDTO dto = new PetDTO();
        BeanUtils.copyProperties(pet, dto);
        if (pet.getCustomer() != null) {
            dto.setOwnerId(pet.getCustomer().getId());
        }
        return dto;
    }
}
