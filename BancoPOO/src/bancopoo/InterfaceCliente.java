package bancopoo;

import banco.TbCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

class InterfaceCliente extends InterfaceAbstrata {

    protected final Session session;

    public InterfaceCliente(JFrame mainFrame, Session session) {
        super(mainFrame, session);
        this.session = session;
    }

    @Override
    protected JTable createTable(Session session) {
        // Código para buscar os dados do banco de dados usando Hibernate
        String hql = "SELECT cli.cliId, cli.tbEntidade.entNome, cli.tbEntidade.entCpfCnpj, cli.tbEntidade.entFone, cli.tbEntidade.entSexo, cli.tbEntidade.entEmail, cli.tbEntidade.entTipo FROM TbCliente cli";
        Query query = session.createQuery(hql);

        String[] columnNames = {"ID","Nome", "Cpf/Cnpj", "Fone", "Sexo", "Email", "Tipo"};

        // Modelo da tabela não editável
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Object[]> results = query.list();
        for (Object[] result : results) {
            int id = (int) result[0];
            String nome = (String) result[1];
            String cpf = (String) result[2];
            String fone = (String) result[3];
            String sexo = (String) result[4];
            String email = (String) result[5];
            String tipo = (String) result[6];            
            
            model.addRow(new Object[]{id,nome, cpf,fone,sexo,email,tipo}); // Adicione outras colunas conforme necessário
        }

        // Parte superior com os botões flutuantes
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createSmallButton(buttonIcons[i]);
            String label = buttonLabels[i];
            button.addActionListener(new ActionListener() {
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
