
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author 
 */
public class Produto extends javax.swing.JFrame {

    /**
     * Creates new form Cadastro
     */
    public Produto() {
        
        initComponents();
        conectar();
        upDateDB();
    }

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    Date dataAtual = new Date();
    SimpleDateFormat data = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat data2 = new SimpleDateFormat("yyyy-MM-dd");
    String dataFormatadaBR = data.format(dataAtual);
    String dataFormatada = data2.format(dataAtual);
    
    public void conectar(){
        String servidor = "jdbc:mysql://localhost:3306/bd_produtos";
        String user = "root";
        String senha = "root";
        String driver = "com.mysql.cj.jdbc.Driver";
        try{            
            Class.forName(driver);
            this.connection = DriverManager.getConnection(servidor, user, senha);
            this.statement = this.connection.createStatement();
            System.out.println("Conectado!");
        }catch(Exception e){
            System.out.println("Erro: " + e);
            throw new RuntimeException();
        }   
    }
    
    //Método para inserir um NOVO atributo
    public void inserir(String status,String nome,String descricao,int qtd_estoque,
            int estoque_minimo,int estoque_maximo,float preco_compra,float preco_venda,
            int bar_code,int ncm,float fator,String data_cadastro,String imagem){
        try{
            String inserirQuery = "insert into `produto`(`status`,`nome`,`descricao`,"
                    + "`qtd_estoque`,`estoque_minimo`,`estoque_maximo`,`preco_compra`,"
                    + "`preco_venda`,`bar_code`,`ncm`,`fator`,`data_cadastro`,`imagem`) "
                    + "values ('" + status + "','" + nome + "','" + descricao + "',"
                    + "'" + qtd_estoque + "','" + estoque_minimo + "','" + estoque_maximo + "',"
                    + "'" + preco_compra + "', '" + preco_venda + "', '" + bar_code + "', "
                    + "'" + ncm + "','" + fator + "','" + data_cadastro + "','" +imagem + "');";

            //
            
            this.statement.execute(inserirQuery);
            }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro ao inserir o novo produto: " + e);
        }
    }
    
    //Método para alterar alguma info do produto
    public void alterar(Integer Id,String nome ,String status,float preco_venda,
            float preco_compra,int qtd_estoque,int estoque_minimo,int estoque_maximo,float fator){
        try{
            String alterarQuery = "update `produto` set `status`= '" + status + "',`nome`= '" + nome + "',`qtd_estoque`= '" + qtd_estoque + "', `estoque_minimo`= '" + estoque_minimo + "',`estoque_maximo`= '" + estoque_maximo + "',`preco_compra`= '" + preco_compra + "',`preco_venda`= '" + preco_venda + "',`fator` = '" + fator + "' where Id = " + Id;
            this.statement.execute(alterarQuery);
            
            JOptionPane.showMessageDialog(null,"Produto alterado com sucesso!!");
        }catch(SQLException e){ 
            System.out.println("Erro: " + e);
//            throw new RuntimeException();
        }
    }
    
    //Método para excluir um produto
    public void apagar(String Id){
        try{
            String deletarQuery = "delete from `produto` where `id` = " + Id;
            this.statement.execute(deletarQuery);
            JOptionPane.showMessageDialog(null,"Produto excluido com sucesso!!");
        }catch(SQLException e){
            System.out.println("Erro: " + e);
        }
    }

    public void limpar(){
        txtCodigo.setText("");
        txtData.setText("");
        txtNome.setText("");
        txtQtdEst.setText("");
        txtDesc.setText("");
        txtEtqMin.setText("");
        txtEtqMax.setText("");
        txtPrecoCompra.setText("");
        txtPrecoVenda.setText("");
        txtLucro.setText("");
        txtNCM.setText("");
        txtGTINEAN.setText("");
        txtImagem.setText("");
        lblImagem.setText("");
        lblImagem.setIcon(new ImageIcon(""));
    }
    
    public void upDateDB(){
        try{
            tabela.clearSelection();
            PreparedStatement pst = connection.prepareStatement("select * from `produto`;");
            resultSet = pst.executeQuery();
            ResultSetMetaData stData = resultSet.getMetaData();   
            Integer qtsColunas = stData.getColumnCount();
            DefaultTableModel recordTable = (DefaultTableModel)tabela.getModel();
            recordTable.getDataVector().removeAllElements();
            while(resultSet.next()){
                Vector columnData = new Vector();
                for (int coluna = 1; coluna <= qtsColunas; coluna++){
                    columnData.add(resultSet.getString("id"));
                    columnData.add(resultSet.getString("status"));
                    columnData.add(resultSet.getString("nome"));
                    columnData.add(resultSet.getString("qtd_estoque"));
                    columnData.add(resultSet.getString("estoque_minimo"));
                    columnData.add(resultSet.getString("estoque_maximo"));
                    columnData.add(resultSet.getString("preco_compra"));
                    columnData.add(resultSet.getString("preco_venda"));
                    columnData.add(resultSet.getString("fator"));
                    columnData.add(resultSet.getString("descricao"));                    
                    columnData.add(resultSet.getString("bar_code"));
                    columnData.add(resultSet.getString("ncm"));
                    columnData.add(resultSet.getString("data_cadastro"));
                    columnData.add(resultSet.getString("imagem"));
                    }
                recordTable.addRow(columnData);
            }       
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Erro aqui: " + e.getMessage());
        }
    }
  
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtGTINEAN = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblImagem = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNCM = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtImagem = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtEtqMin = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtEtqMax = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtPrecoCompra = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnNovo = new javax.swing.JToggleButton();
        btnAlterar = new javax.swing.JToggleButton();
        btnApagar = new javax.swing.JToggleButton();
        btnLimpar = new javax.swing.JToggleButton();
        btnImprimir = new javax.swing.JToggleButton();
        btnSair = new javax.swing.JToggleButton();
        txtNome = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtPrecoVenda = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtLucro = new javax.swing.JTextField();
        txtQtdEst = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Código:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Status:");

        txtDesc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Data do cadastro:");

        cbStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "I - Inativo", "A - Ativo" }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Nome:");

        txtGTINEAN.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Qtd. em estoque:");

        lblImagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagem.setText(".");
        lblImagem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Descrição:");

        txtNCM.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Estoque mínimo:");

        txtImagem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtImagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtImagemMouseClicked(evt);
            }
        });
        txtImagem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtImagemKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Estoque máximo:");

        txtEtqMin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Imagem do produto:");

        tabela.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Status", "Nome", "Qtd Est", "Qtd Min", "Qtd Max", "Preço Com.", "Preço Ven.", "Fator %", "Título 10", "Título 11", "Título 12", "Título 13"
            }
        ));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Preço de compra:");

        txtEtqMax.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Preço de venda:");

        txtPrecoCompra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPrecoCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecoCompraActionPerformed(evt);
            }
        });

        txtData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnNovo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnAlterar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnApagar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnApagar.setText("Apagar");
        btnApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApagarActionPerformed(evt);
            }
        });

        btnLimpar.setBackground(new java.awt.Color(204, 204, 255));
        btnLimpar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLimpar.setSelected(true);
        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnImprimir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnSair.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnApagar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(btnNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAlterar)
                .addGap(63, 63, 63)
                .addComponent(btnApagar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpar)
                .addGap(72, 72, 72)
                .addComponent(btnImprimir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSair)
                .addContainerGap())
        );

        txtNome.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(204, 153, 255));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Sistema - Cadastro de Produtos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(261, 261, 261)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("NCM:");

        txtPrecoVenda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPrecoVenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecoVendaFocusLost(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Fator lucro (%):");

        txtLucro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtLucro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLucroFocusLost(evt);
            }
        });
        txtLucro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLucroMouseClicked(evt);
            }
        });

        txtQtdEst.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Código de barra GTIN / EAN:");
        jLabel16.setToolTipText("");

        jMenu1.setText("Cadastro");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Sobre");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuItem3.setText("Sobre...");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem1.setText("Sair");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtGTINEAN, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtEtqMin, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12)
                                            .addComponent(txtPrecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14)
                                            .addComponent(txtLucro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtEtqMax, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13)
                                            .addComponent(txtPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15)
                                            .addComponent(txtNCM, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(46, 46, 46)
                                        .addComponent(jLabel11)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(127, 127, 127)
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7)
                                    .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(31, 31, 31)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtQtdEst, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(36, 36, 36)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)))
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQtdEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(6, 6, 6)
                        .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtEtqMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel12)
                                        .addGap(6, 6, 6)
                                        .addComponent(txtPrecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel14)
                                        .addGap(11, 11, 11)
                                        .addComponent(txtLucro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtEtqMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel13)
                                        .addGap(6, 6, 6)
                                        .addComponent(txtPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel15)
                                        .addGap(11, 11, 11)
                                        .addComponent(txtNCM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(13, 13, 13)
                                .addComponent(jLabel16))))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGTINEAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        String dataFormatad = data.format(dataAtual);
        txtData.setText(dataFormatadaBR);
        upDateDB();        
    }//GEN-LAST:event_formWindowActivated

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        
    }//GEN-LAST:event_formMouseClicked

    private void txtImagemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImagemKeyReleased
        //Carregar imagem
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("C:/Users/Aluno/Downloads/CadastroProdutos/CadastroProdutos/src/Img/" + txtImagem.getText() + ".jpg").getImage().getScaledInstance(274,155,Image.SCALE_DEFAULT ));
        lblImagem.setIcon(imageIcon);
    }//GEN-LAST:event_txtImagemKeyReleased

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        //datagredview
        if(evt.getClickCount() == 1){
            txtCodigo.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
            cbStatus.setSelectedItem(tabela.getValueAt(tabela.getSelectedRow(), 1).toString());
            txtNome.setText(tabela.getValueAt(tabela.getSelectedRow(), 2).toString());
            txtQtdEst.setText(tabela.getValueAt(tabela.getSelectedRow(),3).toString());
            txtEtqMin.setText(tabela.getValueAt(tabela.getSelectedRow(), 4).toString());
            txtEtqMax.setText(tabela.getValueAt(tabela.getSelectedRow(), 5).toString());
            txtPrecoCompra.setText(tabela.getValueAt(tabela.getSelectedRow(), 6).toString());
            txtPrecoVenda.setText(tabela.getValueAt(tabela.getSelectedRow(), 7).toString());
            txtLucro.setText(tabela.getValueAt(tabela.getSelectedRow(), 8).toString());
            txtDesc.setText(tabela.getValueAt(tabela.getSelectedRow(), 9).toString());
            txtGTINEAN.setText(tabela.getValueAt(tabela.getSelectedRow(), 10).toString());
            txtNCM.setText(tabela.getValueAt(tabela.getSelectedRow(), 11).toString());
            txtImagem.setText(tabela.getValueAt(tabela.getSelectedRow(), 12).toString());
            txtData.setText(tabela.getValueAt(tabela.getSelectedRow(), 13).toString());
        }
    }//GEN-LAST:event_tabelaMouseClicked

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // Botão NOVO
        try {
            inserir(cbStatus.getSelectedItem().toString(),
                    txtNome.getText(),
                txtDesc.getText(), Integer.parseInt(txtQtdEst.getText()),
                Integer.parseInt(txtEtqMin.getText()),
                Integer.parseInt(txtEtqMax.getText()),
                Float.parseFloat(txtPrecoCompra.getText()),
                Float.parseFloat(txtPrecoVenda.getText()), 
                Integer.parseInt(txtGTINEAN.getText()), 
                Integer.parseInt(txtNCM.getText()),
                Float.parseFloat(txtLucro.getText()), 
                dataFormatada, txtImagem.getText());

            limpar();
            upDateDB();
            String dataFormatad = data.format(dataAtual);
            txtData.setText(dataFormatadaBR);

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null,"Erro bt: " + erro);
        }
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        //Botão alterar
        try {
            alterar(Integer.parseInt(txtCodigo.getText()),
                    txtNome.getText(),
                cbStatus.getSelectedItem().toString(),
                Float.parseFloat(txtPrecoCompra.getText()),
                Float.parseFloat(txtPrecoVenda.getText()),
                Integer.parseInt(txtQtdEst.getText()),
                Integer.parseInt(txtEtqMin.getText()),
                Integer.parseInt(txtEtqMax.getText()),
                Float.parseFloat(txtLucro.getText()));
            upDateDB();
            String dataFormatad = data.format(dataAtual);
            txtData.setText(dataFormatadaBR);

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null,"Erro: " + erro);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarActionPerformed
        // Botão apagar
        try {
            if(!txtCodigo.getText().equals("")){
                apagar(txtCodigo.getText());
                limpar();
                upDateDB();
                String dataFormatad = data.format(dataAtual);
                txtData.setText(dataFormatadaBR);

            }else
            JOptionPane.showMessageDialog(null,"Preencha o campo do ID!");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null,"Erro: " + erro);
        }
    }//GEN-LAST:event_btnApagarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpar();
        String dataFormatad = data.format(dataAtual);
        txtData.setText(dataFormatadaBR);
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        MessageFormat header = new MessageFormat ("Impressão Padrão");
        MessageFormat footer = new MessageFormat ("Página {0, number, integer}");
        try{
            tabela.print(JTable.PrintMode.NORMAL,header,footer);
        }catch(java.awt.print.PrinterException e){
            System.err.format("Impressão não efetuada", e.getMessage());
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        //Botão SAIR
        System.exit(0);
    }//GEN-LAST:event_btnSairActionPerformed

    private void txtPrecoVendaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecoVendaFocusLost

    }//GEN-LAST:event_txtPrecoVendaFocusLost

    private void txtLucroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLucroFocusLost
        //Cálculo do lucro
        if(!txtPrecoVenda.getText().isEmpty()){

            float lucro;

            lucro = (Float.parseFloat(txtPrecoVenda.getText()) - Float.parseFloat(txtPrecoCompra.getText()));
            lucro = (lucro  / Float.parseFloat(txtPrecoCompra.getText()));
            lucro = lucro * 100;
            txtLucro.setText(String.valueOf(lucro));
        }
    }//GEN-LAST:event_txtLucroFocusLost

    private void txtImagemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtImagemMouseClicked
        //Carregar imagem
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("C:/Users/jhenn/Documents/NetBeansProjects/CadastroProdutos/src/Img/" + txtImagem.getText() + ".jpg").getImage().getScaledInstance(125,125,Image.SCALE_DEFAULT ));
        lblImagem.setIcon(imageIcon);
    }//GEN-LAST:event_txtImagemMouseClicked

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed

    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // Sair do sobre pela opção:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        //Ir para o form SOBRE:
        new Sobre().setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void txtPrecoCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecoCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoCompraActionPerformed

    private void txtLucroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLucroMouseClicked
       //Cálculo do lucro
        if(!txtPrecoVenda.getText().isEmpty()){

            float lucro;

            lucro = (Float.parseFloat(txtPrecoVenda.getText()) - Float.parseFloat(txtPrecoCompra.getText()));
            lucro = (lucro  / Float.parseFloat(txtPrecoCompra.getText()));
            lucro = lucro * 100;
            txtLucro.setText(String.valueOf(lucro));
        }
    }//GEN-LAST:event_txtLucroMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Produto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Produto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Produto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Produto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Produto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAlterar;
    private javax.swing.JToggleButton btnApagar;
    private javax.swing.JToggleButton btnImprimir;
    private javax.swing.JToggleButton btnLimpar;
    private javax.swing.JToggleButton btnNovo;
    private javax.swing.JToggleButton btnSair;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImagem;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtEtqMax;
    private javax.swing.JTextField txtEtqMin;
    private javax.swing.JTextField txtGTINEAN;
    private javax.swing.JTextField txtImagem;
    private javax.swing.JTextField txtLucro;
    private javax.swing.JTextField txtNCM;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPrecoCompra;
    private javax.swing.JTextField txtPrecoVenda;
    private javax.swing.JTextField txtQtdEst;
    // End of variables declaration//GEN-END:variables
}
