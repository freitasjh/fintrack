package br.com.systec.fintrack.notification.impl.fake;

import br.com.systec.fintrack.user.model.User;

public class UserFake {

    public static User fake() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setEmail("user@user.com.br");

        return user;
    }

}
