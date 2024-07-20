package br.com.systec.controle.financeiro.administrator.category.api.v1.controller;

import br.com.systec.controle.financeiro.administrator.category.api.v1.converter.CategoryConverter;
import br.com.systec.controle.financeiro.administrator.category.api.v1.dto.CategoryDTO;
import br.com.systec.controle.financeiro.administrator.category.enums.CategoryType;
import br.com.systec.controle.financeiro.administrator.category.filter.FilterCategoryVO;
import br.com.systec.controle.financeiro.administrator.category.model.Category;
import br.com.systec.controle.financeiro.administrator.category.service.CategoryService;
import br.com.systec.controle.financeiro.commons.AbstractController;
import br.com.systec.controle.financeiro.commons.RestPath;
import br.com.systec.controle.financeiro.commons.exception.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * @author JOAO HENRIQUE FREITAS
 */
@RestController
@RequestMapping(RestPath.V1+"/categories")
@Tag(name = "Category", description = "Cadastro de categorias")
@SecurityRequirement(name = "Authorization")
public class CategoryController extends AbstractController {
    private static final String ENDPOINT = RestPath.V1+"/categories";
    @Autowired
    private CategoryService service;

    @Operation(description = "Salva nova categoria")
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO, UriComponentsBuilder uri) {
        Category category = CategoryConverter.toEntity(categoryDTO);
        Category categorySaved = service.save(category);

        return buildSuccessResponseCreated(CategoryConverter.toDTO(categorySaved), uri, ENDPOINT, categorySaved.getId());
    }

    @Operation(description = "Atualiza categoria")
    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO) {
        Category category = CategoryConverter.toEntity(categoryDTO);
        Category categoryUpdate = service.update(category);

        return buildSuccessResponse(CategoryConverter.toDTO(categoryUpdate));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retorna os dados da categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Conta não encontrada", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Long categoryId){
        CategoryDTO categoryDTO = CategoryConverter.toDTO(service.findById(categoryId));

        return buildSuccessResponse(categoryDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retorna a lista de categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as contas cadastradas", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class))
            }),
    })
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<Category> listOfCategory = service.findAll();
        List<CategoryDTO> listOfCategoryDTO = CategoryConverter.toListDTO(listOfCategory);

        return buildSuccessResponse(listOfCategoryDTO);
    }

    @GetMapping("/filter")
    @Operation(description = "Realiza a pesqusia de categoria com filtro e paginada")
    public ResponseEntity<Page<CategoryDTO>> findByFilter(@RequestParam(value = "limit", defaultValue = "30") int limit,
                                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "search", defaultValue = "") String search,
                                                          @RequestParam(value = "category_type", required = false) Long categoryType) {

        FilterCategoryVO filterCategoryVO = new FilterCategoryVO();
        filterCategoryVO.setLimit(limit);
        filterCategoryVO.setPage(page);
        filterCategoryVO.setSearch(search);

        if(categoryType != null) {
            filterCategoryVO.setCategoryType(CategoryType.valueOfCode(categoryType));
        }

        Page<Category> listOfCategory = service.findByFilterAndPageable(filterCategoryVO);
        Page<CategoryDTO> pageOfCategoryDto = CategoryConverter.toPageDTO(listOfCategory);

        return buildSuccessResponse(pageOfCategoryDto);
    }
}
