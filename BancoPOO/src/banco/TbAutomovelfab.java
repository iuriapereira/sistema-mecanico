package banco;
// Generated 02/06/2023 13:27:54 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TbAutomovelfab generated by hbm2java
 */
public class TbAutomovelfab  implements java.io.Serializable {


     private Integer autoFabCod;
     private TbFabricante tbFabricante;
     private TbModelo tbModelo;
     private float autoFabPreco;
     private Set tbPecasautomovels = new HashSet(0);
     private Set tbAutomovels = new HashSet(0);

    public TbAutomovelfab() {
    }

	
    public TbAutomovelfab(TbFabricante tbFabricante, TbModelo tbModelo, float autoFabPreco) {
        this.tbFabricante = tbFabricante;
        this.tbModelo = tbModelo;
        this.autoFabPreco = autoFabPreco;
    }
    public TbAutomovelfab(TbFabricante tbFabricante, TbModelo tbModelo, float autoFabPreco, Set tbPecasautomovels, Set tbAutomovels) {
       this.tbFabricante = tbFabricante;
       this.tbModelo = tbModelo;
       this.autoFabPreco = autoFabPreco;
       this.tbPecasautomovels = tbPecasautomovels;
       this.tbAutomovels = tbAutomovels;
    }
   
    public Integer getAutoFabCod() {
        return this.autoFabCod;
    }
    
    public void setAutoFabCod(Integer autoFabCod) {
        this.autoFabCod = autoFabCod;
    }
    public TbFabricante getTbFabricante() {
        return this.tbFabricante;
    }
    
    public void setTbFabricante(TbFabricante tbFabricante) {
        this.tbFabricante = tbFabricante;
    }
    public TbModelo getTbModelo() {
        return this.tbModelo;
    }
    
    public void setTbModelo(TbModelo tbModelo) {
        this.tbModelo = tbModelo;
    }
    public float getAutoFabPreco() {
        return this.autoFabPreco;
    }
    
    public void setAutoFabPreco(float autoFabPreco) {
        this.autoFabPreco = autoFabPreco;
    }
    public Set getTbPecasautomovels() {
        return this.tbPecasautomovels;
    }
    
    public void setTbPecasautomovels(Set tbPecasautomovels) {
        this.tbPecasautomovels = tbPecasautomovels;
    }
    public Set getTbAutomovels() {
        return this.tbAutomovels;
    }
    
    public void setTbAutomovels(Set tbAutomovels) {
        this.tbAutomovels = tbAutomovels;
    }




}


