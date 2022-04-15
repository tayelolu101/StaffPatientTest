package com.example.hospitalcasestudy.repository;

import com.example.hospitalcasestudy.model.Staff;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface StaffRepository extends PagingAndSortingRepository<Staff, Long>, QuerydslPredicateExecutor<Staff> {

}
