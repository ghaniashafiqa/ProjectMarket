/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package market;

import java.sql.ResultSet;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class MainMenu extends javax.swing.JFrame {

    private DefaultTableModel productTableModel;
    private DefaultTableModel cartTableModel;
    private DefaultTableModel transactionTableModel;

    private ArrayList<Product> productList = new ArrayList<Product>();
    private ArrayList<CartItem> cartList = new ArrayList<CartItem>();
    private ArrayList<TransactionItem> transactionList = new ArrayList<TransactionItem>();

    private int totalTransaction = 0;
    private int totalTransactionHistory = 0;

    private Database dbConnection = new Database();

    private int loggedInUserId;

    // Untuk ubah angka total belanjaan
    private void displayTotalTransactionCart(int amount) {
        // Format jadi bentuk currency
        Locale locale = new Locale("id", "ID");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        // Set UI (label) jadi total price
        lbTotalPrice.setText(currencyFormatter.format(amount));
    }

    /**
     * Creates new form MainMenu
     */
    public MainMenu(int userId) {
        initComponents();
        this.initTable();
        this.loggedInUserId = userId;
        this.fetchProductsTable();
        this.fetchCartsTable();
        this.fetchTransactionsTable();
    }

    private void initTable() {
        productTableModel = new DefaultTableModel();

        // Initialize products table
        tableProductList.setModel(productTableModel);
        tableProductCustomer.setModel(productTableModel);

        productTableModel.addColumn("ID");
        productTableModel.addColumn("Product Name");
        productTableModel.addColumn("Price");
        productTableModel.addColumn("Category");
        productTableModel.addColumn("Stock");

        // Initialize cart table
        cartTableModel = new DefaultTableModel();
        tableProductCustomer.setModel(cartTableModel);

        cartTableModel.addColumn("Product ID");
        cartTableModel.addColumn("Product Name");
        cartTableModel.addColumn("Price");
        cartTableModel.addColumn("Category");
        cartTableModel.addColumn("Quantity");

        // Initialize transactions table
        transactionTableModel = new DefaultTableModel();
        tableTransaction.setModel(transactionTableModel);

        transactionTableModel.addColumn("ID");
        transactionTableModel.addColumn("Product Name");
        transactionTableModel.addColumn("Price");
        transactionTableModel.addColumn("Quantity");
        transactionTableModel.addColumn("Total Price (with tax)");

    }

    private void fetchProductsTable() {
        try {
            productList.clear();
            productTableModel.setRowCount(0);

            ResultSet productsData = dbConnection.readQuery(
                    "SELECT * FROM products p "
                    + "JOIN categories c "
                    + "ON c.id = p.category_id;"
            );

            while (productsData.next()) {
                int id = productsData.getInt("id");
                String productName = productsData.getString("product_name");
                int price = productsData.getInt("price");
                int stock = productsData.getInt("stock");
                String category = productsData.getString("category_name");

                Object[] obj = {id, productName, price, category, stock};
                productTableModel.addRow(obj);

                Product product = new Product(productName, price, category, stock);
                product.setID(Integer.toString(id));

                productList.add(product);
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchCartsTable() {
        try {
            cartList.clear();
            cartTableModel.setRowCount(0);

            ResultSet cartsData = dbConnection.readQuery(
                    "SELECT * FROM carts c "
                    + "JOIN products p ON c.product_id = p.id "
                    + "JOIN categories cat ON p.category_id = cat.id "
                    + "WHERE c.user_id = " + Integer.toString(loggedInUserId)
            );

            while (cartsData.next()) {
                int id = cartsData.getInt("product_id");
                String productName = cartsData.getString("product_name");
                int quantity = cartsData.getInt("quantity");
                String categoryName = cartsData.getString("category_name");
                int price = cartsData.getInt("price");
                int stock = cartsData.getInt("stock");

                Object[] obj = {id, productName, price, categoryName, quantity};
                cartTableModel.addRow(obj);

                CartItem cartItem = new CartItem(quantity, productName, price, categoryName, stock);
                cartItem.setID(Integer.toString(id));

                cartList.add(cartItem);

                totalTransaction += cartItem.calculateTotalPrice();
            };
            this.displayTotalTransactionCart(totalTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchTransactionsTable() {
        try {
            transactionList.clear();
            transactionTableModel.setRowCount(0);

            ResultSet transactionsData = dbConnection.readQuery(
                    "SELECT *, t.id AS transaction_id FROM transactions t "
                    + "JOIN products p ON t.product_id = p.id "
                    + "JOIN categories cat ON p.category_id = cat.id "
                    + "WHERE t.user_id = " + Integer.toString(loggedInUserId)
            );

            while (transactionsData.next()) {
                int id = transactionsData.getInt("transaction_id");
                String productName = transactionsData.getString("product_name");
                int quantity = transactionsData.getInt("quantity");
                String categoryName = transactionsData.getString("category_name");
                int price = transactionsData.getInt("transaction_price");
                int stock = transactionsData.getInt("stock");

                Object[] obj = {id, productName, price, quantity, Math.round(price / 10) + price};
                transactionTableModel.addRow(obj);

                TransactionItem transactionItem = new TransactionItem(quantity, productName, price, categoryName, stock);
                transactionItem.setID(Integer.toString(id));

                transactionList.add(transactionItem);

                totalTransactionHistory += transactionItem.calculateTotalPrice();

                // Format jadi bentuk currency
                Locale locale = new Locale("id", "ID");
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

                // Set UI (label) jadi total price
                lbTotalTransactionHistory.setText(currencyFormatter.format(totalTransactionHistory));
            };
        } catch (Exception e) {
            e.printStackTrace();
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

        titleLabel = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        productsPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        tfProductName = new javax.swing.JTextField();
        labelProductName = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        tfPrice = new javax.swing.JTextField();
        labelPrice = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        tfStock = new javax.swing.JTextField();
        labelStock = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        labelCategory = new javax.swing.JLabel();
        cbCategory = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnSubmit = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProductList = new javax.swing.JTable();
        cartPanel = new javax.swing.JPanel();
        tfQuantityCart = new javax.swing.JTextField();
        btnAddCart = new javax.swing.JButton();
        transactionPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableProductCustomer = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbTotalPrice = new javax.swing.JLabel();
        btnCheckout = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableTransaction = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbTotalTransactionHistory = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Supermarket");
        setMaximumSize(new java.awt.Dimension(780, 720));
        setPreferredSize(new java.awt.Dimension(1280, 720));

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Supermarket");
        titleLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        titleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel4.setLayout(new java.awt.GridLayout(2, 2));

        tfProductName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfProductNameActionPerformed(evt);
            }
        });

        labelProductName.setText("Product Name");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(labelProductName)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(tfProductName)
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelProductName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.add(jPanel6);

        tfPrice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPriceActionPerformed(evt);
            }
        });

        labelPrice.setText("Price");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(labelPrice)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(tfPrice)
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPrice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.add(jPanel5);

        tfStock.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfStockActionPerformed(evt);
            }
        });

        labelStock.setText("Stock");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(labelStock)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tfStock)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelStock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.add(jPanel3);

        labelCategory.setText("Category");

        cbCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sayur", "Buah", "Daging" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(labelCategory)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(cbCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCategory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.add(jPanel7);

        jPanel2.setLayout(new java.awt.GridLayout(1, 3, 16, 0));

        btnSubmit.setBackground(new java.awt.Color(56, 161, 105));
        btnSubmit.setForeground(new java.awt.Color(255, 255, 255));
        btnSubmit.setText("Submit");
        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSubmitMouseClicked(evt);
            }
        });
        jPanel2.add(btnSubmit);

        btnReset.setBackground(new java.awt.Color(113, 128, 150));
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel2.add(btnReset);

        btnDelete.setBackground(new java.awt.Color(229, 62, 62));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel2.add(btnDelete);

        tableProductList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Product Name", "Price", "Category", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableProductList.setEditingColumn(1);
        jScrollPane1.setViewportView(tableProductList);

        cartPanel.setLayout(new java.awt.GridLayout(1, 2, 16, 0));

        tfQuantityCart.setText("1");
        tfQuantityCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfQuantityCartActionPerformed(evt);
            }
        });
        cartPanel.add(tfQuantityCart);

        btnAddCart.setBackground(new java.awt.Color(49, 130, 206));
        btnAddCart.setForeground(new java.awt.Color(247, 250, 252));
        btnAddCart.setLabel("Add To Cart");
        btnAddCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCartActionPerformed(evt);
            }
        });
        cartPanel.add(btnAddCart);

        javax.swing.GroupLayout productsPanelLayout = new javax.swing.GroupLayout(productsPanel);
        productsPanel.setLayout(productsPanelLayout);
        productsPanelLayout.setHorizontalGroup(
            productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1405, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(productsPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1405, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        productsPanelLayout.setVerticalGroup(
            productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsPanelLayout.createSequentialGroup()
                .addContainerGap(605, Short.MAX_VALUE)
                .addComponent(cartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
            .addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(productsPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Products", productsPanel);

        tableProductCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableProductCustomer.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tableProductCustomerComponentShown(evt);
            }
        });
        jScrollPane2.setViewportView(tableProductCustomer);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel8.setLayout(new java.awt.GridLayout(2, 1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Total cart anda:");
        jPanel8.add(jLabel1);

        lbTotalPrice.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbTotalPrice.setText("Rp. 0");
        jPanel8.add(lbTotalPrice);

        jPanel1.add(jPanel8);

        btnCheckout.setBackground(new java.awt.Color(56, 161, 105));
        btnCheckout.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCheckout.setForeground(new java.awt.Color(255, 255, 255));
        btnCheckout.setText("Checkout");
        btnCheckout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckoutActionPerformed(evt);
            }
        });
        jPanel1.add(btnCheckout);

        tableTransaction.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tableTransaction);

        jPanel9.setLayout(new java.awt.GridLayout(2, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Total history transaksi anda:");
        jPanel9.add(jLabel2);

        lbTotalTransactionHistory.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbTotalTransactionHistory.setText("Rp. 0");
        jPanel9.add(lbTotalTransactionHistory);

        javax.swing.GroupLayout transactionPanelLayout = new javax.swing.GroupLayout(transactionPanel);
        transactionPanel.setLayout(transactionPanelLayout);
        transactionPanelLayout.setHorizontalGroup(
            transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1405, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        transactionPanelLayout.setVerticalGroup(
            transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transactionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cart", transactionPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(100, 100, 100))
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jTabbedPane1)
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfStockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfStockActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // Clear database product
        productList.clear();

        // Clear UI product
        productTableModel.setRowCount(0);
    }//GEN-LAST:event_btnResetActionPerformed

    private void tfPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPriceActionPerformed

    private void tfProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfProductNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfProductNameActionPerformed

    private void btnSubmitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubmitMouseClicked
        String uniqueID = UUID.randomUUID().toString();
        String productName = tfProductName.getText();
        int price = Integer.parseInt(tfPrice.getText());
        // type cast
        String category = (String) cbCategory.getSelectedItem();
        int stock = Integer.parseInt(tfStock.getText());

        // TODO: handle category ID
        String sqlQuery = String.format("INSERT INTO products VALUES(0, '%s', %s, %s, 1)", productName, price, stock);

        dbConnection.cudQuery(
                sqlQuery
        );

        // Clear fields
        tfProductName.setText("");
        tfPrice.setText("");
        tfStock.setText("");

        this.fetchProductsTable();
    }//GEN-LAST:event_btnSubmitMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int dataDeleteIndex = tableProductList.getSelectedRow();
        Product selectedProduct = productList.get(dataDeleteIndex);

        dbConnection.cudQuery("DELETE FROM products WHERE ID = " + selectedProduct.getID() + ";");

        // Remove from table UI
        productTableModel.removeRow(dataDeleteIndex);

        // Remove from array list (database)
        productList.remove(dataDeleteIndex);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tableProductCustomerComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tableProductCustomerComponentShown
        // TODO add your handling code here:
        this.initTable();
    }//GEN-LAST:event_tableProductCustomerComponentShown

    private void tfQuantityCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfQuantityCartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfQuantityCartActionPerformed

    private void btnAddCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCartActionPerformed
        int quantity = Integer.parseInt(tfQuantityCart.getText());
        int idxSelectedRow = tableProductList.getSelectedRow();
        Product selectedProduct = productList.get(idxSelectedRow);

        boolean successAddToCart = selectedProduct.addToCart(quantity, loggedInUserId);

        // Menghentikan method jika add to cart gagal atau false
        if (!successAddToCart) {
            return;
        }

        this.fetchCartsTable();

        // Update stock in product master table UI
        productTableModel.setValueAt(selectedProduct.getStock(), idxSelectedRow, 4);
    }//GEN-LAST:event_btnAddCartActionPerformed

    private void btnCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutActionPerformed
        // Validation
        if (cartList.size() == 0) {
            return;
        }

        cartList.forEach(item -> {
            String sqlQuery = String.format(
                    "INSERT INTO transactions VALUES (0, %s, %s, %s, %s)",
                    loggedInUserId,
                    item.getID(),
                    item.quantity,
                    item.quantity * item.getPrice()
            );
            dbConnection.cudQuery(sqlQuery);
        });

        dbConnection.cudQuery("DELETE FROM carts WHERE user_id = " + loggedInUserId);

        this.fetchTransactionsTable();

        // Kosongin database
        cartList.clear();

        // Kosongin table cart UI
        cartTableModel.setRowCount(0);

        // Reset total belanjaan jadi 0
        totalTransaction = 0;
        this.displayTotalTransactionCart(totalTransaction);
    }//GEN-LAST:event_btnCheckoutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu(0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCart;
    private javax.swing.JButton btnCheckout;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JPanel cartPanel;
    private javax.swing.JComboBox<String> cbCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelCategory;
    private javax.swing.JLabel labelPrice;
    private javax.swing.JLabel labelProductName;
    private javax.swing.JLabel labelStock;
    private javax.swing.JLabel lbTotalPrice;
    private javax.swing.JLabel lbTotalTransactionHistory;
    private javax.swing.JPanel productsPanel;
    private javax.swing.JTable tableProductCustomer;
    private javax.swing.JTable tableProductList;
    private javax.swing.JTable tableTransaction;
    private javax.swing.JTextField tfPrice;
    private javax.swing.JTextField tfProductName;
    private javax.swing.JTextField tfQuantityCart;
    private javax.swing.JTextField tfStock;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel transactionPanel;
    // End of variables declaration//GEN-END:variables

}
