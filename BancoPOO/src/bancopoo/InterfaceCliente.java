package bancopoo;

import banco.TbCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;


class InterfaceCliente extends InterfaceAbstrata{
    protected final Session session;

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
                            InterfaceInsereCliente inserir = new InterfaceInsereCliente(mainFrame, session);
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
