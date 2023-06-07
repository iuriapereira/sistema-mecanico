
package bancopoo;

import banco.TbVendaSer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;

class InterfaceServ extends InterfaceAbstrata {
    private final Session session;

    public InterfaceServ(JFrame mainFrame, Session session) {
        super(mainFrame,session);
        this.session = session;
    }

    @Override
    protected JTable createTable(Session session) {
        Criteria criteria = session.createCriteria(TbVendaSer.class);
        ArrayList<TbVendaSer> servicos = (ArrayList<TbVendaSer>) criteria.list();

        // Dados da tabela
        Object[][] data = new Object[servicos.size()][6];
        for (int i = 0; i < servicos.size(); i++) {
            TbVendaSer servico = servicos.get(i);
            data[i][0] = servico.getTbServico().getSerDescricao();
            data[i][1] = servico.getVsValorServico();
            data[i][2] = servico.getTbVenda().getTbCliente().getTbEntidade().getEntNomeFantasia();
            data[i][3] = servico.getTbVeiculo().getVeiMarca();
            data[i][4] = servico.getTbVeiculo().getVeiModelo();
            data[i][5] = servico.getTbVeiculo().getVeiPlaca();
        }
        String[] columnNames = {"Serviço", "Valor", "Cliente", "Marca Veiculo", "Modelo Veiculo", "Placa Veiculo" };

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