
package bancopoo;

import banco.TbEstoque;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

class InterfaceEstoque extends InterfaceAbstrata{
    private final Session session;

    public InterfaceEstoque(JFrame mainFrame, Session session) {
        super(mainFrame,session);
        this.session = session;
    }

    @Override
    protected JTable createTable(Session session) {
        // Código para buscar os dados do banco de dados usando Hibernate
        String hql = "SELECT e.tbFornecedorHasPeca.tbPeca.peDescricao, e.estoQuantidade, e.estoValorUni, e.tbFornecedorHasPeca.tbPeca.peQuantMin, e.tbFornecedorHasPeca.tbFornecedor.tbEntidade.entNomeFantasia FROM TbEstoque e";
        Query query = session.createQuery(hql);

        String[] columnNames = {"Nome Produto", "Quantidade em Estoque", "Valor Unitário", "Quantidade Mínima", "Fornecedor"};

        // Modelo da tabela não editável
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Object[]> results = query.list();
        for (Object[] result : results) {
            String nome = (String) result[0];
            int qtd_e = (int) result[1];
            int valor = (int) result[2];
            int qtd_m = (int) result[3];
            String fornecedor = (String) result[4];          
            
            model.addRow(new Object[]{nome, qtd_e,valor,qtd_m,fornecedor}); // Adicione outras colunas conforme necessário
        }
         // Parte superior com os botões flutuantes
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createSmallButton(buttonIcons[i]);
            String label = buttonLabels[i];
            button.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Verifica se uma janela menor já está aberta
                    if (!isSmallWindowOpen) {
                        if (label.equals("Inserir")) {
                            InterfaceInsereEstoque inserir = new InterfaceInsereEstoque(mainFrame, session);
                            inserir.showInterface();
                        } else if (label.equals("Alterar")) {
                            //InterfaceFornecedor forne = new InterfaceFornecedor(mainFrame);
                            //forne.show();
                        } else if (label.equals("Remover")) {
                            //InterfaceFuncionario func = new InterfaceFuncionario(mainFrame);
                            //func.show();
                        }
                    }
                }
                
            });
            buttonPanel.add(button);
        }
        
        // Cria a tabela
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);      

        return table;
    }
}
