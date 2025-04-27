package br.com.systec.fintrack.budget.planning.v1.controller;

import br.com.systec.fintrack.budget.planning.api.filter.BudgetPlanningPageParam;
import br.com.systec.fintrack.budget.planning.api.model.BudgetPlanning;
import br.com.systec.fintrack.budget.planning.api.service.BudgetPlanningService;
import br.com.systec.fintrack.budget.planning.api.vo.BudgetPlanningExpenseVO;
import br.com.systec.fintrack.budget.planning.api.vo.BudgetPlanningVO;
import br.com.systec.fintrack.budget.planning.v1.converter.BudgetPlanningConverter;
import br.com.systec.fintrack.budget.planning.v1.dto.BudgetPlanningInputDTO;
import br.com.systec.fintrack.budget.planning.v1.dto.BudgetPlanningResponseDTO;
import br.com.systec.fintrack.commons.AbstractController;
import br.com.systec.fintrack.commons.RestPath;
import br.com.systec.fintrack.commons.exception.StandardError;
import br.com.systec.fintrack.commons.query.PaginatedList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1+"/budget-plannings")
@Tag(name = "Budget Planning", description = "Planejamento Orçamentário")
@SecurityRequirement(name = "Authorization")
public class BudgetPlanningControllerV1 extends AbstractController {

    @Autowired
    private BudgetPlanningService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Salva um novo planejamento orçamentário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<Void> createBudgetPlanning(@RequestBody @Valid BudgetPlanningInputDTO budgetPlanning) {
        BudgetPlanningVO budgetPlanningVO = BudgetPlanningConverter.toVO(budgetPlanning);
        service.save(budgetPlanningVO);

        return buildSuccessResponseNoContent();
    }

    @GetMapping
    @Operation(description = "Pesquisa os planejamentos orçamentários por filtro")
    public ResponseEntity<PaginatedList<BudgetPlanningResponseDTO>> findByFilter() {
        PaginatedList<BudgetPlanningVO> paginatedList = service.findByFilter(new BudgetPlanningPageParam(30,0));
        PaginatedList<BudgetPlanningResponseDTO> paginatedListResponseDTO = BudgetPlanningConverter.toListResponseDTO(paginatedList);

        return buildSuccessResponse(paginatedListResponseDTO);
    }

    @GetMapping("/expense-mouth-year")
    public ResponseEntity<BudgetPlanningExpenseVO> findExpenseByMouthAndYear(@RequestParam(name = "mouthAndYear") String mouthAndYear) {
        BudgetPlanningExpenseVO budgetPlanningExpenseVO = service.findBudgetPlanningExpenseByMouthAndYear(mouthAndYear);

        return buildSuccessResponse(budgetPlanningExpenseVO);
    }
}
