package rocha.euAtendo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rocha.euAtendo.dto.CadastroEmpresaDTO;
import rocha.euAtendo.service.EmpresaService;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
	@Autowired
	EmpresaService empresaService;

	@RequestMapping(value="/cadastrar", method=RequestMethod.POST)
	public ResponseEntity<String> home(@RequestBody CadastroEmpresaDTO dto) {
		try {
			empresaService.processoNovaEmpresa(dto);
			return ResponseEntity.status(HttpStatus.ACCEPTED)                 
		            .body("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
		            .body(e.getMessage());
		}

	}
	

	
}
