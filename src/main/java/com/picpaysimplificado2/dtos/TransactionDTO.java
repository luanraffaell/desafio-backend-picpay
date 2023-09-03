package com.picpaysimplificado2.dtos;

import com.picpaysimplificado2.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@Builder
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private Long senderId;
    private Long receptorId;
    private LocalDateTime timesTamp;
}
