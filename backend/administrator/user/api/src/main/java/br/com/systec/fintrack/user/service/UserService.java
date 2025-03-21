package br.com.systec.fintrack.user.service;

import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.user.model.User;

import java.util.List;

public interface UserService {

    User save(final User user) throws BaseException;

    User update(User user) throws BaseException;

    User findById(Long userId) throws BaseException;

    User findByIdOrGetPrincipal(Long userId) throws BaseException;

    List<User> findAllByTenant() throws BaseException;

    User findByLogin(String login) throws BaseException;
}
