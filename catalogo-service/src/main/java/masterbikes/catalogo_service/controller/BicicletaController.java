package masterbikes.catalogo_service.controller;
import masterbikes.catalogo_service.dto.BicicletaDTO;


import masterbikes.catalogo_service.model.Bicicleta;
import masterbikes.catalogo_service.service.BicicletaService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/catalogo/bicicletas")
@Tag(name = "Bicicletas", description = "Operaciones sobre el catálogo de bicicletas")
public class BicicletaController {

    private final BicicletaService bicicletaService;


    public BicicletaController(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }


    @GetMapping
    @Operation(summary = "Listar todas las bicicletas", description = "Devuelve una lista de todas las bicicletas registradas", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de bicicletas obtenida correctamente")
    })
    public List<Bicicleta> findAll() {
        return bicicletaService.findAll();
    }

    /**
     * Endpoint HATEOAS: Devuelve una bicicleta con links de self, update y delete.
     */
    @GetMapping("/hateoas/{id}")
    @Operation(summary = "Obtener bicicleta con HATEOAS", description = "Devuelve una bicicleta con enlaces HATEOAS", responses = {
            @ApiResponse(responseCode = "200", description = "Bicicleta encontrada y devuelta con enlaces HATEOAS"),
            @ApiResponse(responseCode = "404", description = "Bicicleta no encontrada")
    })
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
    @Operation(summary = "Agregar una bicicleta", description = "Agrega una nueva bicicleta al catálogo", responses = {
            @ApiResponse(responseCode = "200", description = "Bicicleta agregada correctamente")
    })
    public Bicicleta add(@RequestBody BicicletaDTO dto) {
        return bicicletaService.crearDesdeDTO(dto);
    }

    //@PostMapping
    //public Bicicleta add(@RequestBody Bicicleta bicicleta) {
        //return bicicletaService.save(bicicleta);
    //}

    @GetMapping("/{id}")
    @Operation(summary = "Buscar bicicleta por ID", description = "Devuelve una bicicleta por su identificador", responses = {
            @ApiResponse(responseCode = "200", description = "Bicicleta encontrada"),
            @ApiResponse(responseCode = "404", description = "Bicicleta no encontrada")
    })
    public Bicicleta findById(@PathVariable long id) {
        return bicicletaService.findById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar bicicleta", description = "Elimina una bicicleta por su identificador", responses = {
            @ApiResponse(responseCode = "204", description = "Bicicleta eliminada correctamente")
    })
    public void delete(@PathVariable long id) {
        bicicletaService.delete(id);
    }
}