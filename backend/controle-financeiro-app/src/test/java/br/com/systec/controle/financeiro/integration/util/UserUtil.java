package br.com.systec.controle.financeiro.integration.util;

import br.com.systec.controle.financeiro.administrator.user.model.User;
import br.com.systec.controle.financeiro.administrator.user.v1.dto.UserInputDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserUtil {
    public static String tokenAccess;

    public static UserInputDTO generateUserToSave() throws ParseException {
        UserInputDTO user = new UserInputDTO();
        user.setName("Usuario teste");
        user.setEmail("usuario@gmail.com");
        user.setUsername("usuarioTeste");
        user.setPassword("usuarioTeste123");
        user.setCellphone("479988569");
        user.setUserPrincipalTenant(true);
        user.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse("30/08/1990"));
        user.setGender("M");
        user.setPhone("4734533311");
        user.setFederalId("74839342040");

        return user;
    }



}
