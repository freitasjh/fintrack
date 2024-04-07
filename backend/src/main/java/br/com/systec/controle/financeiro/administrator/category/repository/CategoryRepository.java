package br.com.systec.controle.financeiro.administrator.category.repository;

import br.com.systec.controle.financeiro.administrator.category.model.Category;
import br.com.systec.controle.financeiro.commons.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository extends AbstractRepository<Category, Long> {
}
