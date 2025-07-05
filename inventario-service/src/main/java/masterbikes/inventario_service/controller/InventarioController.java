package masterbikes.inventario_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import masterbikes.inventario_service.model.Inventario;
import masterbikes.inventario_service.repository.InventarioRepository;
import masterbikes.inventario_service.service.InventarioService;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventarios")
@Tag(name = "Inventario", description = "Operaciones sobre el inventario de productos en sucursales")
public class InventarioController {

    public final InventarioService inventarioService;
    private final InventarioRepository inventarioRepository;

    public InventarioController(InventarioService inventarioService, InventarioRepository inventarioRepository) {
        this.inventarioService = inventarioService;
        this.inventarioRepository = inventarioRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todo el inventario", description = "Devuelve una lista de todos los registros de inventario", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de inventario obtenida correctamente")
    })
    public List<Inventario> findAll() {
        return inventarioService.findAll();
    }

    @PostMapping
    @Operation(summary = "Guardar inventario", description = "Guarda un nuevo registro de inventario", responses = {
            @ApiResponse(responseCode = "200", description = "Inventario guardado correctamente")
    })
    public Inventario save(@RequestBody Inventario inventario) {
        return inventarioService.save(inventario);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar inventario por ID", description = "Devuelve un registro de inventario por su identificador", responses = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    public Inventario findById(@PathVariable long id) {
        return inventarioService.findById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inventario", description = "Elimina un registro de inventario por su identificador", responses = {
            @ApiResponse(responseCode = "204", description = "Inventario eliminado correctamente")
    })
    public void delete(@PathVariable long id) {
        inventarioService.deleteById(id);
    }

    @GetMapping(params = { "productoId", "sucursalId" })
    @Operation(summary = "Buscar inventario por producto y sucursal", description = "Devuelve registros de inventario filtrados por producto y sucursal", responses = {
            @ApiResponse(responseCode = "200", description = "Inventario filtrado obtenido correctamente")
    })
    public List<Inventario> findByProductoYSucursal(
            @RequestParam Long productoId,
            @RequestParam Long sucursalId
    ) {
        return inventarioRepository.findByProductoIdAndSucursalId(productoId, sucursalId);
    }

    @GetMapping("/hateoas/{id}")
    @Operation(summary = "Obtener inventario con HATEOAS", description = "Devuelve un registro de inventario con enlaces HATEOAS", responses = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado y devuelto con enlaces HATEOAS"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    public EntityModel<Inventario> findByIdHateoas(@PathVariable long id) {
        Inventario inv = inventarioService.findById(id);
        if (inv == null) {
            throw new RuntimeException("Inventario no encontrado");
        }
        EntityModel<Inventario> resource = EntityModel.of(inv);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventarioController.class).findByIdHateoas(id)).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(InventarioController.class).slash(id).withRel("delete"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventarioController.class).findAll()).withRel("all"));
        return resource;
    }

}
