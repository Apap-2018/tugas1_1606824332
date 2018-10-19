package com.apap.tugas1_apap.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1_apap.model.ProvinsiModel;

public interface ProvinsiService {
	List<ProvinsiModel> getAll();

	Optional<ProvinsiModel> getById(Integer idProvinsi);
}
