package br.com.systec.controle.financeiro.administrator.category.service;

import br.com.systec.controle.financeiro.administrator.category.api.v1.dto.CategoryDTO;
import br.com.systec.controle.financeiro.administrator.category.expceptions.CategoryNotFoundException;
import br.com.systec.controle.financeiro.administrator.category.filter.FilterCategoryVO;
import br.com.systec.controle.financeiro.administrator.category.model.Category;
import br.com.systec.controle.financeiro.administrator.category.repository.CategoryRepository;
import br.com.systec.controle.financeiro.administrator.category.repository.CategoryRepositoryJPA;
import br.com.systec.controle.financeiro.commons.TenantContext;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.commons.filter.FilterSearchVO;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;
    @Autowired
    private CategoryRepositoryJPA repositoryJpa;

    @Transactional(propagation = Propagation.REQUIRED)
    public Category save(Category category) {
        Category categorySave = repository.save(category);

        if (categorySave.getTenantId() == null) {
            categorySave.setTenantId(TenantContext.getTenant());
        }

        return categorySave;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Category update(Category category) {
        Category categoryUpdated = repository.update(category);

        return categoryUpdated;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Category findById(Long id) {
        Category category = repository.findById(id).orElseThrow(CategoryNotFoundException::new);

        return category;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<Category> findByFilterAndPageable(FilterCategoryVO filterCategory) {
        var listOfCategory = repositoryJpa.findAll(filterCategory.getSpecification(), filterCategory.getPageable());

        return listOfCategory;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Category> findAll() {
        var listOfCategory = repository.findAllByTenantId();

        return StreamSupport.stream(listOfCategory.spliterator(), false).toList();
    }
}
