package br.com.systec.fintrack.category.repository;

import br.com.systec.fintrack.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoryJPA extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
}
