package com.example.hospitalcasestudy.controller;

import com.example.hospitalcasestudy.dto.patient.DeletePatientReqDto;
import com.example.hospitalcasestudy.exception.RecordNotFoundException;
import com.example.hospitalcasestudy.model.Patient;
import com.example.hospitalcasestudy.model.QPatient;
import com.example.hospitalcasestudy.service.PatientService;
import com.example.hospitalcasestudy.util.PatientDownloadHeader;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/patient")
public class PatientController {

    PatientService patientService;


    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public Page<Patient> getPatients(Pageable pageable){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(QPatient.patient.age.goe(2));
        return patientService.findAll(booleanBuilder, pageable);
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadRecord() throws IOException {
        final Iterable<Patient> patientIterable =  patientService.findAll(new BooleanBuilder());
        final String fileNameWithExt = String.format("patient_record_%s", System.currentTimeMillis());
        final String fileName  = fileNameWithExt+".csv";
        ByteArrayOutputStream out = new ByteArrayOutputStream();


        try (CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT
                .withHeader(PatientDownloadHeader.class))) {
            patientIterable.forEach( (patient -> {
                try {
                    printer.printRecord(patient.getId(), patient.getName(), patient.getAge(), patient.getLastVisitDate());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename="+fileName+"");
        headers.add("Expires", "0");
        headers.add("X-Frame-Options", "sameorigin");

        InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(inputStreamResource);



    }
    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatients(@Valid  @RequestBody DeletePatientReqDto deletePatientReqDto) throws RecordNotFoundException {
        patientService.deletePatients(deletePatientReqDto);
    }
}
