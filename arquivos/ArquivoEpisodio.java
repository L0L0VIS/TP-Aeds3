package arquivos;


import entidades.Episodio;
import entidades.ParNomeID;
import estruturas.HashExtensivel;

public class ArquivoEpisodio extends Arquivo<Episodio>
{

    Arquivo<Episodio> arqEpisodio;
    HashExtensivel<ParNomeID> indiceIndiretoNome;

    //@Override
    public ArquivoEpisodio() throws Exception {
        super("Episodios", Episodio.class.getConstructor());
        indiceIndiretoNome = new HashExtensivel<>(
            ParNomeID.class.getConstructor(), 
            4, 
            ".\\dados\\Episodios\\indiceNome.d.db",   // diretório
            ".\\dados\\Episodios\\indiceNome.c.db"    // cestos 
        );
    }

    @Override
    public int create(Episodio c) throws Exception {
        //System.out.println("ArquivoEpisodio super being formed");
        int id = super.create(c);
        //System.out.println("ArquivoEpisodio super formed");
        //System.out.println("Creating ParNomeID of id: " + id + " to Episodio: " + c.getNome());
        //System.out.println("ArquivoEpisodio IndiceIndireto being formed");
        indiceIndiretoNome.create(new ParNomeID(c.getNome(), id));
        //System.out.println("ArquivoEpisodio IndiceIndireto formed");
        return id;
    }

    public Episodio read(String nome) throws Exception {
        ParNomeID pni = indiceIndiretoNome.read(ParNomeID.hash(nome));
        //System.out.println("PNomeID encontrado: " + pni.getId() + " " + pni.getnome());
        if(pni == null)
            return null;
        return read(pni.getId());
    }
    
    public boolean delete(String nome) throws Exception {
        ParNomeID pni = indiceIndiretoNome.read(ParNomeID.hash(nome));
        //System.out.println("PNomeID encontrado para deletar: " + pni.getId() + " " + pni.getnome());
        if(pni != null) 
            if(delete(pni.getId())) 
                //return indiceIndiretoNome.delete(ParNomeID.hash(nome));
                return true;
                // Antigo return substituído pois era redundante com o outro método delete
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Episodio c = super.read(id);
        //System.out.println("Deleting ParNomeID of id: " + id + " to Episodio: " + c.getNome());
        if(c != null) {
            if(super.delete(id))
                return indiceIndiretoNome.delete(ParNomeID.hash(c.getNome()));
        }
        return false;
    }

    @Override
    public boolean update(Episodio novaEpisodio) throws Exception {
        Episodio EpisodioVelha = read(novaEpisodio.getNome());
        if(super.update(novaEpisodio)) {
            if(novaEpisodio.getNome().compareTo(EpisodioVelha.getNome())!=0) {
                indiceIndiretoNome.delete(ParNomeID.hash(EpisodioVelha.getNome()));
                indiceIndiretoNome.create(new ParNomeID(novaEpisodio.getNome(), novaEpisodio.getId()));
            }
            return true;
        }
        return false;
    }
}