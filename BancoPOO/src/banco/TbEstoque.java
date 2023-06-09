package banco;
// Generated 09/06/2023 14:46:48 by Hibernate Tools 3.6.0

import java.util.HashSet;
import java.util.Set;



/**
 * TbEstoque generated by hbm2java
 */
public class TbEstoque  implements java.io.Serializable {


     private Integer estoId;
     private TbFornecedorHasPeca tbFornecedorHasPeca;
     private int estoQuantidade;
     private float estoValorUni;
     private int estoFpId;
     private String estoMedida;
     private Set tbVenPecas = new HashSet(0);

    public TbEstoque() {
    }

	
    public TbEstoque(TbFornecedorHasPeca tbFornecedorHasPeca, int estoQuantidade, float estoValorUni, int estoFpId) {
        this.tbFornecedorHasPeca = tbFornecedorHasPeca;
        this.estoQuantidade = estoQuantidade;
        this.estoValorUni = estoValorUni;
    }
    public TbEstoque(TbFornecedorHasPeca tbFornecedorHasPeca, int estoQuantidade, float estoValorUni, String estoMedida, Set tbVenPecas) {
       this.tbFornecedorHasPeca = tbFornecedorHasPeca;
       this.estoQuantidade = estoQuantidade;
       this.estoValorUni = estoValorUni;
       this.tbVenPecas = tbVenPecas;
       this.estoMedida = estoMedida;
    }
   
    public Integer getEstoId() {
        return this.estoId;
    }
    
    public void setEstoId(Integer estoId) {
        this.estoId = estoId;
    }
    public int getEstoQuantidade() {
        return this.estoQuantidade;
    }
    
    public void setEstoQuantidade(int estoQuantidade) {
        this.estoQuantidade = estoQuantidade;
    }
    public float getEstoValorUni() {
        return this.estoValorUni;
    }
    
    public void setEstoValorUni(float estoValorUni) {
        this.estoValorUni = estoValorUni;
    }
    public int getEstoFpId() {
        return this.estoFpId;
    }
    
    public void setEstoFpId(int estoFpId) {
        this.estoFpId = estoFpId;
    }
    public String getEstoMedida() {
        return this.estoMedida;
    }
    
    public void setEstoMedida(String estoMedida) {
        this.estoMedida = estoMedida;
    }

    public TbFornecedorHasPeca getTbFornecedorHasPeca() {
        return tbFornecedorHasPeca;
    }

    public void setTbFornecedorHasPeca(TbFornecedorHasPeca tbFornecedorHasPeca) {
        this.tbFornecedorHasPeca = tbFornecedorHasPeca;
    }

    public Set getTbVenPecas() {
        return tbVenPecas;
    }

    public void setTbVenPecas(Set tbVenPecas) {
        this.tbVenPecas = tbVenPecas;
    }




}


