package masterbikes.sucursal_service.controller;

import masterbikes.sucursal_service.model.Sucursal;
import masterbikes.sucursal_service.service.SucursalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/sucursales")
@Tag(name = "Sucursales", description = "Operaciones sobre las sucursales")
public class SucursalController {

    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las sucursales", description = "Devuelve una lista de todas las sucursales registradas", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida correctamente")
    })
    public List<Sucursal> getAll() {
        return sucursalService.findAll();
    }

    @PostMapping
    @Operation(summary = "Crear sucursal", description = "Crea una nueva sucursal", responses = {
            @ApiResponse(responseCode = "200", description = "Sucursal creada correctamente")
    })
    public Sucursal create(@RequestBody Sucursal sucursal) {
        return sucursalService.save(sucursal);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar sucursal por ID", description = "Devuelve una sucursal por su identificador", responses = {
            @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public Sucursal findById(@PathVariable long id) {
        return sucursalService.findById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal por su identificador", responses = {
            @ApiResponse(responseCode = "204", description = "Sucursal eliminada correctamente")
    })
    public void delete(@PathVariable Long id) {
        sucursalService.delete(id);
    }

    @GetMapping("/hateoas/{id}")
    @Operation(summary = "Obtener sucursal con HATEOAS", description = "Devuelve una sucursal con enlaces HATEOAS", responses = {
            @ApiResponse(responseCode = "200", description = "Sucursal encontrada y devuelta con enlaces HATEOAS"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public EntityModel<Sucursal> findByIdHateoas(@PathVariable long id) {
        Sucursal suc = sucursalService.findById(id);
        if (suc == null) {
            throw new RuntimeException("Sucursal no encontrada");
        }
        EntityModel<Sucursal> resource = EntityModel.of(suc);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SucursalController.class).findByIdHateoas(id)).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(SucursalController.class).slash(id).withRel("delete"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SucursalController.class).getAll()).withRel("all"));
        return resource;
    }

}
