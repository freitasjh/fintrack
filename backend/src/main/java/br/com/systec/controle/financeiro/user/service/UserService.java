package br.com.systec.controle.financeiro.user.service;

import br.com.systec.controle.financeiro.user.model.User;
import br.com.systec.controle.financeiro.user.repository.UserRepository;
import br.com.systec.controle.financeiro.user.v1.converter.UserConverter;
import br.com.systec.controle.financeiro.user.v1.dto.UserInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserInputDTO save(final UserInputDTO userInputDTO){
        User user = UserConverter.getInstance().toEntity(userInputDTO);
        return UserConverter.getInstance().toInputDTO(repository.save(user));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserInputDTO update(UserInputDTO inputDTO, Long id){
        User user = UserConverter.getInstance().toEntity(inputDTO);

        return UserConverter.getInstance().toInputDTO(repository.update(user));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public UserInputDTO findById(Long userId){
        User user = repository.findById(userId).orElseThrow(() -> new RuntimeException());

        return UserConverter.getInstance().toInputDTO(user);
    }
}
