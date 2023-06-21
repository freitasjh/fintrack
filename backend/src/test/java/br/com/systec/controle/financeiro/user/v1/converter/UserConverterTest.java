package br.com.systec.controle.financeiro.user.v1.converter;

import br.com.systec.controle.financeiro.fake.UserFake;
import br.com.systec.controle.financeiro.user.model.User;
import br.com.systec.controle.financeiro.user.v1.dto.UserInputDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserConverterTest {


    @Test
    void converterUserToUserInputDTOTest(){
        User user = UserFake.fakeUser();

        UserInputDTO userInputDTO = UserConverter.getInstance().toInputDTO(user);

        Assertions.assertEquals(user.getId(), userInputDTO.getId());
        Assertions.assertEquals(user.getName(), userInputDTO.getName());
        Assertions.assertEquals(user.getCellphone(), userInputDTO.getCellphone());
        Assertions.assertEquals(user.getPhone(), userInputDTO.getPhone());
        Assertions.assertEquals(user.getEmail(), userInputDTO.getEmail());
        Assertions.assertEquals(user.getFederalId(), userInputDTO.getFederalId());
        Assertions.assertEquals(user.getDateOfBirth(), userInputDTO.getDateOfBirth());
        Assertions.assertEquals(user.getGender(), userInputDTO.getGender());
    }

    @Test
    void converterUserInputDTOToUserTest(){
        UserInputDTO userInputDTO = UserFake.fakeUserInputDTO();

        User user = UserConverter.getInstance().toEntity(userInputDTO);

        Assertions.assertEquals(user.getId(), userInputDTO.getId());
        Assertions.assertEquals(user.getName(), userInputDTO.getName());
        Assertions.assertEquals(user.getCellphone(), userInputDTO.getCellphone());
        Assertions.assertEquals(user.getPhone(), userInputDTO.getPhone());
        Assertions.assertEquals(user.getEmail(), userInputDTO.getEmail());
        Assertions.assertEquals(user.getFederalId(), userInputDTO.getFederalId());
        Assertions.assertEquals(user.getDateOfBirth(), userInputDTO.getDateOfBirth());
        Assertions.assertEquals(user.getGender(), userInputDTO.getGender());
    }
}
