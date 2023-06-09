package BancoDeDados;
// Generated 06/06/2023 20:44:58 by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * TbFornecedorHasPeca generated by hbm2java
 */
public class TbFornecedorHasPeca  implements java.io.Serializable {


     private Integer fpId;
     private TbFornecedor tbFornecedor;
     private TbPeca tbPeca;
     private float fpValorCompra;
     private Set tbEstoques = new HashSet(0);

    public TbFornecedorHasPeca() {
    }

	
    public TbFornecedorHasPeca(TbFornecedor tbFornecedor, TbPeca tbPeca,float fpValorCompra) {
        this.tbFornecedor = tbFornecedor;
        this.tbPeca = tbPeca;
        this.fpValorCompra = fpValorCompra;
    }
    public TbFornecedorHasPeca(TbFornecedor tbFornecedor, TbPeca tbPeca, Set tbEstoques,float fpValorCompra) {
       this.tbFornecedor = tbFornecedor;
       this.tbPeca = tbPeca;
       this.tbEstoques = tbEstoques;
       this.fpValorCompra = fpValorCompra;
    }
   
    public Integer getFpId() {
        return this.fpId;
    }
    
    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }
    public TbFornecedor getTbFornecedor() {
        return this.tbFornecedor;
    }
    
    public void setTbFornecedor(TbFornecedor tbFornecedor) {
        this.tbFornecedor = tbFornecedor;
    }
    public TbPeca getTbPeca() {
        return this.tbPeca;
    }
    
    public void setTbPeca(TbPeca tbPeca) {
        this.tbPeca = tbPeca;
    }
    public Set getTbEstoques() {
        return this.tbEstoques;
    }
    
    public void setTbEstoques(Set tbEstoques) {
        this.tbEstoques = tbEstoques;
    }

    public float getFpValorCompra() {
        return fpValorCompra;
    }

    public void setFpValorCompra(float fpValorCompra) {
        this.fpValorCompra = fpValorCompra;
    }


}


