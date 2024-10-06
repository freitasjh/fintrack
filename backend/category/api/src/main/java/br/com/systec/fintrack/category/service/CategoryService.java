package br.com.systec.fintrack.category.service;

import br.com.systec.fintrack.category.filter.FilterCategoryVO;
import br.com.systec.fintrack.category.model.Category;
import br.com.systec.fintrack.commons.exception.BaseException;
import org.springframework.data.domain.Page;

import java.lang.module.FindException;
import java.util.List;

public interface CategoryService {
    Category save(Category category) throws BaseException;
    Category update(Category category) throws BaseException;
    Category findById(Long id) throws FindException;
    Page<Category> findByFilterAndPageable(FilterCategoryVO filterCategory) throws BaseException;
    List<Category> findAll() throws BaseException;
}
