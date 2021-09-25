package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleMapper {
    UserService userService;
    PetService petService;

    public ScheduleMapper(UserService userService, PetService petService) {
        this.userService = userService;
        this.petService = petService;
    }

    public Schedule toSchedule(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(dto, schedule);
        if (dto.getPetIds() != null) {
            List<Pet> pets = dto.getPetIds().stream().map(id -> petService.getPet(id)).collect(Collectors.toList());
            schedule.setPets(pets);
        }
        if (dto.getEmployeeIds() != null) {
            List<Employee> employees = dto.getEmployeeIds().stream().map(id -> userService.getEmployee(id)).collect(Collectors.toList());
            schedule.setEmployees(employees);
        }
        return schedule;
    }

    public ScheduleDTO tpDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, dto);
        if (schedule.getPets() != null) {
            List<Long> petIds = schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
            dto.setPetIds(petIds);
        }
        if (schedule.getEmployees() != null) {
            List<Long> employeeIds = schedule.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList());
            dto.setEmployeeIds(employeeIds);
        }
        return dto;
    }
}
