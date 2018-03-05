package rocha.euAtendo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rocha.euAtendo.dto.CadastroEmpresaDTO;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
	@RequestMapping(value="/cadastrar", method=RequestMethod.POST)
	public String home(@RequestBody CadastroEmpresaDTO dto) {
		System.out.println(dto.toString());
		
			return "OK";
	}
}
