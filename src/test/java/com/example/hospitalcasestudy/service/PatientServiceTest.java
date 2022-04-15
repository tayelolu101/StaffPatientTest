package com.example.hospitalcasestudy.service;

import com.example.hospitalcasestudy.dto.patient.DeletePatientReqDto;
import com.example.hospitalcasestudy.exception.RecordNotFoundException;
import com.example.hospitalcasestudy.model.Patient;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Timestamp;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PatientServiceTest {

    @Autowired
    PatientService patientService;


    @Test
    @Order(2)
    public void shouldDeleteAllPatients() throws RecordNotFoundException {
        shouldFindAllPatients();

        final DeletePatientReqDto deleteRq = new DeletePatientReqDto();
        deleteRq.setLastVisitDateFrom(new Timestamp(1L));
        deleteRq.setLastVisitDateTo(new Timestamp(System.currentTimeMillis()));
        patientService.deletePatients(deleteRq);

        Iterable<Patient> patientIterableAfterDeleting =  patientService.findAll(new BooleanBuilder());

        Assertions.assertTrue(!patientIterableAfterDeleting.iterator().hasNext());


    }
    @Test
    @Order(1)
    public void shouldFindAllPatients(){
       Iterable<Patient> patientIterable =  patientService.findAll(new BooleanBuilder());
        Assertions.assertTrue(patientIterable.iterator().hasNext());
    }

}
