package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {
    private UserService customerService;

    public PetMapper(UserService customerService) {
        this.customerService = customerService;
    }

    public Pet toPet(PetDTO dto) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(dto, pet);
        long ownerId = dto.getOwnerId();
        if (ownerId != 0) {
            Customer customer = customerService.getCustomer(ownerId);
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
