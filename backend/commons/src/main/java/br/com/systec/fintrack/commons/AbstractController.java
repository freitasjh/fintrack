package br.com.systec.fintrack.commons;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public abstract class AbstractController {

    protected <T> ResponseEntity<T> buildSuccessResponseNoContent() {
        return ResponseEntity.noContent().build();
    }

    protected <T> ResponseEntity<T> buildSuccessResponse(T object) {
        return ResponseEntity.ok(object);
    }

    protected <T> ResponseEntity<List<T>> buildSuccessResponse(List<T> object) {
        return ResponseEntity.ok(object);
    }

    protected <T> ResponseEntity<T> buildSuccessResponseCreated(T object, UriComponentsBuilder uriBuilder, String pathMapping, Long id) {
        URI uri = uriBuilder.path(pathMapping+"/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body(object);
    }

    protected <T> ResponseEntity<T> buildSuccessResponseAccepted() {
        return ResponseEntity.accepted().build();
    }

    protected <T> ResponseEntity<T> buildBadRequest() {
        return ResponseEntity.badRequest().build();
    }
}