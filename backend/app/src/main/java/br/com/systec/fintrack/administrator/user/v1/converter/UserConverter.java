package br.com.systec.fintrack.administrator.user.v1.converter;

import br.com.systec.fintrack.administrator.user.model.User;
import br.com.systec.fintrack.administrator.user.util.GenerType;
import br.com.systec.fintrack.administrator.user.v1.dto.UserInputDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        userInputDTO.setGender(user.getGender().getCode());
        userInputDTO.setUserPrincipalTenant(user.isUserPrincipalTenant());


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
        user.setGender(GenerType.valuesOfCode(userInputDTO.getGender()));
        user.setUserPrincipalTenant(userInputDTO.isUserPrincipalTenant());
        user.setUsername(userInputDTO.getUsername());
        if (userInputDTO.getPassword() != null && !userInputDTO.getPassword().isEmpty()){
            user.setPassword(new BCryptPasswordEncoder().encode(userInputDTO.getPassword()));
        }

        return user;
    }
}
