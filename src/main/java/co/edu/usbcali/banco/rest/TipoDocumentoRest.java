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

import co.edu.usbcali.banco.domain.TipoDocumento;
import co.edu.usbcali.banco.dto.TipoDocumentoDTO;
import co.edu.usbcali.banco.mapper.TipoDocumentoMapper;
import co.edu.usbcali.banco.service.TipoDocumentoService;

@RestController
@RequestMapping("/tipo-documento")
@CrossOrigin("*")
public class TipoDocumentoRest {

	private final static Logger log = LoggerFactory.getLogger(TipoDocumentoRest.class);
	
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	
	@Autowired
	private TipoDocumentoMapper tipoDocumentoMapper;
	
	@GetMapping("/{tdocId}")
	public ResponseEntity<TipoDocumentoDTO> findById(@PathVariable("tdocId") Long tdocId) throws Exception {
		log.info("Entro a Find ById");
		if (tdocId == null || tdocId == 0) {
			return ResponseEntity.badRequest().body(null);
		}
		TipoDocumento tipoDocumento = tipoDocumentoService.findById(tdocId);
		if (tipoDocumento == null) {
			return ResponseEntity.badRequest().body(null);
		}

		TipoDocumentoDTO tipoDocumentoDTO = tipoDocumentoMapper.tipoDocumentoToTipoDocumentoDTO(tipoDocumento);

		return ResponseEntity.ok().body(tipoDocumentoDTO);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<TipoDocumentoDTO>> findAll() throws Exception {
		log.info("Entro a Find ById");
		
		List<TipoDocumentoDTO> listaTipoDocumentoDTO = tipoDocumentoMapper.listTipoDocumentoToListTipoDocumentoDTO(tipoDocumentoService.findAll());

		return ResponseEntity.ok().body(listaTipoDocumentoDTO);
	}
	
	@PostMapping("/")
	public ResponseEntity<String> save(@RequestBody TipoDocumentoDTO tipoDocumentoDTO) throws Exception {
		log.info("Entro a Find save");
		try {
			if (tipoDocumentoDTO == null) {
				return ResponseEntity.badRequest().body("El tipoDocumentoDTO es nulo");
			}
			
			TipoDocumento tipoDocumento = tipoDocumentoMapper.tipoDocumentoDTOToTipoDocumento(tipoDocumentoDTO);
			tipoDocumentoService.save(tipoDocumento);
			return ResponseEntity.ok().body("El tipo de documento se guardo satisfactoriamente");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/")
	public ResponseEntity<String> update(@RequestBody TipoDocumentoDTO tipoDocumentoDTO) throws Exception {
		log.info("Entro a Find update");
		try {
			if (tipoDocumentoDTO == null) {
				return ResponseEntity.badRequest().body("El tipoDocumentoDTO es nulo");
			}
			TipoDocumento entity = tipoDocumentoService.findById(tipoDocumentoDTO.getTdocId());
			if (entity == null) {
				return ResponseEntity.badRequest().body("El tipo de dcoumento no existe");
			}
			TipoDocumento tipoDocumento = tipoDocumentoMapper.tipoDocumentoDTOToTipoDocumento(tipoDocumentoDTO);
			tipoDocumentoService.update(tipoDocumento);
			return ResponseEntity.ok().body("El tipo de documento se modificó satisfactoriamente");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{tdocId}")
	public ResponseEntity<String> delete(@PathVariable("tdocId") Long tdocId) throws Exception {
		log.info("Entro al delete");
		try {
			if (tdocId == null || tdocId == 0) {
				return ResponseEntity.badRequest().body(null);
			}
			TipoDocumento tipoDocumento = tipoDocumentoService.findById(tdocId);
			if (tipoDocumento == null) {
				return ResponseEntity.badRequest().body("El tipo de documento no existe");
			}
			tipoDocumentoService.delete(tipoDocumento);
			return ResponseEntity.ok().body("El tipo de documento se borró");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
