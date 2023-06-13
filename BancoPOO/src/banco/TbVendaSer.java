package banco;
// Generated 06/06/2023 20:44:58 by Hibernate Tools 3.6.0



/**
 * TbVendaSer generated by hbm2java
 */
public class TbVendaSer  implements java.io.Serializable {


     private Integer vsId;
     private String vsSerDescricao;
     private TbVenda tbVenda;
     private TbVeiculo tbVeiculo;
     private float vsValorServico;
     private float vsKmPercorrido;
     private Float vsValorKm;

    public TbVendaSer() {
    }

	
    public TbVendaSer(String vsSerDescricao, TbVenda tbVenda, TbVeiculo tbVeiculo, float vsValorServico) {
        this.vsSerDescricao = vsSerDescricao;
        this.tbVenda = tbVenda;
        this.tbVeiculo = tbVeiculo;
        this.vsValorServico = vsValorServico;
    }
    public TbVendaSer(String vsSerDescricao, TbVenda tbVenda, TbVeiculo tbVeiculo, float vsValorServico, float vsKmPercorrido, Float vsValorKm) {
       this.vsSerDescricao = vsSerDescricao;
       this.tbVenda = tbVenda;
       this.tbVeiculo = tbVeiculo;
       this.vsValorServico = vsValorServico;
       this.vsKmPercorrido = vsKmPercorrido;
       this.vsValorKm = vsValorKm;
    }
   
    public Integer getVsId() {
        return this.vsId;
    }
    
    public void setVsId(Integer vsId) {
        this.vsId = vsId;
    }
    
    public TbVenda getTbVenda() {
        return this.tbVenda;
    }
    
    public void setTbVenda(TbVenda tbVenda) {
        this.tbVenda = tbVenda;
    }
    public TbVeiculo getTbVeiculo() {
        return this.tbVeiculo;
    }
    
    public void setTbVeiculo(TbVeiculo tbVeiculo) {
        this.tbVeiculo = tbVeiculo;
    }
    public float getVsValorServico() {
        return this.vsValorServico;
    }
    
    public void setVsValorServico(float vsValorServico) {
        this.vsValorServico = vsValorServico;
    }
    public float getVsKmPercorrido() {
        return this.vsKmPercorrido;
    }
    
    public void setVsKmPercorrido(float vsKmPercorrido) {
        this.vsKmPercorrido = vsKmPercorrido;
    }
    public Float getVsValorKm() {
        return this.vsValorKm;
    }

    public String getVsSerDescricao() {
        return vsSerDescricao;
    }

    public void setVsSerDescricao(String vsSerDescricao) {
        this.vsSerDescricao = vsSerDescricao;
    }
    
    public void setVsValorKm(Float vsValorKm) {
        this.vsValorKm = vsValorKm;
    }




}


