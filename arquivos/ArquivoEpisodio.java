package arquivos;

import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import Entidades.Episodio;
import entidade.ChaveComposta;
import estruturas.ArvoreBMais;

public class ArquivoEpisodio extends Arquivo<Episodio> {

    private ArvoreBMais<ChaveComposta> indiceBMais;

    public ArquivoEpisodio() throws Exception {
        super("episodios", Episodio.class.getConstructor());

        Constructor<ChaveComposta> c = ChaveComposta.class.getConstructor();
        indiceBMais = new ArvoreBMais<>(c, 7, ".\\dados\\episodios\\indiceB+.db");
    }

    @Override
    public int create(Episodio ep) throws Exception {
        int id = super.create(ep);
        ChaveComposta chave = new ChaveComposta(ep.getIdSerie(), ep.getTemporada(), ep.getNumero());
        indiceBMais.create(chave);
        return id;
    }

    public Episodio read(int idSerie, String titulo) throws Exception {
        for (Episodio ep : listarTodos()) {
            if (ep.getIdSerie() == idSerie && ep.getTitulo().equalsIgnoreCase(titulo)) {
                return ep;
            }
        }
        return null;
    }
    
    
    @Override
    public boolean delete(int id) throws Exception {
        Episodio ep = super.read(id);
        if (ep != null) {
            ChaveComposta chave = new ChaveComposta(ep.getIdSerie(), ep.getTemporada(), ep.getNumero());
            indiceBMais.delete(chave);
            return super.delete(id);
        }
        return false;
    }

    public ArrayList<Episodio> listarTodos() throws Exception {
        ArrayList<Episodio> lista = new ArrayList<>();
        int ultimoId = getUltimoID();
        for (int i = 1; i <= ultimoId; i++) {
            Episodio ep = super.read(i);
            if (ep != null) {
                lista.add(ep);
            }
        }
        return lista;
    }

    private int getUltimoID() throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(nomeArquivo, "r")) {
            return raf.readInt();
        }
    }
}
