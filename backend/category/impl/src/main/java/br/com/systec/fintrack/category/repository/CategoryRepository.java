package br.com.systec.fintrack.category.repository;

import br.com.systec.fintrack.category.model.Category;
import br.com.systec.fintrack.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository extends AbstractRepository<Category, Long> {

}
