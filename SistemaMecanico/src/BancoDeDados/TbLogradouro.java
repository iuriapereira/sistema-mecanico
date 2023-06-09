package BancoDeDados;
// Generated 06/06/2023 20:44:58 by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * TbLogradouro generated by hbm2java
 */
public class TbLogradouro  implements java.io.Serializable {


     private Integer logId;
     private String logDescricao;
     private Set tbEndPostals = new HashSet(0);

    public TbLogradouro() {
    }

	
    public TbLogradouro(String logDescricao) {
        this.logDescricao = logDescricao;
    }
    public TbLogradouro(String logDescricao, Set tbEndPostals) {
       this.logDescricao = logDescricao;
       this.tbEndPostals = tbEndPostals;
    }
   
    public Integer getLogId() {
        return this.logId;
    }
    
    public void setLogId(Integer logId) {
        this.logId = logId;
    }
    public String getLogDescricao() {
        return this.logDescricao;
    }
    
    public void setLogDescricao(String logDescricao) {
        this.logDescricao = logDescricao;
    }
    public Set getTbEndPostals() {
        return this.tbEndPostals;
    }
    
    public void setTbEndPostals(Set tbEndPostals) {
        this.tbEndPostals = tbEndPostals;
    }




}


