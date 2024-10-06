package br.com.systec.fintrack.user.fake;

import br.com.systec.fintrack.user.model.GenerType;
import br.com.systec.fintrack.user.model.User;

import java.util.Date;

public class UserFake {

    public static User fakeUser(){
        User user = new User();
        user.setId(1l);
        user.setName("John Doe");
        user.setCellphone("123456789");
        user.setPhone("987654321");
        user.setEmail("john.doe@example.com");
        user.setFederalId("1234567890");
        user.setDateOfBirth(new Date());
        user.setGender(GenerType.M);
        user.setUserPrincipalTenant(true);
        user.setUsername("joao.hfreitas");
        user.setPassword("testeConverter");

        return user;
    }

//    public static UserInputDTO fakeUserInputDTO(){
//        UserInputDTO user = new UserInputDTO();
//        user.setId(1l);
//        user.setName("John Doe");
//        user.setCellphone("123456789");
//        user.setPhone("987654321");
//        user.setEmail("john.doe@example.com");
//        user.setFederalId("05405868973");
//        user.setDateOfBirth(new Date());
//        user.setGender("M");
//        user.setUserPrincipalTenant(true);
//        user.setUsername("joao.hfreitas");
//        user.setPassword("testeConverter");
//
//        return user;
//    }
}
