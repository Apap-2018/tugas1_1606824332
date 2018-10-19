package com.apap.tugas1_apap.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1_apap.model.InstansiModel;
import com.apap.tugas1_apap.model.ProvinsiModel;
import com.apap.tugas1_apap.repository.InstansiDb;
import com.apap.tugas1_apap.repository.JabatanDb;

@Service
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDb instansiDb;
	
	@Override
	public Optional<InstansiModel> getInstansiById(long id) {
		return instansiDb.findById(id);
	}

	@Override
	public List<InstansiModel> getAll() {
		return instansiDb.findAll();
	}
	
	@Override 
	public List<InstansiModel> getByProvinsi(ProvinsiModel provinsi){
		return instansiDb.findByProvinsi(provinsi);
	}
}
