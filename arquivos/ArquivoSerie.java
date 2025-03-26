package arquivos;


import Entidades.Serie;
import entidade.ParNomeID;
import estruturas.HashExtensivel;

public class ArquivoSerie extends Arquivo<Serie>
{

    Arquivo<Serie> arqSerie;
    HashExtensivel<ParNomeID> indiceIndiretoNome;

    //@Override
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
        //System.out.println("ArquivoSerie super being formed");
        int id = super.create(c);
        //System.out.println("ArquivoSerie super formed");
        //System.out.println("Creating ParNomeID of id: " + id + " to Serie: " + c.getNome());
        //System.out.println("ArquivoSerie IndiceIndireto being formed");
        indiceIndiretoNome.create(new ParNomeID(c.getNome(), id));
        //System.out.println("ArquivoSerie IndiceIndireto formed");
        return id;
    }

    public Serie read(String nome) throws Exception {
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
        Serie c = super.read(id);
        //System.out.println("Deleting ParNomeID of id: " + id + " to Serie: " + c.getNome());
        if(c != null) {
            if(super.delete(id))
                return indiceIndiretoNome.delete(ParNomeID.hash(c.getNome()));
        }
        return false;
    }

    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie SerieVelha = read(novaSerie.getNome());
        if(super.update(novaSerie)) {
            if(novaSerie.getNome().compareTo(SerieVelha.getNome())!=0) {
                indiceIndiretoNome.delete(ParNomeID.hash(SerieVelha.getNome()));
                indiceIndiretoNome.create(new ParNomeID(novaSerie.getNome(), novaSerie.getId()));
            }
            return true;
        }
        return false;
    }
}