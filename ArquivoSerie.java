import entidades.Serie;
import aed3.*;

public class ArquivoSerie extends aed3.Arquivo<Serie>
{

    Arquivo<Serie> arqSerie;
    HashExtensivel<ParNomeID> IdDaSerie;

    //@Override
    public ArquivoSerie() throws Exception {
        super("series", Serie.class.getConstructor());
        IdDaSerie = new HashExtensivel<>(
            ParNomeID.class.getConstructor(), 
            4, 
            ".\\dados\\series\\indiceNome.d.db",   // diretório
            ".\\dados\\series\\indiceNome.c.db"    // cestos 
        );
    }

    @Override
    public int create(Serie c) throws Exception {
        int id = super.create(c);
        IdDaSerie.create(new ParNomeID(c.getNome(), id));
        return id;
    }

    public Serie read(String Nome) throws Exception {
        ParNomeID pci = IdDaSerie.read(ParNomeID.hash(Nome));
        if(pci == null)
            return null;
        return read(pci.getId());
    }
    
    public boolean delete(String Nome) throws Exception {
        ParNomeID pci = IdDaSerie.read(ParNomeID.hash(Nome));
        if(pci != null) 
            if(delete(pci.getId())) 
                return IdDaSerie.delete(ParNomeID.hash(Nome));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Serie c = super.read(id);
        if(c != null) {
            if(super.delete(id))
                return IdDaSerie.delete(ParNomeID.hash(c.getNome()));
        }
        return false;
    }

    @Override
    public boolean update(Serie novaSerie) throws Exception {
        Serie SerieVelha = read(novaSerie.getNome());
        if(super.update(novaSerie)) {
            if(novaSerie.getNome().compareTo(SerieVelha.getNome())!=0) {
                IdDaSerie.delete(ParNomeID.hash(SerieVelha.getNome()));
                IdDaSerie.create(new ParNomeID(novaSerie.getNome(), novaSerie.getId()));
            }
            return true;
        }
        return false;
    }
}