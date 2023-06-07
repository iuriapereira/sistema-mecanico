package banco;
// Generated 06/06/2023 20:44:58 by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * TbCliente generated by hbm2java
 */
public class TbCliente  implements java.io.Serializable {


     private Integer cliId;
     private TbEntidade tbEntidade;
     private Set tbVendas = new HashSet(0);

    public TbCliente() {
    }

	
    public TbCliente(TbEntidade tbEntidade) {
        this.tbEntidade = tbEntidade;
    }
    public TbCliente(TbEntidade tbEntidade, Set tbVendas) {
       this.tbEntidade = tbEntidade;
       this.tbVendas = tbVendas;
    }
   
    public Integer getCliId() {
        return this.cliId;
    }
    
    public void setCliId(Integer cliId) {
        this.cliId = cliId;
    }
    public TbEntidade getTbEntidade() {
        return this.tbEntidade;
    }
    
    public void setTbEntidade(TbEntidade tbEntidade) {
        this.tbEntidade = tbEntidade;
    }
    public Set getTbVendas() {
        return this.tbVendas;
    }
    
    public void setTbVendas(Set tbVendas) {
        this.tbVendas = tbVendas;
    }




}


