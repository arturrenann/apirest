package com.stefanini.selecao2019.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.selecao2019.models.Endereco;
import com.stefanini.selecao2019.repository.EnderecoRepository;



@RestController
@RequestMapping("/api")
public class EnderecoResource {

    private final Logger log = LoggerFactory.getLogger(EnderecoResource.class);

    private final EnderecoRepository enderecoRepository;

    public EnderecoResource(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    /**
     * POST  /enderecos : Create a new endereco.
     *
     * @param endereco the endereco to create
     * @return the ResponseEntity with status 201 (Created) and with body the new endereco, or with status 400 (Bad Request) if the endereco has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enderecos")
    public ResponseEntity<Endereco> createEndereco(@RequestBody Endereco endereco) throws URISyntaxException {
        log.debug("REST request to save Endereco : {}", endereco);
        if (endereco.getId() != null) {
      	  return ResponseEntity.notFound().build();
        }
        Endereco result = enderecoRepository.save(endereco);
        return ResponseEntity.created(new URI("/api/enderecos/" + result.getId()))
            .body(result);
    }

    /**
     * PUT  /enderecos : Updates an existing endereco.
     *
     * @param endereco the endereco to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated endereco,
     * or with status 400 (Bad Request) if the endereco is not valid,
     * or with status 500 (Internal Server Error) if the endereco couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enderecos")
    public ResponseEntity<Endereco> updateEndereco(@RequestBody Endereco endereco) throws URISyntaxException {
        log.debug("REST request to update Endereco : {}", endereco);
        if (endereco.getId() == null) {
        	  return ResponseEntity.notFound().build();
        }
        Endereco result = enderecoRepository.save(endereco);
        return ResponseEntity.ok()
            .body(result);
    }

    /**
     * GET  /enderecos : get all the enderecos.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of enderecos in body
     */
    @GetMapping("/enderecos")
    public List<Endereco> getAllEnderecos(@RequestParam(required = false) String filter) {
        if ("pessoa-is-null".equals(filter)) {
            log.debug("REST request to get all Enderecos where pessoa is null");
            return StreamSupport
                .stream(enderecoRepository.findAll().spliterator(), false)
                .filter(endereco -> endereco.getPessoa() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Enderecos");
        return enderecoRepository.findAll();
    }

    /**
     * GET  /enderecos/:id : get the "id" endereco.
     *
     * @param id the id of the endereco to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the endereco, or with status 404 (Not Found)
     */
    @GetMapping("/enderecos/{id}")
    public ResponseEntity<Optional<Endereco>> getEndereco(@PathVariable Long id) {
        log.debug("REST request to get Endereco : {}", id);
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        return ResponseEntity.ok(endereco);
    }

    /**
     * DELETE  /enderecos/:id : delete the "id" endereco.
     *
     * @param id the id of the endereco to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enderecos/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        log.debug("REST request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
