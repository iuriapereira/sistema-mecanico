package bancopoo;

import banco.TbFuncionario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;

class InterfaceFuncionario extends InterfaceAbstrata {
    private final Session session;

    public InterfaceFuncionario(JFrame mainFrame, Session session) {
        super(mainFrame,session);
        this.session = session;
    }
    
    @Override
    protected JTable createTable(Session session) {
        Criteria criteria = session.createCriteria(TbFuncionario.class);
        ArrayList<TbFuncionario> funcionarios = (ArrayList<TbFuncionario>) criteria.list();

        // Dados da tabela
        Object[][] data = new Object[funcionarios.size()][2];
        for (int i = 0; i < funcionarios.size(); i++) {
            TbFuncionario funcionario = funcionarios.get(i);
            data[i][0] = funcionario.getTbEntidade().getEntCpfCnpj();
            data[i][1] = funcionario.getTbEntidade().getEntNome();
            data[i][2] = funcionario.getTbEntidade().getEntNomeFantasia();
            data[i][3] = funcionario.getTbEntidade().getEntFone();
            data[i][4] = funcionario.getTbEntidade().getEntSexo();
            data[i][6] = funcionario.getTbEntidade().getEntEmail();
            data[i][7] = funcionario.getTbCargo().getCarDescricao();
            data[i][8] = funcionario.getFuncUsuario();
        }
        String[] columnNames = {"Cpf/Cnpj", "Nome", "Nome Fantasia", "Fone","Sexo", "Email","Cargo", "Login"};

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
