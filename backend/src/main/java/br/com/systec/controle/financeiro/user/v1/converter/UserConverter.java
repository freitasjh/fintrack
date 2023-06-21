package br.com.systec.controle.financeiro.user.v1.converter;

import br.com.systec.controle.financeiro.user.model.User;
import br.com.systec.controle.financeiro.user.v1.dto.UserInputDTO;

public final class UserConverter {

    private static UserConverter instance;

    private UserConverter(){
        // Construtor privado para evitar a instanciação direta da classe
    }

    public static UserConverter getInstance(){
        if(instance == null){
            instance = new UserConverter();
        }

        return instance;
    }

    public UserInputDTO toInputDTO(User user){
        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setId(user.getId());
        userInputDTO.setName(user.getName());
        userInputDTO.setCellphone(user.getCellphone());
        userInputDTO.setPhone(user.getPhone());
        userInputDTO.setEmail(user.getEmail());
        userInputDTO.setFederalId(user.getFederalId());
        userInputDTO.setDateOfBirth(user.getDateOfBirth());
        userInputDTO.setGender(user.getGender());

        return userInputDTO;
    }

    public User toEntity(UserInputDTO userInputDTO){
        User user = new User();

        user.setId(userInputDTO.getId());
        user.setName(userInputDTO.getName());
        user.setCellphone(userInputDTO.getCellphone());
        user.setPhone(userInputDTO.getPhone());
        user.setEmail(userInputDTO.getEmail());
        user.setFederalId(userInputDTO.getFederalId());
        user.setDateOfBirth(userInputDTO.getDateOfBirth());
        user.setGender(userInputDTO.getGender());

        return user;
    }
}
