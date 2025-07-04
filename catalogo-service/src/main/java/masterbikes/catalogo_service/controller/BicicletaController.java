package masterbikes.catalogo_service.controller;
import masterbikes.catalogo_service.dto.BicicletaDTO;


import masterbikes.catalogo_service.model.Bicicleta;
import masterbikes.catalogo_service.service.BicicletaService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/catalogo/bicicletas")
public class BicicletaController {

    private final BicicletaService bicicletaService;


    public BicicletaController(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }


    @GetMapping
    public List<Bicicleta> findAll() {
        return bicicletaService.findAll();
    }

    /**
     * Endpoint HATEOAS: Devuelve una bicicleta con links de self, update y delete.
     */
    @GetMapping("/hateoas/{id}")
    public EntityModel<Bicicleta> findByIdHateoas(@PathVariable long id) {
        Bicicleta bici = bicicletaService.findById(id);
        if (bici == null) {
            throw new RuntimeException("Bicicleta no encontrada");
        }
        EntityModel<Bicicleta> resource = EntityModel.of(bici);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BicicletaController.class).findByIdHateoas(id)).withSelfRel());
        // El método delete es void, así que usamos linkTo con el método y el parámetro id
        // El método delete es void, así que construimos el link manualmente
        resource.add(WebMvcLinkBuilder.linkTo(BicicletaController.class).slash(id).withRel("delete"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BicicletaController.class).findAll()).withRel("all"));
        return resource;
    }

    @PostMapping
    public Bicicleta add(@RequestBody BicicletaDTO dto) {
        return bicicletaService.crearDesdeDTO(dto);
    }

    //@PostMapping
    //public Bicicleta add(@RequestBody Bicicleta bicicleta) {
        //return bicicletaService.save(bicicleta);
    //}

    @GetMapping("/{id}")
    public Bicicleta findById(@PathVariable long id) {
        return bicicletaService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        bicicletaService.delete(id);
    }
}