package br.com.systec.controle.financeiro.receive.api.v1.controller;

import br.com.systec.controle.financeiro.commons.AbstractController;
import br.com.systec.controle.financeiro.commons.RestPath;
import br.com.systec.controle.financeiro.receive.api.v1.converter.ReceiverConverter;
import br.com.systec.controle.financeiro.receive.api.v1.dto.ReceiveInputDTO;
import br.com.systec.controle.financeiro.receive.model.Receive;
import br.com.systec.controle.financeiro.receive.service.ReceiveService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(RestPath.V1+"/receivers")
public class ReceiveController extends AbstractController {
    private static final String ENDPOINT = RestPath.V1+"/receivers";
    @Autowired
    private ReceiveService service;

    @Autowired
    private ReceiverConverter converter;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReceiveInputDTO> save(@RequestBody @Valid ReceiveInputDTO inputDTO, UriComponentsBuilder builder){
        Receive receive = converter.convertToModel(inputDTO);
        Receive receiveSaved = service.save(receive);

        return buildSuccessResponseCreated(converter.convertTOInputDTO(receiveSaved), builder, ENDPOINT, receiveSaved.getId());
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReceiveInputDTO> update(@RequestBody @Valid ReceiveInputDTO inputDTO){
        Receive receive = converter.convertToModel(inputDTO);
        Receive receiveUpdated = service.update(receive);

        return buildSuccessResponse(converter.convertTOInputDTO(receiveUpdated));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathParam("id") Long id){
        service.delete(id);
    }
}
