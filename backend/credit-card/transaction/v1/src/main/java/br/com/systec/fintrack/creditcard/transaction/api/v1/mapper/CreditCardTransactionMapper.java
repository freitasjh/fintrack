package br.com.systec.fintrack.creditcard.transaction.api.v1.mapper;

import br.com.systec.fintrack.commons.query.PaginatedList;
import br.com.systec.fintrack.creditcard.model.CreditCard;
import br.com.systec.fintrack.creditcard.transaction.api.v1.dto.CreditCardTransactionDTO;
import br.com.systec.fintrack.creditcard.transaction.api.v1.dto.CreditCardTransactionInputDTO;
import br.com.systec.fintrack.creditcard.transaction.model.CreditCardTransaction;
import org.springframework.data.domain.Page;

public class CreditCardTransactionMapper {

    public static CreditCardTransaction toEntity(CreditCardTransactionInputDTO inputDTO) {
        CreditCardTransaction transaction = new CreditCardTransaction();
        transaction.setId(inputDTO.getId());
        transaction.setCreditCard(new CreditCard(inputDTO.getCreditCardId()));
        transaction.setInstallments(inputDTO.getInstallments());
        transaction.setAmount(inputDTO.getAmount());
        transaction.setDescription(inputDTO.getDescription());
        transaction.setDateTransaction(inputDTO.getDateTransaction());

        return transaction;
    }

    public static CreditCardTransactionInputDTO toInputDTO(CreditCardTransaction transaction) {
        CreditCardTransactionInputDTO inputDTO = new CreditCardTransactionInputDTO();
        inputDTO.setId(transaction.getId());
        inputDTO.setAmount(transaction.getAmount());
        inputDTO.setDescription(transaction.getDescription());
        inputDTO.setCreditCardId(transaction.getCreditCard().getId());
        inputDTO.setInstallments(transaction.getInstallments());

        return inputDTO;
    }

    public static CreditCardTransactionDTO toDTO(CreditCardTransaction transaction) {
        CreditCardTransactionDTO dto = new CreditCardTransactionDTO();
        dto.setId(transaction.getId());
        dto.setDescription(transaction.getDescription());
        dto.setDateCreate(transaction.getDateCreated());
        dto.setCreditCardName(transaction.getCreditCard().getName());
        dto.setAmount(transaction.getAmount());
        dto.setInstallments(transaction.getInstallments());

        return dto;
    }

    public static PaginatedList<CreditCardTransactionDTO> toPageDTO(PaginatedList<CreditCardTransaction> pageOfCreditCardTransaction) {
        PaginatedList<CreditCardTransactionDTO> paginatedList = new PaginatedList<>();
        paginatedList.setHasNext(pageOfCreditCardTransaction.isHasNext());
        paginatedList.setTotalResults(pageOfCreditCardTransaction.getTotalResults());
        paginatedList.setPageSizeResult(pageOfCreditCardTransaction.getPageSizeResult());
        paginatedList.addAll(
                pageOfCreditCardTransaction.getResultList().
                stream().map(CreditCardTransactionMapper::toDTO)
                .toList()
        );

        return paginatedList;
    }
}
