package banco;
// Generated 06/06/2023 20:44:58 by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * TbPeca generated by hbm2java
 */
public class TbPeca  implements java.io.Serializable {


     private Integer peId;
     private String peDescricao;
     private int peQuantMin;
     private int peQuantRepos;
     private Set tbFornecedorHasPecas = new HashSet(0);

    public TbPeca() {
    }

	
    public TbPeca(String peDescricao, int peQuantMin, int peQuantRepos) {
        this.peDescricao = peDescricao;
        this.peQuantMin = peQuantMin;
        this.peQuantRepos = peQuantRepos;
    }
    public TbPeca(String peDescricao, int peQuantMin, int peQuantRepos, Set tbFornecedorHasPecas) {
       this.peDescricao = peDescricao;
       this.peQuantMin = peQuantMin;
       this.peQuantRepos = peQuantRepos;
       this.tbFornecedorHasPecas = tbFornecedorHasPecas;
    }
   
    public Integer getPeId() {
        return this.peId;
    }
    
    public void setPeId(Integer peId) {
        this.peId = peId;
    }
    public String getPeDescricao() {
        return this.peDescricao;
    }
    
    public void setPeDescricao(String peDescricao) {
        this.peDescricao = peDescricao;
    }
    public int getPeQuantMin() {
        return this.peQuantMin;
    }
    
    public void setPeQuantMin(int peQuantMin) {
        this.peQuantMin = peQuantMin;
    }
    public int getPeQuantRepos() {
        return this.peQuantRepos;
    }
    
    public void setPeQuantRepos(int peQuantRepos) {
        this.peQuantRepos = peQuantRepos;
    }
    public Set getTbFornecedorHasPecas() {
        return this.tbFornecedorHasPecas;
    }
    
    public void setTbFornecedorHasPecas(Set tbFornecedorHasPecas) {
        this.tbFornecedorHasPecas = tbFornecedorHasPecas;
    }




}

