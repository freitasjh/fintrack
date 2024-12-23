package br.com.systec.fintrack.dashboard.api.v1.mapper;

import br.com.systec.fintrack.dashboard.api.v1.dto.CategoryExpenseDTO;
import br.com.systec.fintrack.financial.transaction.vo.CategoryExpenseVO;

import java.util.List;

public class CategoryExpenseMapper {

    private CategoryExpenseMapper(){}

    public static CategoryExpenseDTO toDTO(CategoryExpenseVO categoryExpanseVO) {
        CategoryExpenseDTO categoryExpenseDTO = new CategoryExpenseDTO();
        categoryExpenseDTO.setDescription(categoryExpanseVO.getCategoryDescription());
        categoryExpenseDTO.setTotalExpense(categoryExpanseVO.getTotalExpense());

        return categoryExpenseDTO;
    }

    public static List<CategoryExpenseDTO> toListDTO(List<CategoryExpenseVO> listOfCategoryExpense) {
        return listOfCategoryExpense.stream().map(CategoryExpenseMapper::toDTO).toList();
    }

}
