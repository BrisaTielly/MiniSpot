package com.example;

import com.example.model.*;
import com.example.repository.*;
import java.time.Duration;
import java.time.Year;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static AlbumRepository albumRepository = new AlbumRepository();
    private static ArtistaRepository artistaRepository = new ArtistaRepository(albumRepository);
    private static FaixaRepository faixaRepository = new FaixaRepository();
    private static PlaylistRepository playlistRepository = new PlaylistRepository();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("🎵 Bem-vindo ao MiniSpot! 🎵\n");
        
        // Popular dados iniciais
        popularDadosIniciais();
        
        // Menu principal
        boolean continuar = true;
        while (continuar) {
            continuar = exibirMenuPrincipal();
        }
        
        System.out.println("\n👋 Obrigado por usar o MiniSpot!");
        scanner.close();
    }

    private static void popularDadosIniciais() {
        // Criar artistas
        Artista artista1 = artistaRepository.adicionar("Fernanda Paula");
        Artista artista2 = artistaRepository.adicionar("João Silva");
        Artista artista3 = artistaRepository.adicionar("Maria Santos");

        // Criar faixas (Música e Podcast)
        Faixa faixa1 = faixaRepository.adicionarMusica("Canção da Manhã", Duration.ofMinutes(3).plusSeconds(45));
        Faixa faixa2 = faixaRepository.adicionarPodcast("Noite Estrelada", Duration.ofMinutes(4).plusSeconds(20));
        Faixa faixa3 = faixaRepository.adicionarMusica("Vento do Sul", Duration.ofMinutes(3).plusSeconds(15));
        Faixa faixa4 = faixaRepository.adicionarPodcast("Melodia do Coração", Duration.ofMinutes(5).plusSeconds(10));

        // Criar álbuns
        Album album1 = albumRepository.adicionar("Amanhecer", artista1, Year.of(2023));
        albumRepository.adicionarFaixaNoAlbum(album1.getId(), faixa1);
        albumRepository.adicionarFaixaNoAlbum(album1.getId(), faixa2);

        Album album2 = albumRepository.adicionar("Horizontes", artista2, Year.of(2024));
        albumRepository.adicionarFaixaNoAlbum(album2.getId(), faixa3);
        albumRepository.adicionarFaixaNoAlbum(album2.getId(), faixa4);

        // Criar playlist
        Playlist playlist1 = playlistRepository.adicionar("Minhas Favoritas", true);
        playlistRepository.adicionarFaixa(playlist1.getId(), faixa2);
        playlistRepository.adicionarFaixa(playlist1.getId(), faixa4);

        System.out.println("✅ Dados iniciais carregados com sucesso!");
        System.out.println("   - " + artistaRepository.contar() + " artistas");
        System.out.println("   - " + albumRepository.contar() + " álbuns");
        System.out.println("   - " + faixaRepository.contar() + " faixas");
        System.out.println("   - " + playlistRepository.contar() + " playlist(s)\n");
    }

    private static boolean exibirMenuPrincipal() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("1. Gerenciar Artistas");
        System.out.println("2. Gerenciar Álbuns");
        System.out.println("3. Gerenciar Faixas");
        System.out.println("4. Gerenciar Playlists");
        System.out.println("0. Sair");
        System.out.println("====================================");
        System.out.print("Escolha uma opção: ");

        int opcao = lerInteiro();
        System.out.println();

        switch (opcao) {
            case 1:
                menuArtistas();
                break;
            case 2:
                menuAlbuns();
                break;
            case 3:
                menuFaixas();
                break;
            case 4:
                menuPlaylists();
                break;
            case 0:
                return false;
            default:
                System.out.println("❌ Opção inválida! Tente novamente.");
        }
        return true;
    }

    // ==================== CRUD ARTISTAS ====================
    private static void menuArtistas() {
        System.out.println("========== GERENCIAR ARTISTAS ==========");
        System.out.println("1. Listar todos os artistas");
        System.out.println("2. Adicionar novo artista");
        System.out.println("3. Atualizar artista");
        System.out.println("4. Excluir artista");
        System.out.println("5. Ver detalhes de um artista");
        System.out.println("0. Voltar ao menu principal");
        System.out.println("========================================");
        System.out.print("Escolha uma opção: ");

        int opcao = lerInteiro();
        System.out.println();

        switch (opcao) {
            case 1:
                listarArtistas();
                break;
            case 2:
                adicionarArtista();
                break;
            case 3:
                atualizarArtista();
                break;
            case 4:
                excluirArtista();
                break;
            case 5:
                verDetalhesArtista();
                break;
            case 0:
                break;
            default:
                System.out.println("❌ Opção inválida!");
        }
    }

    private static void listarArtistas() {
        List<Artista> artistas = artistaRepository.listarTodos();
        if (artistas.isEmpty()) {
            System.out.println("📭 Nenhum artista cadastrado.");
            return;
        }
        System.out.println("📋 LISTA DE ARTISTAS:");
        System.out.println("----------------------------------------");
        for (Artista artista : artistas) {
            System.out.println("ID: " + artista.getId() + " | Nome: " + artista.getNome() + 
                             " | Álbuns: " + (artista.getListaAlbuns() != null ? artista.getListaAlbuns().size() : 0));
        }
        System.out.println("----------------------------------------");
    }

    private static void adicionarArtista() {
        System.out.print("Digite o nome do artista: ");
        String nome = scanner.nextLine();
        
        Artista novoArtista = artistaRepository.adicionar(nome);
        System.out.println("✅ Artista '" + nome + "' adicionado com sucesso! (ID: " + novoArtista.getId() + ")");
    }

    private static void atualizarArtista() {
        listarArtistas();
        if (artistaRepository.contar() == 0) return;

        System.out.print("Digite o ID do artista que deseja atualizar: ");
        long id = lerLong();
        
        Artista artista = artistaRepository.buscarPorId(id);
        if (artista == null) {
            System.out.println("❌ Artista não encontrado!");
            return;
        }

        System.out.println("Nome atual: " + artista.getNome());
        System.out.print("Digite o novo nome: ");
        String novoNome = scanner.nextLine();
        
        artistaRepository.atualizar(id, novoNome);
        System.out.println("✅ Artista atualizado com sucesso!");
    }

    private static void excluirArtista() {
        listarArtistas();
        if (artistaRepository.contar() == 0) return;

        System.out.print("Digite o ID do artista que deseja excluir: ");
        long id = lerLong();
        
        Artista artista = artistaRepository.buscarPorId(id);
        if (artista == null) {
            System.out.println("❌ Artista não encontrado!");
            return;
        }

        albumRepository.excluirPorArtista(artista);
        artistaRepository.excluir(id);
        System.out.println("✅ Artista '" + artista.getNome() + "' excluído com sucesso!");
    }

    private static void verDetalhesArtista() {
        listarArtistas();
        if (artistaRepository.contar() == 0) return;

        System.out.print("Digite o ID do artista: ");
        long id = lerLong();
        
        Artista artista = artistaRepository.buscarPorId(id);
        if (artista == null) {
            System.out.println("❌ Artista não encontrado!");
            return;
        }

        System.out.println("\n👤 DETALHES DO ARTISTA");
        System.out.println("========================================");
        System.out.println("ID: " + artista.getId());
        System.out.println("Nome: " + artista.getNome());
        System.out.println("\n📀 Álbuns:");
        if (artista.getListaAlbuns().isEmpty()) {
            System.out.println("   Nenhum álbum cadastrado");
        } else {
            for (Album album : artista.getListaAlbuns()) {
                System.out.println("   - " + album.getNome() + " (" + album.getAno() + ")");
            }
        }
        System.out.println("========================================");
    }

    // ==================== CRUD ÁLBUNS ====================
    private static void menuAlbuns() {
        System.out.println("========== GERENCIAR ÁLBUNS ==========");
        System.out.println("1. Listar todos os álbuns");
        System.out.println("2. Adicionar novo álbum");
        System.out.println("3. Atualizar álbum");
        System.out.println("4. Excluir álbum");
        System.out.println("5. Ver detalhes de um álbum");
        System.out.println("0. Voltar ao menu principal");
        System.out.println("======================================");
        System.out.print("Escolha uma opção: ");

        int opcao = lerInteiro();
        System.out.println();

        switch (opcao) {
            case 1:
                listarAlbuns();
                break;
            case 2:
                adicionarAlbum();
                break;
            case 3:
                atualizarAlbum();
                break;
            case 4:
                excluirAlbum();
                break;
            case 5:
                verDetalhesAlbum();
                break;
            case 0:
                break;
            default:
                System.out.println("❌ Opção inválida!");
        }
    }

    private static void listarAlbuns() {
        List<Album> albuns = albumRepository.listarTodos();
        if (albuns.isEmpty()) {
            System.out.println("📭 Nenhum álbum cadastrado.");
            return;
        }
        System.out.println("📋 LISTA DE ÁLBUNS:");
        System.out.println("----------------------------------------");
        for (Album album : albuns) {
            String nomeArtista = album.getArtista() != null ? album.getArtista().getNome() : "Sem artista";
            System.out.println("ID: " + album.getId() + " | Nome: " + album.getNome() + 
                             " | Artista: " + nomeArtista + " | Ano: " + album.getAno());
        }
        System.out.println("----------------------------------------");
    }

    private static void adicionarAlbum() {
        System.out.print("Digite o nome do álbum: ");
        String nome = scanner.nextLine();
        
        System.out.print("Digite o ano de lançamento (ex: 2024): ");
        int ano = lerInteiro();
        
        listarArtistas();
        System.out.print("Digite o ID do artista: ");
        long idArtista = lerLong();
        
        Artista artista = artistaRepository.buscarPorId(idArtista);
        Album novoAlbum = albumRepository.adicionar(nome, artista, Year.of(ano));
        System.out.println("✅ Álbum '" + nome + "' adicionado com sucesso! (ID: " + novoAlbum.getId() + ")");
    }

    private static void atualizarAlbum() {
        listarAlbuns();
        if (albumRepository.contar() == 0) return;

        System.out.print("Digite o ID do álbum que deseja atualizar: ");
        long id = lerLong();
        
        Album album = albumRepository.buscarPorId(id);
        if (album == null) {
            System.out.println("❌ Álbum não encontrado!");
            return;
        }

        System.out.println("Nome atual: " + album.getNome());
        System.out.print("Digite o novo nome: ");
        String novoNome = scanner.nextLine();
        albumRepository.atualizarNome(id, novoNome);

        System.out.println("Ano atual: " + album.getAno());
        System.out.print("Digite o novo ano: ");
        int novoAno = lerInteiro();
        albumRepository.atualizarAno(id, Year.of(novoAno));

        System.out.println("✅ Álbum atualizado com sucesso!");
    }

    private static void excluirAlbum() {
        listarAlbuns();
        if (albumRepository.contar() == 0) return;

        System.out.print("Digite o ID do álbum que deseja excluir: ");
        long id = lerLong();
        
        Album album = albumRepository.buscarPorId(id);
        if (album == null) {
            System.out.println("❌ Álbum não encontrado!");
            return;
        }

        albumRepository.excluir(id);
        System.out.println("✅ Álbum '" + album.getNome() + "' excluído com sucesso!");
    }

    private static void verDetalhesAlbum() {
        listarAlbuns();
        if (albumRepository.contar() == 0) return;

        System.out.print("Digite o ID do álbum: ");
        long id = lerLong();
        
        Album album = albumRepository.buscarPorId(id);
        if (album == null) {
            System.out.println("❌ Álbum não encontrado!");
            return;
        }

        System.out.println("\n💿 DETALHES DO ÁLBUM");
        System.out.println("========================================");
        System.out.println("ID: " + album.getId());
        System.out.println("Nome: " + album.getNome());
        System.out.println("Artista: " + album.getArtista().getNome());
        System.out.println("Ano: " + album.getAno());
        System.out.println("\n🎵 Faixas:");
        if (album.getListaFaixas().isEmpty()) {
            System.out.println("   Nenhuma faixa cadastrada");
        } else {
            for (Faixa faixa : album.getListaFaixas()) {
                String tipo = (faixa instanceof Podcast) ? "🎙 Podcast" : "♪ Música";
                System.out.println("   " + tipo + ": " + faixa.getNome() + " - " + formatarDuracao(faixa.getDuracao()));
            }
        }
        System.out.println("========================================");
    }

    // ==================== CRUD FAIXAS ====================
    private static void menuFaixas() {
        System.out.println("========== GERENCIAR FAIXAS ==========");
        System.out.println("1. Listar todas as faixas");
        System.out.println("2. Adicionar nova faixa");
        System.out.println("3. Atualizar faixa");
        System.out.println("4. Excluir faixa");
        System.out.println("5. Ver detalhes de uma faixa");
        System.out.println("0. Voltar ao menu principal");
        System.out.println("======================================");
        System.out.print("Escolha uma opção: ");

        int opcao = lerInteiro();
        System.out.println();

        switch (opcao) {
            case 1:
                listarFaixas();
                break;
            case 2:
                adicionarFaixa();
                break;
            case 3:
                atualizarFaixa();
                break;
            case 4:
                excluirFaixa();
                break;
            case 5:
                verDetalhesFaixa();
                break;
            case 0:
                break;
            default:
                System.out.println("❌ Opção inválida!");
        }
    }

    private static void listarFaixas() {
        List<Faixa> faixas = faixaRepository.listarTodas();
        if (faixas.isEmpty()) {
            System.out.println("📭 Nenhuma faixa cadastrada.");
            return;
        }
        System.out.println("📋 LISTA DE FAIXAS:");
        System.out.println("----------------------------------------");
        for (Faixa faixa : faixas) {
            String tipo = (faixa instanceof Podcast) ? "🎙 Podcast" : "♪ Música";
            System.out.println("ID: " + faixa.getId() + " | Nome: " + faixa.getNome() +
                             " | Duração: " + formatarDuracao(faixa.getDuracao()) + " | Tipo: " + tipo);
        }
        System.out.println("----------------------------------------");
    }

    private static void adicionarFaixa() {
        System.out.print("Digite o nome da faixa: ");
        String nome = scanner.nextLine();
        
        System.out.print("Digite a duração em minutos: ");
        int minutos = lerInteiro();
        
        System.out.print("Digite a duração em segundos: ");
        int segundos = lerInteiro();
        
        System.out.println("Tipo de faixa:");
        System.out.println("1. Música");
        System.out.println("2. Podcast");
        System.out.print("Escolha: ");
        int tipo = lerInteiro();

        Duration duracao = Duration.ofMinutes(minutos).plusSeconds(segundos);
        Faixa novaFaixa;
        
        if (tipo == 2) {
            novaFaixa = faixaRepository.adicionarPodcast(nome, duracao);
        } else {
            novaFaixa = faixaRepository.adicionarMusica(nome, duracao);
        }
        
        System.out.println("✅ Faixa '" + nome + "' adicionada com sucesso! (ID: " + novaFaixa.getId() + ")");
        
        System.out.print("\nDeseja adicionar esta faixa a um álbum? (S/N): ");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("S")) {
            listarAlbuns();
            System.out.print("Digite o ID do álbum: ");
            long idAlbum = lerLong();
            albumRepository.adicionarFaixaNoAlbum(idAlbum, novaFaixa);
            System.out.println("✅ Faixa adicionada ao álbum!");
        }
    }

    private static void atualizarFaixa() {
        listarFaixas();
        if (faixaRepository.contar() == 0) return;

        System.out.print("Digite o ID da faixa que deseja atualizar: ");
        long id = lerLong();
        
        Faixa faixa = faixaRepository.buscarPorId(id);
        if (faixa == null) {
            System.out.println("❌ Faixa não encontrada!");
            return;
        }

        System.out.println("Nome atual: " + faixa.getNome());
        System.out.print("Digite o novo nome: ");
        String novoNome = scanner.nextLine();
        faixaRepository.atualizarNome(id, novoNome);

        System.out.println("Duração atual: " + formatarDuracao(faixa.getDuracao()));
        System.out.print("Digite a nova duração em minutos: ");
        int minutos = lerInteiro();
        System.out.print("Digite a nova duração em segundos: ");
        int segundos = lerInteiro();
        faixaRepository.atualizarDuracao(id, Duration.ofMinutes(minutos).plusSeconds(segundos));

        System.out.println("✅ Faixa atualizada com sucesso!");
    }

    private static void excluirFaixa() {
        listarFaixas();
        if (faixaRepository.contar() == 0) return;

        System.out.print("Digite o ID da faixa que deseja excluir: ");
        long id = lerLong();
        
        Faixa faixa = faixaRepository.buscarPorId(id);
        if (faixa == null) {
            System.out.println("❌ Faixa não encontrada!");
            return;
        }

        albumRepository.removerFaixaDosTodosAlbuns(faixa);
        playlistRepository.removerFaixaDeTodasPlaylists(faixa);
        faixaRepository.excluir(id);
        System.out.println("✅ Faixa '" + faixa.getNome() + "' excluída com sucesso!");
    }

    private static void verDetalhesFaixa() {
        listarFaixas();
        if (faixaRepository.contar() == 0) return;

        System.out.print("Digite o ID da faixa: ");
        long id = lerLong();
        
        Faixa faixa = faixaRepository.buscarPorId(id);
        if (faixa == null) {
            System.out.println("❌ Faixa não encontrada!");
            return;
        }

        System.out.println("\n🎵 DETALHES DA FAIXA");
        System.out.println("========================================");
        System.out.println("ID: " + faixa.getId());
        System.out.println("Nome: " + faixa.getNome());
        System.out.println("Duração: " + formatarDuracao(faixa.getDuracao()));
        System.out.println("Tipo: " + (faixa instanceof Podcast ? "🎙 Podcast" : "♪ Música"));
        System.out.println("========================================");
    }

    // ==================== CRUD PLAYLISTS ====================
    private static void menuPlaylists() {
        System.out.println("========== GERENCIAR PLAYLISTS ==========");
        System.out.println("1. Listar todas as playlists");
        System.out.println("2. Adicionar nova playlist");
        System.out.println("3. Atualizar playlist");
        System.out.println("4. Excluir playlist");
        System.out.println("5. Ver detalhes de uma playlist");
        System.out.println("6. Adicionar faixa a uma playlist");
        System.out.println("7. Remover faixa de uma playlist");
        System.out.println("0. Voltar ao menu principal");
        System.out.println("=========================================");
        System.out.print("Escolha uma opção: ");

        int opcao = lerInteiro();
        System.out.println();

        switch (opcao) {
            case 1:
                listarPlaylists();
                break;
            case 2:
                adicionarPlaylist();
                break;
            case 3:
                atualizarPlaylist();
                break;
            case 4:
                excluirPlaylist();
                break;
            case 5:
                verDetalhesPlaylist();
                break;
            case 6:
                adicionarFaixaNaPlaylist();
                break;
            case 7:
                removerFaixaDaPlaylist();
                break;
            case 0:
                break;
            default:
                System.out.println("❌ Opção inválida!");
        }
    }

    private static void listarPlaylists() {
        List<Playlist> playlists = playlistRepository.listarTodas();
        if (playlists.isEmpty()) {
            System.out.println("📭 Nenhuma playlist cadastrada.");
            return;
        }
        System.out.println("📋 LISTA DE PLAYLISTS:");
        System.out.println("----------------------------------------");
        for (Playlist playlist : playlists) {
            String visibilidade = playlist.getIsPublic() ? "🌍 Pública" : "🔒 Privada";
            int numFaixas = playlist.getListaFaixas() != null ? playlist.getListaFaixas().size() : 0;
            System.out.println("ID: " + playlist.getId() + " | Nome: " + playlist.getNome() + 
                             " | Faixas: " + numFaixas + " | " + visibilidade);
        }
        System.out.println("----------------------------------------");
    }

    private static void adicionarPlaylist() {
        System.out.print("Digite o nome da playlist: ");
        String nome = scanner.nextLine();
        
        System.out.print("A playlist é pública? (S/N): ");
        String resposta = scanner.nextLine();
        boolean isPublica = resposta.equalsIgnoreCase("S");

        Playlist novaPlaylist = playlistRepository.adicionar(nome, isPublica);
        System.out.println("✅ Playlist '" + nome + "' criada com sucesso! (ID: " + novaPlaylist.getId() + ")");
    }

    private static void atualizarPlaylist() {
        listarPlaylists();
        if (playlistRepository.contar() == 0) return;

        System.out.print("Digite o ID da playlist que deseja atualizar: ");
        long id = lerLong();
        
        Playlist playlist = playlistRepository.buscarPorId(id);
        if (playlist == null) {
            System.out.println("❌ Playlist não encontrada!");
            return;
        }

        System.out.println("Nome atual: " + playlist.getNome());
        System.out.print("Digite o novo nome: ");
        String novoNome = scanner.nextLine();
        playlistRepository.atualizarNome(id, novoNome);

        System.out.print("A playlist é pública? (S/N): ");
        String visibilidade = scanner.nextLine();
        playlistRepository.atualizarVisibilidade(id, visibilidade.equalsIgnoreCase("S"));

        System.out.println("✅ Playlist atualizada com sucesso!");
    }

    private static void excluirPlaylist() {
        listarPlaylists();
        if (playlistRepository.contar() == 0) return;

        System.out.print("Digite o ID da playlist que deseja excluir: ");
        long id = lerLong();
        
        Playlist playlist = playlistRepository.buscarPorId(id);
        if (playlist == null) {
            System.out.println("❌ Playlist não encontrada!");
            return;
        }

        playlistRepository.excluir(id);
        System.out.println("✅ Playlist '" + playlist.getNome() + "' excluída com sucesso!");
    }

    private static void verDetalhesPlaylist() {
        listarPlaylists();
        if (playlistRepository.contar() == 0) return;

        System.out.print("Digite o ID da playlist: ");
        long id = lerLong();
        
        Playlist playlist = playlistRepository.buscarPorId(id);
        if (playlist == null) {
            System.out.println("❌ Playlist não encontrada!");
            return;
        }

        System.out.println("\n📃 DETALHES DA PLAYLIST");
        System.out.println("========================================");
        System.out.println("ID: " + playlist.getId());
        System.out.println("Nome: " + playlist.getNome());
        System.out.println("Visibilidade: " + (playlist.getIsPublic() ? "🌍 Pública" : "🔒 Privada"));
        System.out.println("\n🎵 Faixas:");
        if (playlist.getListaFaixas().isEmpty()) {
            System.out.println("   Nenhuma faixa na playlist");
        } else {
            int posicao = 1;
            Duration duracaoTotal = Duration.ZERO;
            for (Faixa faixa : playlist.getListaFaixas()) {
                String tipo = (faixa instanceof Podcast) ? "🎙 Podcast" : "♪ Música";
                System.out.println("   " + posicao + ". " + tipo + ": " + faixa.getNome() +
                                 " - " + formatarDuracao(faixa.getDuracao()));
                duracaoTotal = duracaoTotal.plus(faixa.getDuracao());
                posicao++;
            }
            System.out.println("\nDuração total: " + formatarDuracao(duracaoTotal));
        }
        System.out.println("========================================");
    }

    private static void adicionarFaixaNaPlaylist() {
        listarPlaylists();
        if (playlistRepository.contar() == 0) return;

        System.out.print("Digite o ID da playlist: ");
        long idPlaylist = lerLong();

        listarFaixas();
        if (faixaRepository.contar() == 0) return;

        System.out.print("Digite o ID da faixa que deseja adicionar: ");
        long idFaixa = lerLong();
        
        Faixa faixa = faixaRepository.buscarPorId(idFaixa);
        Playlist playlist = playlistRepository.buscarPorId(idPlaylist);
        
        playlistRepository.adicionarFaixa(idPlaylist, faixa);
        System.out.println("✅ Faixa '" + faixa.getNome() + "' adicionada à playlist '" + playlist.getNome() + "'!");
    }

    private static void removerFaixaDaPlaylist() {
        listarPlaylists();
        if (playlistRepository.contar() == 0) return;

        System.out.print("Digite o ID da playlist: ");
        long idPlaylist = lerLong();
        
        Playlist playlist = playlistRepository.buscarPorId(idPlaylist);
        if (playlist == null) {
            System.out.println("❌ Playlist não encontrada!");
            return;
        }

        if (playlist.getListaFaixas().isEmpty()) {
            System.out.println("📭 Esta playlist não possui faixas.");
            return;
        }

        System.out.println("\n🎵 Faixas na playlist:");
        for (int i = 0; i < playlist.getListaFaixas().size(); i++) {
            Faixa f = playlist.getListaFaixas().get(i);
            System.out.println((i + 1) + ". ID: " + f.getId() + " | " + f.getNome());
        }

        System.out.print("\nDigite o ID da faixa que deseja remover: ");
        long idFaixa = lerLong();
        
        Faixa faixa = faixaRepository.buscarPorId(idFaixa);
        playlistRepository.removerFaixa(idPlaylist, faixa);
        System.out.println("✅ Faixa '" + faixa.getNome() + "' removida da playlist '" + playlist.getNome() + "'!");
    }

    // ==================== MÉTODOS AUXILIARES ====================
    private static int lerInteiro() {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.print("❌ Entrada inválida! Digite um número inteiro: ");
            }
        }
    }

    private static long lerLong() {
        while (true) {
            try {
                long valor = Long.parseLong(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.print("❌ Entrada inválida! Digite um número: ");
            }
        }
    }

    private static String formatarDuracao(Duration duracao) {
        if (duracao == null) return "0:00";
        long minutos = duracao.toMinutes();
        long segundos = duracao.minusMinutes(minutos).getSeconds();
        return String.format("%d:%02d", minutos, segundos);
    }
}
