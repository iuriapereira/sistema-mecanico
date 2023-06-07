package banco;
// Generated 06/06/2023 20:44:58 by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TbVenda generated by hbm2java
 */
public class TbVenda  implements java.io.Serializable {


     private Integer venId;
     private TbCliente tbCliente;
     private TbTipoPagamento tbTipoPagamento;
     private Date venData;
     private Set tbVendaSers = new HashSet(0);
     private Set tbVenPecas = new HashSet(0);

    public TbVenda() {
    }

	
    public TbVenda(TbCliente tbCliente, TbTipoPagamento tbTipoPagamento, Date venData) {
        this.tbCliente = tbCliente;
        this.tbTipoPagamento = tbTipoPagamento;
        this.venData = venData;
    }
    public TbVenda(TbCliente tbCliente, TbTipoPagamento tbTipoPagamento, Date venData, Set tbVendaSers, Set tbVenPecas) {
       this.tbCliente = tbCliente;
       this.tbTipoPagamento = tbTipoPagamento;
       this.venData = venData;
       this.tbVendaSers = tbVendaSers;
       this.tbVenPecas = tbVenPecas;
    }
   
    public Integer getVenId() {
        return this.venId;
    }
    
    public void setVenId(Integer venId) {
        this.venId = venId;
    }
    public TbCliente getTbCliente() {
        return this.tbCliente;
    }
    
    public void setTbCliente(TbCliente tbCliente) {
        this.tbCliente = tbCliente;
    }
    public TbTipoPagamento getTbTipoPagamento() {
        return this.tbTipoPagamento;
    }
    
    public void setTbTipoPagamento(TbTipoPagamento tbTipoPagamento) {
        this.tbTipoPagamento = tbTipoPagamento;
    }
    public Date getVenData() {
        return this.venData;
    }
    
    public void setVenData(Date venData) {
        this.venData = venData;
    }
    public Set getTbVendaSers() {
        return this.tbVendaSers;
    }
    
    public void setTbVendaSers(Set tbVendaSers) {
        this.tbVendaSers = tbVendaSers;
    }
    public Set getTbVenPecas() {
        return this.tbVenPecas;
    }
    
    public void setTbVenPecas(Set tbVenPecas) {
        this.tbVenPecas = tbVenPecas;
    }




}


