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

import co.edu.usbcali.banco.domain.Cliente;
import co.edu.usbcali.banco.dto.ClienteDTO;
import co.edu.usbcali.banco.mapper.ClienteMapper;
import co.edu.usbcali.banco.service.ClienteService;

@RestController
@RequestMapping("/cliente")
@CrossOrigin("*")
public class ClienteRest {

	private final static Logger log = LoggerFactory.getLogger(ClienteRest.class);

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteMapper clienteMapper;

	@GetMapping("/{clieId}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable("clieId") Long clieId) throws Exception {
		log.info("Entro a Find ById");
		if (clieId == null || clieId == 0) {
			return ResponseEntity.badRequest().body(null);
		}
		Cliente cliente = clienteService.findById(clieId);
		if (cliente == null) {
			return ResponseEntity.badRequest().body(null);
		}

		ClienteDTO clienteDTO = clienteMapper.clienteToClienteDTO(cliente);

		return ResponseEntity.ok().body(clienteDTO);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<ClienteDTO>> findAll() throws Exception {
		log.info("Entro a Find ById");
		

		List<ClienteDTO> listaClienteDTO = clienteMapper.listClienteToListClienteDTO(clienteService.findAll());

		return ResponseEntity.ok().body(listaClienteDTO);
	}

	@PostMapping("/")
	public ResponseEntity<String> save(@RequestBody ClienteDTO clienteDTO) throws Exception {
		log.info("Entro a Find save");
		try {
			if (clienteDTO == null) {
				return ResponseEntity.badRequest().body("El clienteDTO es nulo");
			}
			
			Cliente cliente = clienteMapper.clienteDTOToCliente(clienteDTO);
			clienteService.save(cliente);
			return ResponseEntity.ok().body("El cliente se guardo satisfactoriamente");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/")
	public ResponseEntity<String> update(@RequestBody ClienteDTO clienteDTO) throws Exception {
		log.info("Entro a Find update");
		try {
			if (clienteDTO == null) {
				return ResponseEntity.badRequest().body("El clienteDTO es nulo");
			}
			Cliente entity = clienteService.findById(clienteDTO.getClieId());
			if (entity == null) {
				return ResponseEntity.badRequest().body("El cliente no existe");
			}
			Cliente cliente = clienteMapper.clienteDTOToCliente(clienteDTO);
			clienteService.update(cliente);
			return ResponseEntity.ok().body("El cliente se modificó satisfactoriamente");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{clieId}")
	public ResponseEntity<String> delete(@PathVariable("clieId") Long clieId) throws Exception {
		log.info("Entro al delete");
		try {
			if (clieId == null || clieId == 0) {
				return ResponseEntity.badRequest().body(null);
			}
			Cliente cliente = clienteService.findById(clieId);
			if (cliente == null) {
				return ResponseEntity.badRequest().body("El cliente no existe");
			}
			clienteService.delete(cliente);
			return ResponseEntity.ok().body("El cliente se borró");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
