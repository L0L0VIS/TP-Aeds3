# Trabalho Prático AEDs3
Trabalho feito com base na template oferecida no repositório [AEDsIII](https://github.com/kutova/AEDsIII), visando a criação de um sistema com a implementação de um CRUD das entidades 'Série' e 'Episódio', com controle preciso do armazenamento em arquivos.

# Participantes
- [Mateus Resende Ottoni](https://github.com/Mateus-Resende-Ottoni)
- [Mateus](https://github.com/L0L0VIS)

# Resumo de funcionalidades
- [X] *"As operações de inclusão, busca, alteração e exclusão de séries estão implementadas e funcionando corretamente?"*
- [ ] *"As operações de inclusão, busca, alteração e exclusão de episódios, por série, estão implementadas e funcionando corretamente?"*
- [ ] *"Essas operações usam a classe CRUD genérica para a construção do arquivo e as classes Tabela Hash Extensível e Árvore B+ como índices diretos e indiretos?"*
- [ ] *"O atributo de ID de série, como chave estrangeira, foi criado na classe de episódios?"*
- [ ] *"Há uma árvore B+ que registre o relacionamento 1:N entre episódios e séries?"*
- [ ] *"Há uma visualização das séries que mostre os episódios por temporada?"*
- [ ] *"A remoção de séries checa se há algum episódio vinculado a ela?"*
- [ ] *"A inclusão da série em um episódio se limita às séries existentes?"*
- [ ] *"O trabalho está funcionando corretamente?"*
- [ ] *"O trabalho está completo?"*
- [X] *"O trabalho é original e não a cópia de um trabalho de outro grupo?"*

# Nosso trabalho
(Que trabalho tivemos, dificuldades, etc.)

# Funcionalidades das classes
## Arquivos

### Arquivo
### Registro

### HashExtensivel
### RegistroHashExtensivel

### ArquivoEpisodio
### ArquivoSerie

### ParNomeID
### ParIDEndereco

## Entidades

### Episodio
### Serie

## Menus

### MenuEpisodios
Acesso via interface às operações CRUD da entidade Episodio.
### MenuSeries
Acesso via interface às operações CRUD da entidade Serie.

## Principal
Acesso via interface aos menus das respectivas classes.
