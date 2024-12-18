# Sistema de Voluntariado para Idosos  
Um sistema para conectar idosos e voluntários, promovendo interações sociais e facilitando a organização de atividades.

## ❤️ Objetivo do Projeto  
Este sistema foi criado para combater a **solidão** e o **isolamento social** de idosos, incentivando o voluntariado e promovendo conexões entre gerações.

## 🛠️ Funcionalidades  
- **Cadastro de Voluntários e Idosos**  
- **Login com diferentes perfis (idoso e voluntário)**  
- **Listagem de voluntários e idosos**  
- **Criação de atividades pelos voluntários**  
- **Gerenciamento de atividades pelos idosos** (aceitar, recusar, concluir)

## 🚀 Tecnologias Utilizadas  
- **Java** (Spring Boot)  
- **Banco de Dados**: PostgreSQL  
- **Front-end**: React/TypeScript 
- **Controle de versão**: Git e GitHub  

## 📚 Wiki  
Confira os requisitos, fluxos e detalhes técnicos na nossa [**Wiki**](https://github.com/DannyCMMarques/Back-ProjetoVolunt-rio/wiki).  




## ⚙️ Como Configurar o Projeto  

Siga os passos abaixo para configurar e rodar o projeto localmente.

### ✅ Pré-requisitos  
Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:  
1. [**Java 21LTS+**](https://www.oracle.com/java/technologies/javase-downloads.html)  
   - Certifique-se de que o Java esteja no **PATH** da sua máquina.  
   - Verifique a instalação executando no terminal:  
     ```bash
     java -version
     ```  
2. [**Maven**](https://maven.apache.org/download.cgi)  
   - Após a instalação, verifique se o Maven está no **PATH**:  
     ```bash
     mvn -version
     ```  
3. [**PostgreSQL**](https://www.postgresql.org/download/)  
   - Crie um banco de dados no PostgreSQL com um nome, usuário e senha de sua preferência.  
   - Configure as credenciais no arquivo `application.yml`.  

4. [**Git**](https://git-scm.com/downloads)  
   - Necessário para clonar o repositório.
     ```bash
     mvn -version
     ```  

5. IDE de sua escolha:  
   - Recomendado: [**Visual Studio Code**](https://code.visualstudio.com/).  
   - No Visual Studio Code, configure o suporte ao **Spring Boot** e **Maven**.

---

### 🚀 Passo a Passo para Rodar o Projeto  

1. **Clone o Repositório**  
   Execute o seguinte comando no terminal para clonar o projeto:  
   ```bash
   git clone https://github.com/DannyCMMarques/Back-ProjetoVolunt-rio.git
   ```
   
2. Instale as Dependências
No diretório do projeto, execute o comando para baixar as dependências:
```bash
   mvn clean install
```
3.Execute o Projeto
Para iniciar o servidor Spring Boot, rode:
```bash
mvn spring-boot:run
```
