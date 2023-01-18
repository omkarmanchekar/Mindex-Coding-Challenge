package com.mindex.challenge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.mindex.challenge.data.Compensation;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String>{
    List<Compensation> findBy(String id);
    // Compensation findCompensationByEmployeeId(String id);
    Compensation findByEmployeeEmployeeId(String id);
}
