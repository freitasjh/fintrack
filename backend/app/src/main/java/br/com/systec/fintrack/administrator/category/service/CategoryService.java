package br.com.systec.fintrack.administrator.category.service;


import br.com.systec.fintrack.administrator.category.expceptions.CategoryNotFoundException;
import br.com.systec.fintrack.administrator.category.filter.FilterCategoryVO;
import br.com.systec.fintrack.administrator.category.model.Category;
import br.com.systec.fintrack.administrator.category.repository.CategoryRepository;
import br.com.systec.fintrack.administrator.category.repository.CategoryRepositoryJPA;
import br.com.systec.fintrack.commons.TenantContext;
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
        if (category.getTenantId() == null) {
            category.setTenantId(TenantContext.getTenant());
        }

        Category categorySave = repository.save(category);

        return categorySave;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Category update(Category category) {
        if(category.getTenantId() == null) {
            category.setTenantId(TenantContext.getTenant());
        }

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
