
package bancopoo;

import banco.TbEstoque;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;

class InterfaceEstoque extends InterfaceAbstrata{
    private final Session session;

    public InterfaceEstoque(JFrame mainFrame, Session session) {
        super(mainFrame,session);
        this.session = session;
    }

    @Override
    protected JTable createTable(Session session) {
        Criteria criteria = session.createCriteria(TbEstoque.class);
        ArrayList<TbEstoque> Pecas = (ArrayList<TbEstoque>) criteria.list();

        // Dados da tabela
        Object[][] data = new Object[Pecas.size()][4];
        for (int i = 0; i < Pecas.size(); i++) {
            TbEstoque peca = Pecas.get(i);
            data[i][0] = peca.getTbFornecedorHasPeca().getTbPeca().getPeDescricao();
            data[i][2] = peca.getEstoQuantidade();
            data[i][1] = peca.getEstoValorUni();
            data[i][2] = peca.getTbFornecedorHasPeca().getTbPeca().getPeQuantMin();
            data[i][3] = peca.getTbFornecedorHasPeca().getTbFornecedor().getTbEntidade().getEntNomeFantasia();

        }
        String[] columnNames = {"Nome Produto", "Quantidade em Estoque", "Valor Unitário", "Quantidade Mínima", "Fornecedor"};

        // Modelo da tabela não editável
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
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
                            //InterfaceInsereCliente inserir = new InterfaceInsereCliente(mainFrame);
                            //inserir.showInterface();
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
