package arquivos;

import java.io.IOException;
import java.io.RandomAccessFile;

import entidades.Serie;
import entidades.ParNomeID;
import estruturas.HashExtensivel;

public class ArquivoSerie extends Arquivo<Serie> {

    private HashExtensivel<ParNomeID> indiceIndiretoNome;

    public ArquivoSerie() throws Exception {
        super("series", Serie.class.getConstructor());
        indiceIndiretoNome = new HashExtensivel<>(
            ParNomeID.class.getConstructor(), 
            4, 
            ".\\dados\\series\\indiceNome.d.db",   // diretório
            ".\\dados\\series\\indiceNome.c.db"    // cestos
        );
    }

    @Override
    public int create(Serie c) throws Exception {
        if (read(c.getNome()) != null) {
            System.out.println("Série já cadastrada: " + c.getNome());
            return -1; // Retorna um valor inválido se a série já existir
        }
        int id = super.create(c);
        indiceIndiretoNome.create(new ParNomeID(c.getNome(), id));
        return id;
    }

    public boolean temSeries() {
        try (RandomAccessFile raf = new RandomAccessFile(nomeArquivo, "r")) {
            return raf.length() > 4; // Deve ter pelo menos 4 bytes para armazenar algum dado
        } catch (IOException e) {
            System.out.println("Erro ao acessar o arquivo de séries: " + e.getMessage());
        }
        return false;
    }

    public Serie read(String nome) throws Exception {
        ParNomeID pni = indiceIndiretoNome.read(ParNomeID.hash(nome));
        if (pni == null) return null;
        return read(pni.getId());
    }
    
    public boolean delete(String nome) throws Exception {
        ParNomeID pni = indiceIndiretoNome.read(ParNomeID.hash(nome));
        if (pni != null && delete(pni.getId())) {
            return indiceIndiretoNome.delete(ParNomeID.hash(nome));
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie c = super.read(id);
        if (c != null) {
            if (super.delete(id)) {
                return indiceIndiretoNome.delete(ParNomeID.hash(c.getNome()));
            }
        }
        return false;
    }

    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie serieAntiga = read(novaSerie.getNome());
        if (serieAntiga == null) {
            System.out.println("Série não encontrada para atualização: " + novaSerie.getNome());
            return false;
        }
        if (super.update(novaSerie)) {
            if (!novaSerie.getNome().equals(serieAntiga.getNome())) {
                indiceIndiretoNome.delete(ParNomeID.hash(serieAntiga.getNome()));
                indiceIndiretoNome.create(new ParNomeID(novaSerie.getNome(), novaSerie.getId()));
            }
            return true;
        }
        return false;
    }
}
