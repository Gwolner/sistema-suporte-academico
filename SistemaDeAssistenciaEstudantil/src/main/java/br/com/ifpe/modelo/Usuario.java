package br.com.ifpe.modelo;

import com.mysql.fabric.xmlrpc.base.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "tb_usuario")
@Inheritance(strategy = InheritanceType.JOINED)//Estrategia adotada na herença
@DiscriminatorColumn(
        name = "disc_usuario", //Coluna que distingue a subclasse
        discriminatorType = DiscriminatorType.STRING, //Tipo de dado descriminador
        length = 1 //Tamanho do dado descriminador
)
@Access(AccessType.FIELD)
public class Usuario implements Serializable { //Classe Pai na herança
    
    public Usuario() {
        this.emprestimos = new ArrayList<>();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private long idUsuario;
    
    @Column(name = "nome_usuario", nullable = false, length = 100)
    private String nomeUsuario;
    
    @Column(name = "cpf", nullable = false, length = 15)
    private long cpf;
    
    @Column(name = "rg", nullable = false, length = 10)
    private int rg;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "data_nascimento", nullable = true)
    private Data dataNascimento;

    //Cardinalidade Aluno 1 : N Emprestimo
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario",
            fetch = FetchType.LAZY)
    protected List<Emprestimo> emprestimos;
    
    public long getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
    
    public long getCpf() {
        return cpf;
    }
    
    public void setCpf(long cpf) {
        this.cpf = cpf;
    }
    
    public int getRg() {
        return rg;
    }
    
    public void setRg(int rg) {
        this.rg = rg;
    }
    
    public Data getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(Data dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public boolean add(Emprestimo e) {
        if (!emprestimos.contains(e)) {
            e.setUsuario(this);
            return emprestimos.add(e);
        } else {
            return false;
        }
        
    }
    
    public boolean remove(Object o) {
        return emprestimos.remove(o);
    }
    
    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }
    
    public void addEmprestimos(List<Emprestimo> emprestimos) {
        for (Emprestimo emprestimo : emprestimos) {
            this.add(emprestimo);
        }
    }
    
}
