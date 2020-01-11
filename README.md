## Informações Importantes

**O Aplicativo ainda não conecta com o Azure**

* O aplicativo .jar para execução está em 'out -> artifacts'
* A maneira mais recomendável de visualizar o projeto e as classes é pela IntelliJ IDE,
a mesma pode ser baixada gratuitamente [aqui](https://www.jetbrains.com/idea/download/#section=windows).
* Todos os dados são salvos localmente no SQL Server.
* Todas as imagens são localmente salvas na pasta *StatueStoreWebApplic/imgAnunciantes/* do aplicativo web 
(em [statue-store-web](https://github.com/EspetacularGus/statue-store-web))

## Manual do Usuário

Nossa plataforma é dividida em duas áreas, uma para o funcionário administrador e outra para o anunciante. Na área do funcionário, este poderá analisar os anúncios cadastrados e aprová-los e ou reprová-los para aparição na loja, assim como acompanhar os dados de cada um. 
Na área do anunciante, este poderá realizar os cadastros de seus anúncios, assim como o pagamento e acompanhamento dos mesmos. 

### Tela Inicial

![statue store menu](https://user-images.githubusercontent.com/59635709/72198299-33b46880-340a-11ea-993f-6bd3486449f6.png)

Nessa tela, o usuário pode clicar em um dos botões para escolher qual tipo de login deseja realizar. Botões: “Sou um Anunciante” e “Funcionário Statue Store”.

### Telas de Login

![login 1](https://user-images.githubusercontent.com/59635709/72198293-331bd200-340a-11ea-80ed-b2592e7bd537.png)
![login 2](https://user-images.githubusercontent.com/59635709/72198294-331bd200-340a-11ea-8fea-924831987466.png)

As telas de login validam os usuários conforme o perfil selecionado (Anunciante ou Funcionário), após o usuário digitar seus dados nos campos CPF e senha, poderá clicar no botão “Entrar” e acessar o menu correspondente.
As telas contam com um botão “Voltar” que pode servir para que os usuários saiam da tela de login de anunciante e troquem para a de funcionário, ou vice-versa.
A tela de login do anunciante conta com um hyperlink “Cadastre-se” logo embaixo do botão Voltar, sublinhada em azul. Esse link levará a tela de cadastro de anunciante.
  
### Cadastro do Anunciante

![cadastro 1](https://user-images.githubusercontent.com/59635709/72198285-31eaa500-340a-11ea-8645-44de30dfb436.png)
![cadastro 2](https://user-images.githubusercontent.com/59635709/72198286-32833b80-340a-11ea-88c9-41ec1b8ca1eb.png)

Após o usuário preencher todos os campos acima com os seus próprios dados, como no exemplo acima, caso não houver nenhuma conta já cadastrada com o e-mail e/ou CPF inserido, a tela de conclusão abaixo deverá aparecer:

![sucesso](https://user-images.githubusercontent.com/59635709/72198300-33b46880-340a-11ea-878c-d368d4ea8967.png)

### Menus Iniciais

![menu anunciante](https://user-images.githubusercontent.com/59635709/72198650-8cd2cb00-340f-11ea-9fd1-27601d22036b.png)
![menu funcionario](https://user-images.githubusercontent.com/59635709/72198651-8cd2cb00-340f-11ea-99b2-a45120f8b548.png)

Os dois menus são bem parecidos, mas se diferem em alguns pontos.
Abaixo a legenda para os retângulos da primeira imagem:

* **Retângulo Azul:** Disponível em ambos os menus, serve para atualizar os cadastros visíveis na tabela.
* **Retângulo Verde:** Disponível em ambos os menus, exibe as informações do usuário atual.
* **Retângulo Roxo:** Disponível em ambos os menus, filtra os dados dos anúncios exibidos na tabela.
* **Retângulo Vermelho:** Disponível apenas no menu do anunciante, abre a tela de cadastro de um novo anúncio.

### Telas de Cadastro de Novo Anúncio

![novo anuncio 1](https://user-images.githubusercontent.com/59635709/72198295-331bd200-340a-11ea-8c60-329a242e3679.png)
![novo anuncio 2](https://user-images.githubusercontent.com/59635709/72198296-331bd200-340a-11ea-941e-110ecb16d29e.png)
![novo anuncio 3](https://user-images.githubusercontent.com/59635709/72198297-33b46880-340a-11ea-9012-a11bd9b38ed0.png)
![novo anuncio 4](https://user-images.githubusercontent.com/59635709/72198298-33b46880-340a-11ea-85db-6e5a76778e55.png)

Após preencher todos os campos acima corretamente, a tela abaixo aparecerá para a confirmação de cadastro do novo anúncio:

![confirmação](https://user-images.githubusercontent.com/59635709/72198287-32833b80-340a-11ea-9839-28c9b1e3dd4d.png)

### Telas de Informação do Anúncio

Para abrir as informações de um anúncio, clique sobre ele duas vezes na tabela, um anúncio aparece na tabela assim:

![info anuncio](https://user-images.githubusercontent.com/59635709/72198292-331bd200-340a-11ea-8819-5f418cd04dec.png)

Após isso, o menu a seguir será aberto e o usuário poderá acompanhar as informações do anúncio, se o usuário for um funcionário administrador do sistema, poderá ativa-lo/desativa-lo.

![info 2](https://user-images.githubusercontent.com/59635709/72198288-32833b80-340a-11ea-8722-f665aa546f65.png)
![info 3](https://user-images.githubusercontent.com/59635709/72198289-32833b80-340a-11ea-9950-699c626d23da.png)
![info 4](https://user-images.githubusercontent.com/59635709/72198291-32833b80-340a-11ea-8c4d-5facd81f009e.png)

