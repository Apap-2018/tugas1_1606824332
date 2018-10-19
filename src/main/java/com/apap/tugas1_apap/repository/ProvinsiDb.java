package com.apap.tugas1_apap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1_apap.model.ProvinsiModel;

@Repository
public interface ProvinsiDb extends JpaRepository<ProvinsiModel, Integer>{
	
}
