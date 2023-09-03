package com.picpaysimplificado2.services;

import com.picpaysimplificado2.domain.transactions.Transaction;
import com.picpaysimplificado2.domain.user.User;
import com.picpaysimplificado2.dtos.TransactionDTO;
import com.picpaysimplificado2.infra.exceptions.OperationUnauthorizedException;
import com.picpaysimplificado2.repositories.TransactionRepository;
import com.picpaysimplificado2.services.factory.NotificationSimpleFactory;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@Service
public class TransactionService {
    private TransactionRepository repository;
    private UserService userService;
    private RestTemplate restTemplate;
    private ModelMapper modelMapper;
    private NotificationSimpleFactory notificationSimpleFactory;

//    @Value("${pp.mock.validation}")
//    private String picPayLinkValid;

    public TransactionDTO createTransaction(TransactionDTO transaction){
        if(!authorizeTransaction()){
            throw new OperationUnauthorizedException("Operation unauthorized!");
        }

        User sender = userService.findEntityById(transaction.getSenderId());
        User receptor = userService.findEntityById(transaction.getReceptorId());
        userService.validateTransaction(sender,transaction.getAmount());

        Transaction newTransaction = new Transaction();
        newTransaction.setSender(sender);
        newTransaction.setReceptor(receptor);
        newTransaction.setAmount(transaction.getAmount());
        newTransaction.setTimesTamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.getAmount()));
        receptor.setBalance(receptor.getBalance().add(transaction.getAmount()));
        newTransaction = this.repository.save(newTransaction);
        this.userService.save(sender);
        this.userService.save(receptor);
        notificationSimpleFactory.getNotification("email").sendNotification(sender,"Successful transfer");
        notificationSimpleFactory.getNotification("email").sendNotification(receptor,"Transfer received");

        return convertToDTO(newTransaction);

    }
    private TransactionDTO convertToDTO(Transaction transaction){
        TransactionDTO dto = TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .senderId(transaction.getSender().getId())
                .receptorId(transaction.getReceptor().getId())
                .timesTamp(transaction.getTimesTamp())
                .build();
        return dto;
    }

    private boolean authorizeTransaction(){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
        if(authorizationResponse.getStatusCode().equals(HttpStatus.OK)){
            String message = (String) authorizationResponse.getBody().get("message");
            return message.equalsIgnoreCase("Autorizado");
        }
        return false;
    }
}
