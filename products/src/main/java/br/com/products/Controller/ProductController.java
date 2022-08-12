package br.com.products.Controller;

import br.com.products.Model.Product;
import br.com.products.Repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/products"})
public class ProductController {

    private ProductRepository repository;
    private long id;

    ProductController(ProductRepository ProductRepository) {
        this.repository = ProductRepository;
    }
    // métodos do CRUD aqui

    @GetMapping
    public List<Product> findAll() {
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return repository.save(product);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Product product) {
        return repository.findById(id)
                .map(record -> {
                    record.setName(product.getName());
                    record.setPrice(product.getPrice());
                    record.setDiscription(product.getDiscription());
                    Product updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity <?> delete(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}





