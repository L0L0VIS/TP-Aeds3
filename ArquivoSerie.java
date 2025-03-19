import entidades.Serie;
import aed3.*;

public class ArquivoSerie extends aed3.Arquivo<Serie> 
{

    Arquivo<Serie> arqSeries;
    HashExtensivel<ParNomeID> indiceIndiretoNome;

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
        int id = super.create(c);
        indiceIndiretoNome.create(new ParNomeID(c.getNome(), id));
        return id;
    }

    public Serie read(String nome) throws Exception {
        ParNomeID pni = indiceIndiretoNome.read(ParNomeID.hash(nome));
        if(pni == null)
            return null;
        return read(pni.getId());
    }
    
    public boolean delete(String nome) throws Exception {
        ParNomeID pni = indiceIndiretoNome.read(ParNomeID.hash(nome));
        if(pni != null) 
            if(delete(pni.getId())) 
                return indiceIndiretoNome.delete(ParNomeID.hash(nome));
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
        Serie serieVelha = read(novaSerie.getNome());
        if(super.update(novaSerie)) {
            if(novaSerie.getNome().compareTo(serieVelha.getNome())!=0) {
                indiceIndiretoNome.delete(ParNomeID.hash(serieVelha.getNome()));
                indiceIndiretoNome.create(new ParNomeID(novaSerie.getNome(), novaSerie.getId()));
            }
            return true;
        }
        return false;
    }
}