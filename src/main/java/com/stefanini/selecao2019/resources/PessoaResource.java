package com.stefanini.selecao2019.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.selecao2019.models.Pessoa;
import com.stefanini.selecao2019.repository.PessoaRepository;



@RestController
@RequestMapping("/api")
public class PessoaResource {

    private final Logger log = LoggerFactory.getLogger(PessoaResource.class);

    private final PessoaRepository pessoaRepository;

    public PessoaResource(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    /**
     * POST  /pessoas : Create a new pessoa.
     *
     * @param pessoa the pessoa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pessoa, or with status 400 (Bad Request) if the pessoa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pessoas")
    public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa pessoa) throws URISyntaxException {
        log.debug("REST request to save Pessoa : {}", pessoa);
        if (pessoa.getId() != null) {
        	return ResponseEntity.notFound().build();
        }
        Pessoa result = pessoaRepository.save(pessoa);
        return ResponseEntity.created(new URI("/api/pessoas/" + result.getId())).build();
          
    }

    /**
     * PUT  /pessoas : Updates an existing pessoa.
     *
     * @param pessoa the pessoa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pessoa,
     * or with status 400 (Bad Request) if the pessoa is not valid,
     * or with status 500 (Internal Server Error) if the pessoa couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pessoas")
    public ResponseEntity<Pessoa> updatePessoa(@RequestBody Pessoa pessoa) throws URISyntaxException {
        log.debug("REST request to update Pessoa : {}", pessoa);
        if (pessoa.getId() == null) {
        	return ResponseEntity.noContent().build();
        }
        Pessoa result = pessoaRepository.save(pessoa);
        return ResponseEntity.ok()
            .body(result);
    }

    /**
     * GET  /pessoas : get all the pessoas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pessoas in body
     */
    @GetMapping("/pessoas")
    public List<Pessoa> getAllPessoas() {
        log.debug("REST request to get all Pessoas");
        return pessoaRepository.findAll();
    }

    /**
     * GET  /pessoas/:id : get the "id" pessoa.
     *
     * @param id the id of the pessoa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pessoa, or with status 404 (Not Found)
     */
    @GetMapping("/pessoas/{id}")
    public ResponseEntity<Optional<Pessoa>> getPessoa(@PathVariable Long id) {
        log.debug("REST request to get Pessoa : {}", id);
       Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return ResponseEntity.ok(pessoa);
    }

    /**
     * DELETE  /pessoas/:id : delete the "id" pessoa.
     *
     * @param id the id of the pessoa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        log.debug("REST request to delete Pessoa : {}", id);
        pessoaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}