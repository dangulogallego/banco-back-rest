package co.edu.usbcali.banco.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.banco.domain.TipoUsuario;
import co.edu.usbcali.banco.dto.TipoUsuarioDTO;
import co.edu.usbcali.banco.mapper.TipoUsuarioMapper;
import co.edu.usbcali.banco.service.TipoUsuarioService;

@RestController
@RequestMapping("/tipo-usuario")
@CrossOrigin("*")
public class TipoUsuarioRest {
	private final static Logger log = LoggerFactory.getLogger(TipoDocumentoRest.class);
	
	@Autowired
	private TipoUsuarioService tipoUsuarioService;
	
	@Autowired
	private TipoUsuarioMapper tipoUsuarioMapper;
	
	@GetMapping("/{tiusId}")
	public ResponseEntity<TipoUsuarioDTO> findById(@PathVariable("tiusId") Long tiusId) throws Exception {
		log.info("Entro a Find ById");
		if (tiusId == null || tiusId == 0) {
			return ResponseEntity.badRequest().body(null);
		}
		TipoUsuario tipoUsuario = tipoUsuarioService.findById(tiusId);
		if (tipoUsuario == null) {
			return ResponseEntity.badRequest().body(null);
		}

		TipoUsuarioDTO tipoUsuarioDTO = tipoUsuarioMapper.tipoUsuarioToTipoUsuarioDTO(tipoUsuario);

		return ResponseEntity.ok().body(tipoUsuarioDTO);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<TipoUsuarioDTO>> findAll() throws Exception {
		log.info("Entro a Find ById");
		
		List<TipoUsuarioDTO> listaTipoUsuarioDTO = tipoUsuarioMapper.listTipoUsuarioToListTipoUsuarioDTO(tipoUsuarioService.findAll());

		return ResponseEntity.ok().body(listaTipoUsuarioDTO);
	}
	
	@PostMapping("/")
	public ResponseEntity<String> save(@RequestBody TipoUsuarioDTO tipoUsuarioDTO) throws Exception {
		log.info("Entro a Find save");
		try {
			if (tipoUsuarioDTO == null) {
				return ResponseEntity.badRequest().body("El tipoUsuarioDTO es nulo");
			}
			
			TipoUsuario tipoUsuario = tipoUsuarioMapper.tipoUsuarioDTOToTipoUsuario(tipoUsuarioDTO);
			tipoUsuarioService.save(tipoUsuario);
			return ResponseEntity.ok().body("El tipo de usuario se guardo satisfactoriamente");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/")
	public ResponseEntity<String> update(@RequestBody TipoUsuarioDTO tipoUsuarioDTO) throws Exception {
		log.info("Entro a Find update");
		try {
			if (tipoUsuarioDTO == null) {
				return ResponseEntity.badRequest().body("El tipoUsuarioDTO es nulo");
			}
			TipoUsuario entity = tipoUsuarioService.findById(tipoUsuarioDTO.getTiusId());
			if (entity == null) {
				return ResponseEntity.badRequest().body("El tipo de usuario no existe");
			}
			TipoUsuario tipoUsuario = tipoUsuarioMapper.tipoUsuarioDTOToTipoUsuario(tipoUsuarioDTO);
			tipoUsuarioService.update(tipoUsuario);
			return ResponseEntity.ok().body("El tipo de usuario se modificó satisfactoriamente");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{tiusId}")
	public ResponseEntity<String> delete(@PathVariable("tiusId") Long tiusId) throws Exception {
		log.info("Entro al delete");
		try {
			if (tiusId == null || tiusId == 0) {
				return ResponseEntity.badRequest().body(null);
			}
			TipoUsuario tipoUsuario = tipoUsuarioService.findById(tiusId);
			if (tipoUsuario == null) {
				return ResponseEntity.badRequest().body("El tipo de usuario no existe");
			}
			tipoUsuarioService.delete(tipoUsuario);
			return ResponseEntity.ok().body("El tipo de usuario se borró");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
