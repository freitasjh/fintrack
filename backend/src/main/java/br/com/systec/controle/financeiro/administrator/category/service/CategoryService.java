package br.com.systec.controle.financeiro.administrator.category.service;

import br.com.systec.controle.financeiro.administrator.category.model.Category;
import br.com.systec.controle.financeiro.administrator.category.repository.CategoryRepository;
import br.com.systec.controle.financeiro.commons.exception.ObjectNotFoundException;
import br.com.systec.controle.financeiro.config.I18nTranslate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Category findById(Long id) {
        Category category = repository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException(I18nTranslate.toLocale("category.not.found")));

        return category;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Category> findAll(){
        var listOfCategory = repository.findAll();

        return StreamSupport.stream(listOfCategory.spliterator(), false).toList();
    }
}
