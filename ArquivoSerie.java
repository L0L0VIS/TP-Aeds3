import entidades.Serie;
import aed3.*;

public class ArquivoSerie extends aed3.Arquivo<Serie>
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
        //System.out.println("ArquivoSerie IndiceIndireto being formed");
        indiceIndiretoNome.create(new ParNomeID(c.getNome(), id));
        //System.out.println("ArquivoSerie IndiceIndireto formed");
        return id;
    }

    public Serie read(String Nome) throws Exception {
        ParNomeID pci = indiceIndiretoNome.read(ParNomeID.hash(Nome));
        if(pci == null)
            return null;
        return read(pci.getId());
    }
    
    public boolean delete(String Nome) throws Exception {
        ParNomeID pci = indiceIndiretoNome.read(ParNomeID.hash(Nome));
        if(pci != null) 
            if(delete(pci.getId())) 
                return indiceIndiretoNome.delete(ParNomeID.hash(Nome));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie c = super.read(id);
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