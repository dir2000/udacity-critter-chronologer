package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {
    private PetService petService;

    public CustomerMapper(PetService petService) {
        this.petService = petService;
    }

    public Customer toCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer);
        if (dto.getPetIds() != null) {
            List<Pet> pets = dto.getPetIds().stream().map(id -> petService.getPet(id))
                    .collect(Collectors.toList());
            customer.setPets(pets);
        }
        return customer;
    }

    public CustomerDTO tpDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer, dto);
        if (customer.getPets() != null) {
            List<Long> petIds = customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
            dto.setPetIds(petIds);
        }
        return dto;
    }
}
