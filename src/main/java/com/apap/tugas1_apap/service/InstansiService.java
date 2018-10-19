package com.apap.tugas1_apap.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1_apap.model.InstansiModel;
import com.apap.tugas1_apap.model.ProvinsiModel;

public interface InstansiService {
	Optional<InstansiModel> getInstansiById(long id);
	
	List<InstansiModel> getAll();

	List<InstansiModel> getByProvinsi(ProvinsiModel provinsi);
}
