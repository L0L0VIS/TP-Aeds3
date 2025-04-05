package entidades;

import java.io.*;

public class Episodio implements Registro {

    private int id;               // Chave primária do episódio
    private int idSerie;          // Chave estrangeira: ID da série a qual pertence
    private String titulo;        // Título do episódio
    private int temporada;        // Número da temporada
    private int numero;           // Número do episódio
    private String dataLancamento; // Data de lançamento (formato: "YYYY-MM-DD")
    private int duracao;          // Duração do episódio em minutos

    // Construtor padrão
    public Episodio() {
        this(-1, -1, "", -1, -1, "0000-00-00", 0);
    }

    // Construtor completo
    public Episodio(int id, int idSerie, String titulo, int temporada, int numero, 
                    String dataLancamento, int duracao) {
        this.id = id;
        this.idSerie = idSerie;
        this.titulo = titulo;
        this.temporada = temporada;
        this.numero = numero;
        this.dataLancamento = dataLancamento;
        this.duracao = duracao;
    }

    // Construtor sem ID (será gerado pelo sistema)
    public Episodio(int idSerie, String titulo, int temporada, int numero, 
                    String dataLancamento, int duracao) {
        this(-1, idSerie, titulo, temporada, numero, dataLancamento, duracao);
    }

    // Getters
    public int getId() { return id; }
    public int getIdSerie() { return idSerie; }
    public String getTitulo() { return titulo; }
    public int getTemporada() { return temporada; }
    public int getNumero() { return numero; }
    public String getDataLancamento() { return dataLancamento; }
    public int getDuracao() { return duracao; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setIdSerie(int idSerie) { this.idSerie = idSerie; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setTemporada(int temporada) { this.temporada = temporada; }
    public void setNumero(int numero) { this.numero = numero; }
    public void setDataLancamento(String dataLancamento) { this.dataLancamento = dataLancamento; }
    public void setDuracao(int duracao) { this.duracao = duracao; }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.writeInt(idSerie);
        dos.writeUTF(titulo);
        dos.writeInt(temporada);
        dos.writeInt(numero);
        dos.writeUTF(dataLancamento);
        dos.writeInt(duracao);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        id = dis.readInt();
        idSerie = dis.readInt();
        titulo = dis.readUTF();
        temporada = dis.readInt();
        numero = dis.readInt();
        dataLancamento = dis.readUTF();
        duracao = dis.readInt();
    }

    @Override
    public String toString() {
        return "(" + id + "; Série " + idSerie + "; T: " + temporada + " Ep: " + numero +
                "; Título: " + titulo + "; Lançamento: " + dataLancamento + "; Duração: " + duracao + " min)";
    }
}
