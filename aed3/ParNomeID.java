package aed3;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParNomeID implements aed3.RegistroHashExtensivel<ParNomeID> {
      
    private String nome;   // chave
    private int id;       // valor
    private final short TAMANHO = 16;  // tamanho em bytes

    public ParNomeID() {
        this.nome = "";
        this.id = -1;
    }

    public ParNomeID(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public int hashCode() {
        return this.id;
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
        dos.writeUTF(this.nome);
        dos.writeInt(this.id);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.nome = dis.readUTF();
        this.id = dis.readInt();
    }

    public static int hash(String nome) {
        // Converter o CPF para um número inteiro longo
        long nomeLong = Long.parseLong(nome);

        // Aplicar uma função de hash usando um número primo grande
        int hashValue = (int) (nomeLong % (int)(1e9 + 7));

        // Retornar um valor positivo
        return Math.abs(hashValue);
    }

}
