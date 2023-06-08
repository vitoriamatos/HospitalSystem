# Sistema Hospitalar

Projeto de um sistemaa hospitalar, criado para a disciplina de Engenharia de Software. Possui 3 módulos: Administradores, Médicos e Pacientes. Cada módulo possui sua respectiva regra de négocio e sistemas baseados na necessidade de cada usuário.


<h4 align="center"> 
	🚧  Status do projeto: 🛠 Concluído..  🚧
</h4>

## Especificações 

Projeto em Java e JavaFX utilizando como Padrão de Projeto o modelo MVC.

O projeto salva os dados em um arquivo de backup. Não foi utilizado nenhum tipo de banco de dados para esta aplicação. 

## Pré Requisitos

  - Instalar o Java SDK e uma IDE Java
 

## Instalação

      # Clone este repositório
      
      $ git clone https://github.com/vitoriamatos/HospitalSystem.git
      
Ao clonar o repositório, abra o diretório do projeto através da IDE de sua preferencia e execute a aplicação.

## Como usar

![workspace](/assets/1.png)

O projeto já vem com um úsuario ADM criado, através dele você pode cadastrar pacientes e médicos, e assim ter acesso a aos outros módulos.

### adm
```
login: "admin@email.com"
senha: "admin"
```

Módulos:

## ADM
* escolher qual usuário gerenciar
![workspace](/assets/4.png)

* criação de usuários e visualização dos usuários cadastrados com base no módulo escolhido (exemplo mostra médicos)

![workspace](/assets/5.png)
![workspace](/assets/6.png)

* Area do administrador:
![workspace](/assets/7.png)

* Area do administrador:
![workspace](/assets/7.png)

* Pronto Socorro: O adm dará inicio ao processo de triagem

![workspace](/assets/9.png)


## PACIENTE


* Marcar exames
![workspace](/assets/11.png)


* Consultar os exames
![workspace](/assets/12.png)

* Ver seus dados e edita-los
![workspace](/assets/8.png)


## Médicos

* Ver solicitações de exames e atende-los
![workspace](/assets/13.png)
![workspace](/assets/14.png)


* Atender o pronto socorro
![workspace](/assets/15.png)
![workspace](/assets/16.png)


