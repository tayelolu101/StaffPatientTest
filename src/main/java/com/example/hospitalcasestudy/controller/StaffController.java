package com.example.hospitalcasestudy.controller;

import com.example.hospitalcasestudy.dto.staff.CreateStaffReqDto;
import com.example.hospitalcasestudy.dto.staff.UpdateStaffReqDto;
import com.example.hospitalcasestudy.model.Staff;
import com.example.hospitalcasestudy.service.StaffService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/staff")
public class StaffController {

    StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Staff createStaff(@Valid @RequestBody CreateStaffReqDto createStaffReqDto){
        return staffService.saveOrUpdate(createStaffReqDto);

    }
    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStaff(@Valid @RequestBody UpdateStaffReqDto updateStaffReqDto) throws Exception{
         staffService.updateStaff(updateStaffReqDto);

    }
    @GetMapping("")
    public Page<Staff> getStaffs(Pageable pageable){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        return staffService.findAll(booleanBuilder, pageable);
    }
}
