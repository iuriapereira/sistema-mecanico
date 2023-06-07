
package bancopoo;

import banco.TbEstoque;
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

        // Cria a tabela
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);      

        return table;
    }
}
