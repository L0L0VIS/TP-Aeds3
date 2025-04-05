package entidades;

import estruturas.RegistroArvoreBMais;
import java.io.*;

public class ChaveComposta implements RegistroArvoreBMais<ChaveComposta> {

    private int idSerie;
    private int temporada;
    private int numeroEpisodio;

    public ChaveComposta() {
        this(-1, -1, -1);
    }

    public ChaveComposta(int idSerie, int temporada, int numeroEpisodio) {
        this.idSerie = idSerie;
        this.temporada = temporada;
        this.numeroEpisodio = numeroEpisodio;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public int getTemporada() {
        return temporada;
    }

    public int getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void set(int idSerie, int temporada, int numeroEpisodio) {
        this.idSerie = idSerie;
        this.temporada = temporada;
        this.numeroEpisodio = numeroEpisodio;
    }

    @Override
    public short size() {
        return (short) (Integer.BYTES * 3); // 3 inteiros: 12 bytes
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(idSerie);
        dos.writeInt(temporada);
        dos.writeInt(numeroEpisodio);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        idSerie = dis.readInt();
        temporada = dis.readInt();
        numeroEpisodio = dis.readInt();
    }

    @Override
    public int compareTo(ChaveComposta outra) {
        if (this.idSerie != outra.idSerie)
            return Integer.compare(this.idSerie, outra.idSerie);
        if (this.temporada != outra.temporada)
            return Integer.compare(this.temporada, outra.temporada);
        return Integer.compare(this.numeroEpisodio, outra.numeroEpisodio);
    }

    @Override
    public ChaveComposta clone() {
        return new ChaveComposta(this.idSerie, this.temporada, this.numeroEpisodio);
    }

    @Override
    public String toString() {
        return "(Série " + idSerie + " - Temp " + temporada + " - Ep " + numeroEpisodio + ")";
    }
}
