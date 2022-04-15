package com.example.hospitalcasestudy.service;

import com.example.hospitalcasestudy.dto.staff.CreateStaffReqDto;
import com.example.hospitalcasestudy.dto.staff.UpdateStaffReqDto;
import com.example.hospitalcasestudy.exception.RecordNotFoundException;
import com.example.hospitalcasestudy.model.QStaff;
import com.example.hospitalcasestudy.model.Staff;
import com.example.hospitalcasestudy.repository.StaffRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Service
public class StaffService {

    StaffRepository staffRepository;
    EntityManager entityManager;

    public StaffService(StaffRepository staffRepository, EntityManager entityManager) {
        this.staffRepository = staffRepository;
        this.entityManager = entityManager;
    }

    public Page<Staff> findAll(Predicate predicate, Pageable pageable){
        return staffRepository.findAll(predicate, pageable);
    }

    public Staff saveOrUpdate(@NonNull  CreateStaffReqDto createStaffReqDto){
        final Staff staff = new Staff();
        staff.setName(createStaffReqDto.getName());
        staff.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
        staff.setUuid(java.util.UUID.randomUUID().toString());
        return staffRepository.save(staff);
    }

    public boolean staffExistByUUid(@NotNull String uuid){
        return staffRepository.exists(QStaff.staff.uuid.eq(uuid));
    }
    @Transactional
    public void updateStaff(@NotNull UpdateStaffReqDto updateStaffReqDto) throws Exception{
        QStaff qStaff  = QStaff.staff;
        final boolean staffExist  = staffRepository.exists(qStaff.id.eq(updateStaffReqDto.getId()));
        if(!staffExist){
            throw new RecordNotFoundException(String.format("Staff with id %d not found", updateStaffReqDto.getId()));
        }
        JPAQueryFactory jpaQueryFactory  = new JPAQueryFactory(entityManager);

        jpaQueryFactory.update(qStaff)
                .set(qStaff.name,updateStaffReqDto.getName().trim())
                .where(qStaff.id.eq(updateStaffReqDto.getId()))
                .execute();

    }
}
