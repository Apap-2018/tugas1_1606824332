// kalo ga berhasil coba ubah onchangenya dipanggil di js

//var showInstansi = function() {
//	console.log($(provinsi).val());
//}

var showInstansi = function() {
	var idProvinsi = $(provinsi).val();
	$.ajax({
		type : "GET",
		url : "/instansi",
		data : {
			'idProvinsi' : idProvinsi,
		// perlu oper model juga ga ya?
		},
		success : function(output) {
			console.log("masuk")
			console.log(output)
			console.log(output.allInstansi)
			allInstansi = output.allInstansi

			html = '<option value="" disabled selected>Pilih Instansi</option>'
			allInstansi.forEach(function(instansi) {
				html += '<option value=' + instansi.id + '>' + instansi.nama
						+ '</option>'
				console.log(instansi.id)
			})
			$("#instansi").html(html)

			// $("#instansi").removeAttribute("disabled")
			console.log($("#instansi").val())
		}
	});
}

var showJabatan = function() {
	console.log($("#jabatan").val())
}

var cariPegawai = function() {
	var idInstansi = $(instansi).val()
	var idJabatan = $(jabatan).val()
	console.log("idJabatan: "+idJabatan)
	console.log("idInstansi: "+idInstansi)
	$.ajax({
		type : "GET",
		url : "/pegawai/cari-lagi",
		data : {
			'idInstansi' : idInstansi,
			'idJabatan' : idJabatan
		// perlu oper model juga ga ya?
		},
		dataType: 'text',
		success : function(output) {
			
			console.log(output.selectedPegawai)
			console.log(output)
			outputJson = JSON.parse(output)
			selectedPegawai = outputJson.selectedPegawai
			console.log(selectedPegawai)
			html = ''
			selectedPegawai.forEach(function(pegawai) {
				html += 
					'<tr>'+
						'<td>'+pegawai.nip+'</td>'+
						'<td>'+pegawai.nama+'</td>'+
						'<td>'+pegawai.tempatLahir+'</td>'+
						'<td>'+pegawai.tanggalLahir+'</td>'+
						'<td>'+pegawai.tahunMasuk+'</td>'+
						'<td>'+pegawai.namaInstansi+'</td>'+
						'<td>'+pegawai.jabatan[0].nama+'</td>'+
					'</tr>'
				console.log("idInstansi: "+instansi.id)
			})
			$("#body-table").append(html)
		},
		error: function(error){
			console.log(error)
		}
	});
}