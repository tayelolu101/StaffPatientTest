package com.example.hospitalcasestudy.service;

import com.example.hospitalcasestudy.dto.patient.DeletePatientReqDto;
import com.example.hospitalcasestudy.exception.RecordNotFoundException;
import com.example.hospitalcasestudy.model.Patient;
import com.example.hospitalcasestudy.model.QPatient;
import com.example.hospitalcasestudy.repository.PatientRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;


@Service
public class PatientService {

    PatientRepository patientRepository;
    EntityManager entityManager;

    public PatientService(PatientRepository patientRepository, EntityManager entityManager) {
        this.patientRepository = patientRepository;
        this.entityManager = entityManager;
    }

    public Iterable<Patient> findAll(Predicate predicate){
        return patientRepository.findAll(predicate);
    }

    public Page<Patient> findAll(Predicate predicate, Pageable pageable){
        return patientRepository.findAll(predicate, pageable);
    }

    @Transactional
    public void deletePatients(@NotNull DeletePatientReqDto deletePatientReqDto) throws RecordNotFoundException {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QPatient qPatient = QPatient.patient;
        long recordDeletedCount  =
                jpaQueryFactory
                        .delete(qPatient)
                        .where(qPatient.lastVisitDate.between(
                                deletePatientReqDto.getLastVisitDateFrom(),
                                deletePatientReqDto.getLastVisitDateTo())
                        ).execute();

        if(recordDeletedCount < 1){
            throw new RecordNotFoundException("Not Record Found for deleting withing range specified");
        }
    }
}
