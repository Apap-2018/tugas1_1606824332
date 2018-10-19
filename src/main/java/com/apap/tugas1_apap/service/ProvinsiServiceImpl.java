package com.apap.tugas1_apap.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1_apap.model.ProvinsiModel;
import com.apap.tugas1_apap.repository.ProvinsiDb;

@Service
public class ProvinsiServiceImpl implements ProvinsiService{
	@Autowired
	ProvinsiDb provinsiDb;

	@Override
	public List<ProvinsiModel> getAll() {
		return provinsiDb.findAll();
	}
	
	@Override
	public Optional<ProvinsiModel> getById(Integer idProvinsi) {
		return provinsiDb.findById(idProvinsi);
	}
	
}
