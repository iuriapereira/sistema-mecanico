package bancopoo;

import banco.TbFornecedor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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