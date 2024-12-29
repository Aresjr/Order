# Order

## Execução

Basta rodar os seguintes comandos (deve ter o Docker instalado)

```commandline
docker-compose up -d
```
Isso fará com que suba um container com o banco MongoDB e o deixará rodando na porta padrão (27017)

```commandline
mvn spring-boot:run
```
Deixará a aplicação rodando na porta 8080


## Swagger
http://localhost:8080/swagger-ui/index.html

Nesse caminho é possível testar os endpoints e verificar o formato das requisições e respostas do projeto.

## JMeter

Adicionado arquivo [OrderTests.jmx](OrderTests.jmx), que pode ser utilizado para fazer testes de carga com o JMeter.
Atualmente ele está configurado para 1000 threads com 1000 requisições em cada thread.

O teste demora 2m 20s para concluir e são inseridos 1 milhão de registros caso a validação de duplicação seja ignorada.