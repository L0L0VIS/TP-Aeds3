# Trabalho Prático AEDs3
Trabalho feito com base nas templates oferecidas no repositório [AEDsIII](https://github.com/kutova/AEDsIII), visando a criação de um sistema com a implementação de um CRUD das entidades *Série* e *Episódio*, com controle preciso do armazenamento em arquivos.

# Participantes
- [Mateus Resende Ottoni](https://github.com/Mateus-Resende-Ottoni)
- [Mateus Ribeiro Fernandes](https://github.com/L0L0VIS)


# Resumo de funcionalidades

## O que o programa faz?
O programa permite a criação das entidades *Série* e *Episódio* (as quais herdam da interface *Registro*) , que contêm os seguintes atributos:

Série:
- ID
- Nome
- Sinopse
- Ano de lançamento
- Streaming

Episódio:
- ID
- ID Serie
- Nome
- Número
- Temporada
- Data de lançamento
- Duração

As entidades criadas são armazenadas em forma de arquivo (um para cada tipo), além de cada arquivo ser acompanhado por arquivos de índice, os quais facilitam a busca, usando a estrutura provida em *HashExtensivel*. Além disso, há a implementação da conexão entre as entidades (pela dependência de *Episódio* de *Série* por conter uma chave estrangeira de tal), que é feita pela estrutura *ArvoreBMais*.

## Nosso trabalho
Nosso trabalho foi de converter e adaptar as template mencionadas anteriormente, considerando as diferentes necessidades das entidades com as quais trabalhamos, como a entidade *Episódio* tendo referência à classe *Série*. Tivemos dificuldade especialmente no que diz respeito a tratar as estruturas *ArvoreBMais* e *HashExtensivel*, a considerar o quão detalhado os seus códigos são, havendo dificuldades especialmente no que diz respeito ao método de deletar.

## Resumo via checklist
- [X] *"As operações de inclusão, busca, alteração e exclusão de séries estão implementadas e funcionando corretamente?"*
- [X] *"As operações de inclusão, busca, alteração e exclusão de episódios, por série, estão implementadas e funcionando corretamente?"*
- [X] *"Essas operações usam a classe CRUD genérica para a construção do arquivo e as classes Tabela Hash Extensível e Árvore B+ como índices diretos e indiretos?"*
- [X] *"O atributo de ID de série, como chave estrangeira, foi criado na classe de episódios?"*
- [X] *"Há uma árvore B+ que registre o relacionamento 1:N entre episódios e séries?"*
- [X] *"Há uma visualização das séries que mostre os episódios por temporada?"*
- [X] *"A remoção de séries checa se há algum episódio vinculado a ela?"*
- [X] *"A inclusão da série em um episódio se limita às séries existentes?"*
- [X] *"O trabalho está funcionando corretamente?"*
- [X] *"O trabalho está completo?"*
- [X] *"O trabalho é original e não a cópia de um trabalho de outro grupo?"*


# Funcionalidades das classes
## *Arquivos*

### Arquivo
Formato base do arquivo para salvar informações das entidades, contendo os métodos de CRUD, e cabeçalho que registra último ID e último espaço com registro deletado.

### ArquivoEpisodio
Estrutura e métodos de arquivo adaptados à entidade *Episódio*, incluindo métodos para recuperar e deletar grupo de episódios com base no ID de série.
### ArquivoSerie
Estrutura e métodos de arquivo adaptados à entidade *Série*.

## *Entidades*

### Registro
Interface base para composição das entidades, contendo os métodos que são necessários de forma universal.

### ChaveComposta
Chave usada para organização da entidade *Episodio* na estrutura *ArvoreBMais*.
### ParIDEndereco
Chave usada como indice direto na estrutura *HashExtensivel*.
### ParNomeID
Chave usada como indice indireto pela entidade *Serie* na estrutura *HashExtensivel*.

### Episodio
Estrutura básica da entidade *Episodio*, contendo os atributos e seus respectivos gets e sets.
### Serie
Estrutura básica da entidade *Serie*, contendo os atributos e seus respectivos gets e sets.

## *Estruturas*

### RegistroArvoreBMais
Interface com os necessário métodos à estrutura *ArvoreBMais*.
### ArvoreBMais
Estrutura *ArvoreBMais*, com métodos de inserção de novo elemento (que possui os necessários meios para aumentar o tamanho atual da estrutura quando o número de elementos ultrapassar o máximo atual), leitura e remoção.

### RegistroHashExtensivel
Interface com os necessário métodos à estrutura *HashExtensivel*.
### Hash Extensivel
Estrutura *HashExtensivel*, com métodos de leitura, inserção, atualização e remoção. Divide-se em Diretório e Cesto, em acordo com aquilo a ser salvo.

## *Menus*

### MenuEpisodios
Acesso e chamada via interface às operações CRUD da entidade Episodio.
### MenuSeries
Acesso e chamada via interface às operações CRUD da entidade Serie.

## Principal
Acesso via interface aos menus das respectivas classes.
