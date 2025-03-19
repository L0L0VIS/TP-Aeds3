import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParNomeID implements aed3.RegistroHashExtensivel<ParNomeID> {
    
    private String nome; // chave
    private int id;     // valor
    private final short TAMANHO = 15;  // tamanho em bytes

    public ParNomeID() {
        this.nome = "serie generica";
        this.id = -1;
    }

    public ParNomeID(String nome, int id) throws Exception {
        this.nome = nome;
        this.id = id;
    }

    public String getnome() {
        return nome;
    }

    public int getId() {
        return id;
    }

 
    @Override
    public int hashCode() {
        return hash(this.nome);
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "("+this.nome + ";" + this.id+")";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.write(this.nome.getBytes());
        dos.writeInt(this.id);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        byte[] b = new byte[11];
        dis.read(b);
        this.nome = new String(b);
        this.id = dis.readInt();
    }

    public static int hash(String nome) throws IllegalArgumentException 
    {
        // Converter o nome para um número inteiro longo
        long nomeLong = Long.parseLong(nome);

        // Aplicar uma função de hash usando um número primo grande
        int hashValue = (int) (nomeLong % (int)(1e9 + 7));

        // Retornar um valor positivo
        return Math.abs(hashValue);
    }


}
