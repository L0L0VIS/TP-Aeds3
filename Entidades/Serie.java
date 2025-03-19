package Entidades;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Serie implements aed3.Registro 
{

    // Variáveis
    private int id;         // Chave
    private String nome;    // Nome da série
    private int ano;        // Ano de lançamento
    private String sinopse; // Sinopse da série
    private String streaming;   // Plataforma de Streaming
    
    private final short TAMANHO_TOTAL = 168;    // Tamanho em bytes do registro

    /* Constutores */
    public Serie() {
        this(-1, "", -1, "", "");
    }

    public Serie(int id, String nome, int ano, String sinopse, String streaming) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.sinopse = sinopse;
        this.streaming = streaming;
    }

    public Serie(String nome, int ano, String sinopse, String streaming) {
        this.nome = nome;
        this.ano = ano;
        this.sinopse = sinopse;
        this.streaming = streaming;
    }
    /* */

    /* Gets */
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        id = this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String novo) {
        nome = novo;
    }

    public int getAno() {
        return this.ano;
    }

    public void setAno(int novo){
        ano = novo;
    }

    public String getSinopse() {
        return this.sinopse;
    }

    public void setSinopse(String nova) {
        sinopse = nova;
    }

    public String getStreaming() {
        return this.streaming;
    }

    public void setStreaming(String nova)
    {
        streaming = nova;
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
        dos.writeUTF(this.nome);
        dos.writeInt(this.ano);
        dos.writeUTF(this.sinopse);
        dos.writeUTF(this.streaming);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.ano = dis.readInt();
        this.sinopse = dis.readUTF();
        this.streaming = dis.readUTF();
    }

    // Print
    public String toString() {
        return "("+this.id + ";" + this.nome + ";" + this.ano + ";" +
        this.sinopse + ";" + this.streaming + ")";
    }

}