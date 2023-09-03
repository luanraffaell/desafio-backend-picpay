package com.picpaysimplificado2.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(unique = true)
    private String SSN;
    @Column(unique = true)
    private String email;
    private BigDecimal balance;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
