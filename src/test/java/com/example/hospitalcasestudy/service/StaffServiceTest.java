package com.example.hospitalcasestudy.service;

import com.example.hospitalcasestudy.dto.staff.CreateStaffReqDto;
import com.example.hospitalcasestudy.model.Staff;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
@Slf4j
public class StaffServiceTest {

    @Autowired
    StaffService staffService;

    @Test
    public void shouldSaveStaff(){
        final CreateStaffReqDto createStaffReqDto = new CreateStaffReqDto();
        final String sampleStaffName  = "Jane Doe";
        createStaffReqDto.setName(sampleStaffName);

        final Staff staff  = staffService.saveOrUpdate(createStaffReqDto);

        Assertions.assertNotNull(staff.getId(),"Staff Id is null");
        Assertions.assertTrue(staff.getName().equals(sampleStaffName));
        Assertions.assertNotNull(staff.getRegistrationDate(), "Staff Registration Date is null");
        Assertions.assertNotNull(staff.getUuid(), "Should UUID is null");

        final boolean staffExistByUuid  = staffService.staffExistByUUid(staff.getUuid());

        Assertions.assertTrue(staffExistByUuid, "Staff with UUID does not exist");

    }
}
