package bancopoo;

import banco.TbCliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;


class InterfaceCliente extends InterfaceAbstrata{
    private final Session session;

    public InterfaceCliente(JFrame mainFrame, Session session) {
        super(mainFrame,session);
        this.session = session;
    }

    @Override
    protected JTable createTable(Session session) {
        // Código para buscar os dados do banco de dados usando Hibernate
        Criteria criteria = session.createCriteria(TbCliente.class);
        ArrayList<TbCliente> clientes = (ArrayList<TbCliente>) criteria.list();

        // Dados da tabela
        Object[][] data = new Object[clientes.size()][7];
        for (int i = 0; i < clientes.size(); i++) {
            TbCliente cliente = clientes.get(i);
            data[i][0] = cliente.getTbEntidade().getEntCpfCnpj();
            data[i][1] = cliente.getTbEntidade().getEntNome();
            data[i][2] = cliente.getTbEntidade().getEntNomeFantasia();
            data[i][3] = cliente.getTbEntidade().getEntFone();
            data[i][4] = cliente.getTbEntidade().getEntSexo();
            data[i][5] = cliente.getTbEntidade().getEntEmail();
            data[i][6] = cliente.getTbEntidade().getEntTipo();
        }
        String[] columnNames = {"Cpf/Cnpj", "Nome", "Nome Fantasia", "Fone", "Sexo", "Email", "Tipo"};

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
