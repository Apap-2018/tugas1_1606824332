package com.apap.tugas1_apap.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "instansi")
public class InstansiModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "deskripsi", nullable = false)
	private String deskripsi;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_provinsi", referencedColumnName = "id")
	@JsonIgnore
	private ProvinsiModel provinsi;
	
	@OneToMany(mappedBy = "instansi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PegawaiModel> listPegawai;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public ProvinsiModel getProvinsi() {
		return provinsi;
	}

	public void setProvinsi(ProvinsiModel provinsi) {
		this.provinsi = provinsi;
	}
	
	
}
