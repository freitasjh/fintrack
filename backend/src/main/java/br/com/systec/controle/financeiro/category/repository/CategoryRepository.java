package br.com.systec.controle.financeiro.category.repository;

import br.com.systec.controle.financeiro.category.model.Category;
import br.com.systec.controle.financeiro.core.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository extends AbstractRepository<Category, Long> {
}
