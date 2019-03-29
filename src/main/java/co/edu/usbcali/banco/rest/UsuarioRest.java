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

import co.edu.usbcali.banco.domain.Usuario;
import co.edu.usbcali.banco.dto.UsuarioDTO;
import co.edu.usbcali.banco.mapper.UsuarioMapper;
import co.edu.usbcali.banco.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioRest {
	
	private final static Logger log = LoggerFactory.getLogger(TipoDocumentoRest.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioMapper usuarioMapper;

	@GetMapping("/{usuUsuario}")
	public ResponseEntity<UsuarioDTO> findById(@PathVariable("usuUsuario") String usuUsuario) throws Exception {
		log.info("Entro a Find ById");
		if (usuUsuario == null || usuUsuario.equals("")) {
			return ResponseEntity.badRequest().body(null);
		}
		Usuario usuario = usuarioService.findById(usuUsuario);
		if (usuario == null) {
			return ResponseEntity.badRequest().body(null);
		}

		UsuarioDTO usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(usuario);

		return ResponseEntity.ok().body(usuarioDTO);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UsuarioDTO>> findAll() throws Exception {
		log.info("Entro a Find ById");
		
		List<UsuarioDTO> listaUsuarioDTO = usuarioMapper.listUsuarioToListUsuarioDTO(usuarioService.findAll());

		return ResponseEntity.ok().body(listaUsuarioDTO);
	}
	
	@PostMapping("/")
	public ResponseEntity<String> save(@RequestBody UsuarioDTO usuarioDTO) throws Exception {
		log.info("Entro a Find save");
		try {
			if (usuarioDTO == null) {
				return ResponseEntity.badRequest().body("El usuarioDTO es nulo");
			}
			
			Usuario usuario = usuarioMapper.usuarioDTOToUsuario(usuarioDTO);
			usuarioService.save(usuario);
			return ResponseEntity.ok().body("El usuario se guardo satisfactoriamente");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/")
	public ResponseEntity<String> update(@RequestBody UsuarioDTO usuarioDTO) throws Exception {
		log.info("Entro a Find update");
		try {
			if (usuarioDTO == null) {
				return ResponseEntity.badRequest().body("El usuarioDTO es nulo");
			}
			Usuario entity = usuarioService.findById(usuarioDTO.getUsuUsuario());
			if (entity == null) {
				return ResponseEntity.badRequest().body("El usuario no existe");
			}
			Usuario usuario = usuarioMapper.usuarioDTOToUsuario(usuarioDTO);
			usuarioService.update(usuario);
			return ResponseEntity.ok().body("El usuario se modificó satisfactoriamente");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{usuUsuario}")
	public ResponseEntity<String> delete(@PathVariable("usuUsuario") String usuUsuario) throws Exception {
		log.info("Entro al delete");
		try {
			if (usuUsuario == null || usuUsuario.equals("")) {
				return ResponseEntity.badRequest().body(null);
			}
			Usuario usuario = usuarioService.findById(usuUsuario);
			if (usuario == null) {
				return ResponseEntity.badRequest().body("El usuario no existe");
			}
			usuarioService.delete(usuario);
			return ResponseEntity.ok().body("El usuario se borró");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
