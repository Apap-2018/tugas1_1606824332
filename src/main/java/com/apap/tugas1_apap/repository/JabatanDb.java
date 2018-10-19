package com.apap.tugas1_apap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1_apap.model.JabatanModel;
import com.apap.tugas1_apap.model.PegawaiModel;

@Repository
public interface JabatanDb extends JpaRepository<JabatanModel, Long>{

	List<JabatanModel> findByPegawaiOrderByGajiPokokDesc(PegawaiModel pegawai);
}
