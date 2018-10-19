package com.apap.tugas1_apap.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1_apap.model.InstansiModel;
import com.apap.tugas1_apap.model.ProvinsiModel;
import com.apap.tugas1_apap.service.InstansiService;
import com.apap.tugas1_apap.service.ProvinsiService;

@Controller
public class InstansiController {
	
	@Autowired
	ProvinsiService provinsiService;
	
	@Autowired
	InstansiService instansiService;
	
	@RequestMapping(value="/instansi", method = RequestMethod.GET)
	private @ResponseBody Map<String, Object> tambah(@RequestParam(value="idProvinsi") Integer idProvinsi, Model model) {
		ProvinsiModel provinsi = provinsiService.getById(idProvinsi).get();
		
		List<InstansiModel> allInstansi = instansiService.getByProvinsi(provinsi);
		model.addAttribute("allInstansi",allInstansi);
		
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("allInstansi", allInstansi);
		
		return output;
	}
}
