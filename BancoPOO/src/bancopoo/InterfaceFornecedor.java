package bancopoo;

import banco.TbFornecedor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;

public class InterfaceFornecedor extends InterfaceAbstrata {
    private Session session;

    public InterfaceFornecedor(JFrame mainFrame, Session session) {
        super(mainFrame,session);
        this.session = session;
    }

    @Override
    protected JTable createTable(Session session) {
        Criteria criteria = session.createCriteria(TbFornecedor.class);
        ArrayList<TbFornecedor> fornecedores = (ArrayList<TbFornecedor>) criteria.list();

        // Dados da tabela
        Object[][] data = new Object[fornecedores.size()][4];
        for (int i = 0; i < fornecedores.size(); i++) {
            TbFornecedor fornecedor = fornecedores.get(i);
            data[i][0] = fornecedor.getTbEntidade().getEntCpfCnpj();
            data[i][1] = fornecedor.getTbEntidade().getEntNomeFantasia();
            data[i][2] = fornecedor.getTbEntidade().getEntFone();
            data[i][3] = fornecedor.getTbEntidade().getEntEmail();
        }
        String[] columnNames = {"Cpf/Cnpj", "Nome Fantasia", "Fone", "Email"};

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