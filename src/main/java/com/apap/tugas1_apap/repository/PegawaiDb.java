package com.apap.tugas1_apap.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1_apap.model.InstansiModel;
import com.apap.tugas1_apap.model.JabatanModel;
import com.apap.tugas1_apap.model.PegawaiModel;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	Optional<PegawaiModel> findByNip(String nip);
	
	List<PegawaiModel> findByInstansiOrderByTanggalLahirDesc(InstansiModel instansi);
	
	boolean existsByNip(String nip);
	
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	
	List<PegawaiModel> findByJabatan(JabatanModel jabatan);
	
	List<PegawaiModel> findByTanggalLahir(Date tanggalLahir);
	
	List<PegawaiModel> findByTanggalLahirAndTahunMasukAndInstansi(Date tanggalLahir, String tahunMasuk, InstansiModel instansi);
	
	List<PegawaiModel> findByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
}

