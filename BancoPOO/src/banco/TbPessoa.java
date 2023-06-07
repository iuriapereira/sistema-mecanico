package banco;
// Generated 02/06/2023 13:27:54 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TbPessoa generated by hbm2java
 */
public class TbPessoa  implements java.io.Serializable {


     private String pesCpf;
     private TbEndereco tbEndereco;
     private TbSexo tbSexo;
     private String pesNome;
     private String pesTelefone;
     private String pesEmail;
     private Set tbEmpregados = new HashSet(0);
     private Set tbClientes = new HashSet(0);

    public TbPessoa() {
    }

	
    public TbPessoa(String pesCpf, TbEndereco tbEndereco, TbSexo tbSexo, String pesNome, String pesTelefone) {
        this.pesCpf = pesCpf;
        this.tbEndereco = tbEndereco;
        this.tbSexo = tbSexo;
        this.pesNome = pesNome;
        this.pesTelefone = pesTelefone;
    }
    public TbPessoa(String pesCpf, TbEndereco tbEndereco, TbSexo tbSexo, String pesNome, String pesTelefone, String pesEmail, Set tbEmpregados, Set tbClientes) {
       this.pesCpf = pesCpf;
       this.tbEndereco = tbEndereco;
       this.tbSexo = tbSexo;
       this.pesNome = pesNome;
       this.pesTelefone = pesTelefone;
       this.pesEmail = pesEmail;
       this.tbEmpregados = tbEmpregados;
       this.tbClientes = tbClientes;
    }
   
    public String getPesCpf() {
        return this.pesCpf;
    }
    
    public void setPesCpf(String pesCpf) {
        this.pesCpf = pesCpf;
    }
    public TbEndereco getTbEndereco() {
        return this.tbEndereco;
    }
    
    public void setTbEndereco(TbEndereco tbEndereco) {
        this.tbEndereco = tbEndereco;
    }
    public TbSexo getTbSexo() {
        return this.tbSexo;
    }
    
    public void setTbSexo(TbSexo tbSexo) {
        this.tbSexo = tbSexo;
    }
    public String getPesNome() {
        return this.pesNome;
    }
    
    public void setPesNome(String pesNome) {
        this.pesNome = pesNome;
    }
    public String getPesTelefone() {
        return this.pesTelefone;
    }
    
    public void setPesTelefone(String pesTelefone) {
        this.pesTelefone = pesTelefone;
    }
    public String getPesEmail() {
        return this.pesEmail;
    }
    
    public void setPesEmail(String pesEmail) {
        this.pesEmail = pesEmail;
    }
    public Set getTbEmpregados() {
        return this.tbEmpregados;
    }
    
    public void setTbEmpregados(Set tbEmpregados) {
        this.tbEmpregados = tbEmpregados;
    }
    public Set getTbClientes() {
        return this.tbClientes;
    }
    
    public void setTbClientes(Set tbClientes) {
        this.tbClientes = tbClientes;
    }
    public String getNome() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }





}


