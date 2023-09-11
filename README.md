<h1 align="center">
   Desafio Back-end PicPay
</h1>

## 📃 Sobre
Este projeto foi desenvolvido como parte de um desafio técnico proposto pela empresa PicPay. O desafio tinha como objetivo avaliar minha habilidade em resolver problemas de programação, design de software e boas práticas de desenvolvimento. Optei por resolver o desafio a fim de desenvolver minhas habilidades.
O desafio se encontra no seguinte repositório: [**GitHub**](https://github.com/PicPay/picpay-desafio-backend#materiais-%C3%BAteis)

## 🚀 Tecnologias utilizadas
Este projeto foi desenvolvido com as seguintes tecnologias:

- [**JDK 17**](https://www.oracle.com/java/technologies/downloads/#java17): Java Development Kit 17 - um kit de desenvolvimento para construção de aplicações e componentes usando a linguagem de programação Java;

- [**Intellij Community edition**](https://www.jetbrains.com/idea/download/?section=windows): um ambiente de desenvolvimento;

- [**Postman**](https://www.postman.com/): uma ferramenta que tem como objetivo testar serviços RESTful (Web APIs) por meio do envio de requisições HTTP e da análise do seu retorno;

- [**Git**](https://git-scm.com/downloads): o sistema de controle de versão distribuído de código aberto mais utilizado;
  
- [**H2**](https://www.h2database.com/): um banco de dados em memória para testes de seed na aplicação.

 ## 🔧 Instalação e execução

Para baixar o código-fonte do projeto em sua máquina, primeiramente terá que ter instalado o [**Git**](https://git-scm.com/).

Com o Git instalado, em seu terminal execute o seguinte comando:

```bash
$ git clone git@github.com:luanraffaell/desafio-backend-picpay.git
```

### Backend

- Com o projeto baixado, abra-o em sua IDE.
- Aguarde enquanto o maven baixe as dependências do projeto.
- Recomendo que após o download das dependência você dê um "clean" no projeto através da aba "maven"
- Execute o arquivo **`Picpaysimplificado2Application.java`**.
- A aplicação Spring Boot será executada no endereço: _**`http://localhost:8080/`**_.

---
# REST API
>:warning: **IMPORTANTE**:<br>
Com exceção dos endpoints `/auth/register` e `/auth/authenticate` todos os demais se encontram protegidos, portanto é necessario inserir o token gerado no **Bearer** do cabeçalho `Authorization` antes de enviar a requisição.

## Registro
### Request
`POST /auth/register `

```JSON
{
   "fullName":"Andrew",
    "ssn":"22554545",
    "email":"andrew@test.com",
    "balance":200,
    "password": "asa54s5sa",
    "userType": "COMMON_USER"
}
```
### Response
```JSON
{
   "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAdGVzZS5jb20iLCJleHAiOjE2OTQ0MjI2MjEsImlhdCI6MTY5NDQyMTE4MX0.Pdoezr0yhu36ai1tOnHpZqCj4bLCP_Ao9w63De6RuYs"
}
```

## Autenticação
### Request
`POST /auth/authenticate `

```JSON
{
    "email":"andrew@test.com",
    "password": "asa54s5sa"
}
```
### Response
```JSON
{
   "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmRyZXdAdGVzZS5jb20iLCJleHAiOjE2OTQ0MjI4ODEsImlhdCI6MTY5NDQyMTQ0MX0.O-BzBvk5DlBDe9yj-TBvNUVm4xCQHcSFrK56oYdzuiQ"
}
```
## Criar transação
### Request
`POST /transactions `

```JSON
{
    "amount":10,
    "senderId":1,
    "receptorId":2
}
```
### Response
```JSON
{
    "id": 1,
    "amount": 10,
    "senderId": 1,
    "receptorId": 2,
    "timesTamp": "2023-09-11T05:41:05.7686808"
}
```
## Listar usuarios
### Request
`GET /users `

### Response
```JSON
[
    {
        "fullName": "Andrew",
        "email": "andrew@tese.com",
        "balance": 180.00,
        "password": "$2a$10$jTf5JHISy86k0W7ohuaqkuig3Y0ZA8fXU4sWKx21ARUIi2dcdmERG",
        "userType": "COMMON_USER",
        "ssn": "22554545"
    },
    {
        "fullName": "Mark",
        "email": "mark@test.com",
        "balance": 220.00,
        "password": "$2a$10$jTf5JHISy86k0W7ohuaqkuakdak%%Y0ZA8fXU4sWKx21ARUIi2dc$$ERG",
        "userType": "COMMON_USER",
        "ssn": "5546545151a"
    }
]
```
## Buscar usuario por ID
### Request
`GET /users/1 `

### Response
```JSON
{
    "fullName": "Andrew",
    "email": "andrew@tese.com",
    "balance": 180.00,
    "password": "$2a$10$jTf5JHISy86k0W7ohuaqkuig3Y0ZA8fXU4sWKx21ARUIi2dcdmERG",
    "userType": "COMMON_USER",
    "ssn": "22554545"
}
```

