package entidades;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Episodio implements aed3.Registro {

    // Variáveis
    private int id;         // Chave
    private int id_serie;   // Chave da série
    private String nome;    // Nome do episódio
    private short temporada;        // Temporada da série do episódio
    private int data_lancamento; // Data de lancamento (dia, mês e ano)
    private short duracao;   // Duração em minutos do episódio
    
    private final short TAMANHO_TOTAL = 46;    // Tamanho em bytes do registro

    /* Constutores */
    public Episodio() {
        this(-1, -1, "", Short.valueOf("1"), -1, Short.valueOf("1"));
    }

    public Episodio(int id, int id_serie, String nome, short temporada, int data_lancamento, short duracao) {
        this.id = id;
        this.id_serie = id_serie;
        this.nome = nome;
        this.temporada = temporada;
        this.data_lancamento = data_lancamento;
        this.duracao = duracao;
    }
    /* */

    /* Gets */
    public int getId() {
        return this.id;
    }

    public int getId_Serie() {
        return this.id_serie;
    }

    public short getTemporada() {
        return this.temporada;
    }

    public int getData_lancamento() {
        return this.data_lancamento;
    }

    public short getDuracao() {
        return this.duracao;
    }
    /* */

    @Override
    public int hashCode() {
        return this.id;
    }

    public short size() {
        return this.TAMANHO_TOTAL;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeInt(this.id_serie);
        dos.writeUTF(this.nome);
        dos.writeShort(this.temporada);
        dos.writeInt(this.data_lancamento);
        dos.writeShort(this.duracao);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.id_serie = dis.readInt();
        this.nome = dis.readUTF();
        this.temporada = dis.readShort();
        this.data_lancamento = dis.readInt();
        this.duracao = dis.readShort();
    }

    // Print
    public String toString() {
        return "(" + this.id + ";" + this.id_serie + ";" + this.nome + ";" +
        this.temporada + ";" + this.data_lancamento + ";" + this.duracao + ")";
    }

}
