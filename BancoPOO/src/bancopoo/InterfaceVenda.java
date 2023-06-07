package bancopoo;

import banco.TbVenPeca;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;

class InterfaceVenda extends InterfaceAbstrata {
    private final Session session;

    public InterfaceVenda(JFrame mainFrame, Session session) {
        super(mainFrame,session);
        this.session = session;
    }

    @Override
    protected JTable createTable(Session session) {
        Criteria criteria = session.createCriteria(TbVenPeca.class);
        ArrayList<TbVenPeca> vendas = (ArrayList<TbVenPeca>) criteria.list();

        // Dados da tabela
        Object[][] data = new Object[vendas.size()][2];
        for (int i = 0; i < vendas.size(); i++) {
            TbVenPeca venda = vendas.get(i);
            data[i][0] = venda.getTbVenda().getVenId();
            data[i][1] = venda.getTbVenda().getTbCliente().getTbEntidade().getEntNomeFantasia();
        }

        String[] columnNames = {"ID", "Cliente", "Valor Total", "Marca Veiculo", "Modelo Veiculo", "Placa Veiculo"};

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
