package br.com.systec.fintrack.user.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.ObjectNotFoundException;
import br.com.systec.fintrack.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(final User user);
    User update(User user);
    User findById(Long userId);
    List<User> findAllByTenant() throws BaseException;
    User findByLogin(String login) throws BaseException;
}
