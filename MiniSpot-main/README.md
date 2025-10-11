
## 🏗 Estrutura de Classes (POO)

### 1. Entidades Principais

#### Artista
- **Atributos:** id, nome, listaAlbuns
- **Métodos:** exibirAlbuns(), exibirTopFaixas()
- **CRUD:** criar, listar, atualizar nome, excluir
- **POO:** Composição (contém lista de álbuns)

#### Album
- **Atributos:** id, nome, artista, ano, listaFaixas
- **Métodos:** exibirFaixas()
- **CRUD:** criar, listar, atualizar, excluir
- **POO:** Composição (contém lista de faixas); associação com Artista

#### Faixa (Classe Abstrata)
- **Atributos:** id, nome, duração
- **Métodos abstratos:** tocar(), exibirInfo()
- **POO:** Classe abstrata (não é instanciada diretamente)
- **Herança/Polimorfismo:** criar subclasses `FaixaNormal` e `FaixaFavorita` que implementam métodos abstratos de formas diferentes

#### Playlist
- **Atributos:** id, nome, listaFaixas, pública/privada
- **Métodos:** adicionarFaixa(), removerFaixa(), mostrarPlaylist()
- **POO:** Composição (contém lista de Faixas)
- **Interface:** Implementar `IPlaylistManipulavel` para manipulação de faixas

---

### 2. Interfaces

- `IFavoritavel` → método `favoritar()`, implementado por `FaixaFavorita`
- `IPlaylistManipulavel` → métodos `adicionarFaixa()`, `removerFaixa()`, `reordenar()`, implementado por Playlist

---

## 📋 Conceitos de POO aplicados

| Conceito       | Onde aplicar                                      |
|----------------|--------------------------------------------------|
| Abstração      | Faixa como classe abstrata                        |
| Herança        | `FaixaNormal` e `FaixaFavorita` herdam de `Faixa` |
| Polimorfismo   | `tocar()` e `exibirInfo()` nas subclasses       |
| Encapsulamento | Atributos privados com getters/setters           |
| Interface      | `IFavoritavel`, `IPlaylistManipulavel`          |
| Composição     | Playlist contém lista de Faixas; Album contém lista de Faixas; Artista contém lista de Álbuns |

---

## 🛠 Funcionalidades (tarefas a implementar)

### CRUD e Operações
- [ ] CRUD de artistas
- [ ] CRUD de álbuns
- [ ] CRUD de faixas (normal e favorita)
- [ ] CRUD de playlists
- [ ] Adicionar/remover faixas em playlists
- [ ] Favoritar faixas (usando interface IFavoritavel)
- [ ] Simular reprodução de faixas (polimorfismo: `FaixaNormal` vs `FaixaFavorita`)
- [ ] Mostrar top faixas de álbuns ou artistas

### Armazenamento/Persistência
- [ ] Salvar artistas, álbuns, faixas e playlists em arquivos `.txt` (um arquivo por tipo)
- [ ] Carregar dados ao iniciar a aplicação
- [ ] Atualizar arquivos após CRUD ou alterações de playlists

### Extras opcionais
- [ ] Recomendações simples (mais tocadas ou mais adicionadas em playlists)
- [ ] Ordenação de playlists (reordenar faixas)
- [ ] Interface JavaFX simples para visualização

---