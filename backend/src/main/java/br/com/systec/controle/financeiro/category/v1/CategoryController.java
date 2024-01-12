package br.com.systec.controle.financeiro.category.v1;

import br.com.systec.controle.financeiro.category.model.Category;
import br.com.systec.controle.financeiro.category.service.CategoryService;
import br.com.systec.controle.financeiro.category.v1.converter.CategoryConverter;
import br.com.systec.controle.financeiro.category.v1.dto.CategoryDTO;
import br.com.systec.controle.financeiro.commons.AbstractController;
import br.com.systec.controle.financeiro.commons.RestPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JOAO HENRIQUE FREITAS
 */
@RestController
@RequestMapping(RestPath.V1+"/categories")
public class CategoryController extends AbstractController {

    @Autowired
    private CategoryService service;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> findById(@PathVariable("id") Long categoryId){
        CategoryDTO categoryDTO = CategoryConverter.getInstance().toDTO(service.findById(categoryId));

        return buildSuccessResponse(categoryDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<Category> listOfCategory = service.findAll();
        List<CategoryDTO> listOfCategoryDTO = CategoryConverter.getInstance().toListDTO(listOfCategory);

        return buildSuccessResponse(listOfCategoryDTO);
    }
}
