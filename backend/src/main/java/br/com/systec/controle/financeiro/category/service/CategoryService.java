package br.com.systec.controle.financeiro.category.service;

import br.com.systec.controle.financeiro.category.model.Category;
import br.com.systec.controle.financeiro.category.repository.CategoryRepository;
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
        Category category = repository.findById(id).orElseThrow();

        return category;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Category> findAll(){
        var listOfCategory = repository.findAll();

        return StreamSupport.stream(listOfCategory.spliterator(), false).toList();
    }
}
