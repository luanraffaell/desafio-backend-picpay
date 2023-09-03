package com.picpaysimplificado2.services;

import com.picpaysimplificado2.domain.user.User;
import com.picpaysimplificado2.domain.user.UserType;
import com.picpaysimplificado2.dtos.UserDTO;
import com.picpaysimplificado2.infra.exceptions.EntityNotFoundException;
import com.picpaysimplificado2.infra.exceptions.UserNoBalanceException;
import com.picpaysimplificado2.infra.exceptions.OperationUnauthorizedException;
import com.picpaysimplificado2.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    public static final String MERCHANT_NOT_AUTHORIZED = "Merchant not authorized to carry out the transfer";
    public static final String USER_WITHOUT_BALANCE = "User without enough balance!";
    private UserRepository repository;
    private ModelMapper modelMapper;

    public void validateTransaction(User sender, BigDecimal amount){
        if(sender.getUserType().equals(UserType.SHOPKEEPERS)){
            throw new OperationUnauthorizedException(MERCHANT_NOT_AUTHORIZED);

        }
        if(sender.getBalance().compareTo(amount) <0){
            throw new UserNoBalanceException(USER_WITHOUT_BALANCE);
        }
    }
    public UserDTO createUser(UserDTO data){
        User user = this.modelMapper.map(data,User.class);
        user = this.save(user);
        return this.modelMapper.map(user,UserDTO.class);
    }
    public UserDTO findById(Long id){
        Optional<User> user = this.repository.findById(id);
        if(user.isEmpty()){
            throw new EntityNotFoundException("User not found!");
        }
        return modelMapper.map(user.get(),UserDTO.class);
    }
    public User findEntityById(Long id){
        Optional<User> user = this.repository.findById(id);
        if(user.isEmpty()){
            throw new EntityNotFoundException("User not found!");
        }
        return user.get();
    }
    public User save(User user){
        return this.repository.save(user);
    }
}