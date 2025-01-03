package possystemproject;
//import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
//import javax.swing.Icon;
//import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
//import javax.swing.JLabel;
import javax.swing.JOptionPane;
//import javax.swing.JTable;
//import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.table.TableColumn;
//import net.proteanit.sql.DbUtils;
//import net.proteanit.sql.DbUtils;
//import net.proteanit.sql.DbUtils;



public class Products extends javax.swing.JFrame {

   Connection Con = null;
   PreparedStatement pst = null;
   ResultSet Res = null,Rs1 = null;
   Statement st = null,st1 = null;
   File file;
   String path;
   private ImageIcon format = null;
    public Products() {
        initComponents();
        GetSupplier();   
        SupNamePf.setEditable(false);
        showProducts();
    }
//    private void showProducts(){
//               try{
//                Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb","root","");
//                st = Con.createStatement();
//                Res = st.executeQuery("Select * from producttbl");
//                ProductTable.setModel(DbUtils.resultSetToTableModel(Res));
//                
//            }
//            catch(SQLException e){
//                
//                JOptionPane.showMessageDialog(this, e);
//            }
//          }
    
    
private void showProducts() {
    try {
        Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "");
        st = Con.createStatement();
        Res = st.executeQuery("Select * from producttbl");

        // Custom table model for handling image display
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                return (column == 3) ? ImageIcon.class : Object.class; // Image in column index 3
            }
        };

        // Set table model and row height
        ProductTable.setModel(model);
        ProductTable.setRowHeight(100);

        // Set column names in the model
        model.setColumnIdentifiers(new String[] { "Pid", "PName", "PCat", "Pimage", "PPrice", "PQty", "SupplierId", "SupName" });

        // Populate model with data from the result set
        while (Res.next()) {
            ImageIcon imageIcon = null;
            byte[] imgBytes = Res.getBytes("Pimage");

            if (imgBytes != null) {
                imageIcon = scaledImage(new ImageIcon(imgBytes));
                System.out.println("Image retrieved and scaled"); // Debugging
            } else {
                System.out.println("No image found for product ID: " + Res.getInt("Pid"));
            }

            model.addRow(new Object[] {
                Res.getInt("Pid"),
                Res.getString("PName"),
                Res.getString("PCat"),
                imageIcon,
                Res.getString("PPrice"),
                Res.getString("PQty"),
                Res.getString("SupplierId"),
                Res.getString("SupName")
            });
        }
        Con.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, e);
    }
}

// Method to scale image to fit row height
private ImageIcon scaledImage(ImageIcon imageIcon) {
    Image img = imageIcon.getImage();
    Image scaledImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Adjust size to fit row height
    return new ImageIcon(scaledImg);
}


    
    
//public void showProducts() {
//        try {
//            // Connect to the database
//            Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "");
//            st = Con.createStatement();
//            Res = st.executeQuery("SELECT * FROM producttbl");
//
//            // Define table model with column headers
//            DefaultTableModel model = new DefaultTableModel(new Object[]{"Pid", "PName", "PCat", "Pimage", "PPrice", "PQty", "SupplierId", "SupName"}, 0) {
//                @Override
//                public Class<?> getColumnClass(int column) {
//                    return column == 3 ? ImageIcon.class : Object.class;
//                }
//            };
//
//            // Set model to the table and define row height for images
//            ProductTable.setModel(model);
//            ProductTable.setRowHeight(150);
//
//            // Populate table model with data and convert image bytes to ImageIcon
//            while (Res.next()) {
//                byte[] imgBytes = Res.getBytes("Pimage");
//                ImageIcon imageIcon = null;
//                if (imgBytes != null && imgBytes.length > 0) {
//                    Image img = new ImageIcon(imgBytes).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//                    imageIcon = new ImageIcon(img);
//                }
//
//                model.addRow(new Object[]{
//                    Res.getInt("Pid"),
//                    Res.getString("PName"),
//                    Res.getString("PCat"),
//                    imageIcon, // Add ImageIcon to the model
//                    Res.getString("PPrice"),
//                    Res.getString("PQty"),
//                    Res.getString("SupplierId"),
//                    Res.getString("SupName")
//                });
//            }
//
//            // Close the connection
//            Con.close();
//
//            // Set up custom renderer for image column to display ImageIcons
//            ProductTable.getColumnModel().getColumn(3).setCellRenderer((JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) -> {
//                JLabel label = new JLabel();
//                if (value instanceof ImageIcon) {
//                    label.setIcon((ImageIcon) value);
//                    label.setHorizontalAlignment(JLabel.CENTER);
//                }
//                return label;
//            });
//
//            // Set width for the image column
//            ProductTable.getColumnModel().getColumn(3).setMinWidth(50);
//            ProductTable.getColumnModel().getColumn(3).setMaxWidth(50);
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error loading products: " + e.getMessage());
//        }
//    }



        private void GetSuppName(){
            
             try{
                Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb","root","");
                st = Con.createStatement();               
                String Query = "Select SupName from  suppliertb WHERE SupId ="+SupCb.getSelectedItem().toString();
                Res = st.executeQuery(Query);
                if(Res.next()){
                SupNamePf.setText(Res.getString("SupName"));
                                 
             }          
            }
            catch(HeadlessException | SQLException e){
                 JOptionPane.showMessageDialog(this, e);
            }      
    }
    
    private void GetSupplier(){
             try{
                Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb","root","");
                st = Con.createStatement();
                String Query = "Select * from  suppliertb";
                ResultSet Rs = st.executeQuery(Query);
                while(Rs.next()){
                    String MySup = Rs.getString("SupId");
                    SupCb.addItem(MySup);
                    
             }
                
            }
            catch(HeadlessException | SQLException e){
                 JOptionPane.showMessageDialog(this, e);
            }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        PNameTb = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        PriceTb = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        SupNamePf = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        CatCb = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        SupCb = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        QtyTb = new javax.swing.JTextField();
        SaveBtn = new javax.swing.JButton();
        EditBtn = new javax.swing.JButton();
        DeleteBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ProductTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        supSc = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblpic = new javax.swing.JLabel();
        l = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Product Name");

        PNameTb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PNameTbActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Category");

        PriceTb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PriceTbActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Supplier Name");

        SupNamePf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupNamePfActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Price");

        CatCb.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CatCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Food", "Drink", "Electronics", "Fashion" }));
        CatCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CatCbActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Quantity");

        SupCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupCbActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Supplier Id");

        QtyTb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QtyTbActionPerformed(evt);
            }
        });

        SaveBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SaveBtn.setText("Save");
        SaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveBtnActionPerformed(evt);
            }
        });

        EditBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        EditBtn.setText("Edit");
        EditBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditBtnActionPerformed(evt);
            }
        });

        DeleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DeleteBtn.setText("Delete");
        DeleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteBtnActionPerformed(evt);
            }
        });

        ProductTable.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        ProductTable.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ProductTable.setRowHeight(25);
        ProductTable.setSelectionBackground(new java.awt.Color(255, 204, 0));
        ProductTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ProductTable);

        jPanel6.setBackground(new java.awt.Color(0, 153, 153));
        jPanel6.setAlignmentY(30.0F);

        jPanel7.setBackground(new java.awt.Color(0, 204, 204));

        supSc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        supSc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        supSc.setText("Supplier");
        supSc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                supScMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(supSc, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(supSc, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(0, 204, 204));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Customer");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        jPanel9.setBackground(new java.awt.Color(0, 204, 204));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Billing");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        jPanel10.setBackground(new java.awt.Color(0, 204, 204));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Logout");
        jLabel16.setToolTipText("");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(527, Short.MAX_VALUE))
        );

        jButton1.setText("Browse Image");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblpic.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SupNamePf)
                        .addComponent(PriceTb)
                        .addComponent(jLabel2)
                        .addComponent(PNameTb)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CatCb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SupCb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(QtyTb, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(l, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)
                        .addComponent(SaveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(EditBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(DeleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(432, 432, 432)
                    .addComponent(lblpic, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(746, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PNameTb, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CatCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PriceTb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(QtyTb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SupCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SupNamePf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SaveBtn)
                            .addComponent(EditBtn)
                            .addComponent(DeleteBtn)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(l, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(512, Short.MAX_VALUE)
                    .addComponent(lblpic, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(130, 130, 130)))
        );

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Point Of Sale");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(802, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(318, 318, 318))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void PriceTbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PriceTbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PriceTbActionPerformed

    private void SupNamePfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupNamePfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SupNamePfActionPerformed

    private void QtyTbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QtyTbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_QtyTbActionPerformed

    private void SupCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupCbActionPerformed
        GetSuppName();       
    }//GEN-LAST:event_SupCbActionPerformed

    private void CatCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CatCbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CatCbActionPerformed

    private void PNameTbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PNameTbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PNameTbActionPerformed
    int proId=1;
    private void CountProd(){
    try{
        st1 = Con.createStatement();
        Rs1 = st1.executeQuery("select Max(PId) from producttbl");
        Rs1.next();
        proId = Rs1.getInt(1)+1;
    }catch(SQLException e){
        JOptionPane.showMessageDialog(this, e);
   }
}
    
    private void SaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveBtnActionPerformed
       if (PNameTb.getText().isEmpty() || PriceTb.getText().isEmpty() || QtyTb.getText().isEmpty() || SupNamePf.getText().isEmpty()) {
    JOptionPane.showMessageDialog(this, "Missing Information");
} else {
    try {
    CountProd();
    byte[] imageData;
    if (path != null) {
        BufferedImage bi = ImageIO.read(new File(path));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", baos);
        imageData = baos.toByteArray();
    } else {
        imageData = null; // or set a default image data
    }
    Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "");
    PreparedStatement Add = Con.prepareStatement("insert into producttbl values(?,?,?,?,?,?,?,?)");
    Add.setInt(1, proId);
    Add.setString(2, PNameTb.getText());
    Add.setString(3, CatCb.getSelectedItem().toString());
    Add.setBytes(4, imageData);
    Add.setString(5, PriceTb.getText());
    Add.setString(6, QtyTb.getText());
    Add.setString(7, SupCb.getSelectedItem().toString());
    Add.setString(8, SupNamePf.getText());
    Add.executeUpdate();
    JOptionPane.showMessageDialog(this, "Product Saved Successfully!!!");
    showProducts();
    Con.close();
    Clear();
} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, e.getMessage());
} catch (IOException ex) {
    Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
}}

    }//GEN-LAST:event_SaveBtnActionPerformed
private void Clear(){
    PNameTb.setText("");
    CatCb.setSelectedIndex(-1);
    SupNamePf.setText("");
    QtyTb.setText("");
    PriceTb.setText("");
    SupCb.setSelectedIndex(-1);
    lblpic.setIcon(null);
    key=0;
}
    private void EditBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditBtnActionPerformed
       if (SupNamePf.getText().isEmpty() || PriceTb.getText().isEmpty() || PNameTb.getText().isEmpty() || QtyTb.getText().isEmpty() || CatCb.getSelectedIndex() == -1) {
    JOptionPane.showMessageDialog(this, "Missing Information!");
} else {
    try {
        InputStream is = new FileInputStream(new File(path));
        String updateQuery = "UPDATE producttbl SET PName=?, PCat=?,Pimage=?, PPrice=?, PQty=? , SupplierId=? , SupName=? WHERE PId=?";
        Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "");
        PreparedStatement Add = Con.prepareStatement(updateQuery);

        Add.setString(1, PNameTb.getText());
        Add.setString(2, CatCb.getSelectedItem().toString());
        Add.setString(3,is.toString());
        Add.setString(4, PriceTb.getText());
        Add.setString(5, QtyTb.getText());
        Add.setString(6, SupCb.getSelectedItem().toString());
        Add.setString(7, SupNamePf.getText());
        Add.setInt(8, key);

        if (Add.executeUpdate() == 1) {
            showProducts();
            JOptionPane.showMessageDialog(this, "Product Updated Successfully!!!");
            Clear();
        } else {
            JOptionPane.showMessageDialog(this, "Product Not updated!");
        }

        Con.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,e.getMessage());
    }      catch (FileNotFoundException ex) { 
               Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
           } 
}

    }//GEN-LAST:event_EditBtnActionPerformed
int key =0;
    private void DeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBtnActionPerformed
          if (key == 0) {
    JOptionPane.showMessageDialog(this, "Select a Product");
} else {
    try {
        Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/posdb", "root", "");
        String Query = "DELETE FROM producttbl WHERE PId=?";
        PreparedStatement Delete = Con.prepareStatement(Query);
        Delete.setInt(1, key);
        Delete.executeUpdate();
        JOptionPane.showMessageDialog(this, "Product Deleted!");
        showProducts();
        Clear();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "An error occurred while executing the SQL query: " + e.getMessage());
    } catch (HeadlessException e) {
        JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage());
    } 
}

    }//GEN-LAST:event_DeleteBtnActionPerformed

    private void ProductTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductTableMouseClicked

//    DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
//    int selectedRow = ProductTable.getSelectedRow();
//    key = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
//    PNameTb.setText(model.getValueAt(selectedRow, 1).toString());
//    CatCb.setSelectedItem(model.getValueAt(selectedRow, 2).toString());
//// In the ProductTableMouseClicked method
//byte[] imageData = (byte[]) model.getValueAt(selectedRow, 3);
//if (imageData != null) {
//    ImageIcon ii = new ImageIcon(imageData);
//    lblpic.setIcon(ii);
//} else {
//    lblpic.setIcon(null); // or set a default icon
//}
//    PriceTb.setText(model.getValueAt(selectedRow, 4).toString());
//    QtyTb.setText(model.getValueAt(selectedRow, 5).toString());
//    SupCb.setSelectedItem(model.getValueAt(selectedRow, 6).toString());
//    SupNamePf.setText(model.getValueAt(selectedRow, 7).toString());  


    DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
    int selectedRow = ProductTable.getSelectedRow();
    // Ensure a valid row is selected
    if (selectedRow != -1) {
        // Retrieve data from the selected row
        key = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
        PNameTb.setText(model.getValueAt(selectedRow, 1).toString());
        CatCb.setSelectedItem(model.getValueAt(selectedRow, 2).toString());
            //        // Retrieve the ImageIcon from the model
        ImageIcon imageIcon = (ImageIcon) model.getValueAt(selectedRow, 3);
        if (imageIcon != null) {
            // Optionally scale the image to fit the label
            Image img = imageIcon.getImage(); // Get the image
            Image scaledImg = img.getScaledInstance(lblpic.getWidth(), lblpic.getHeight(), Image.SCALE_SMOOTH); // Scale it
            lblpic.setIcon(new ImageIcon(scaledImg)); // Set the scaled image
            l.setIcon(new ImageIcon(scaledImg));
            System.out.println("successfully");
        } else {
            lblpic.setIcon(null); // Set to null if no image is available
            System.out.println("failed");
        }

//        byte[] image = (byte[]) model.getValueAt(selectedRow, 3);
//        format = new ImageIcon(image);
//        lblpic.setIcon(format);
        PriceTb.setText(model.getValueAt(selectedRow, 4).toString());
        QtyTb.setText(model.getValueAt(selectedRow, 5).toString());
        SupCb.setSelectedItem(model.getValueAt(selectedRow, 6).toString());
        SupNamePf.setText(model.getValueAt(selectedRow, 7).toString());
    }
    }//GEN-LAST:event_ProductTableMouseClicked

    private void supScMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supScMouseClicked
        new Suppliers().setVisible(true);
              this.dispose();
    }//GEN-LAST:event_supScMouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        new Customers().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        new Billings().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
 JFileChooser jfilechooser = new JFileChooser();
   jfilechooser.setMultiSelectionEnabled(false);
   if(jfilechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
       this.file = jfilechooser.getSelectedFile();
       path = file.getAbsolutePath();
     try {
         BufferedImage bi = ImageIO.read(new File(path));
         Image img;
         img = bi.getScaledInstance(134, 134, Image.SCALE_SMOOTH);
         ImageIcon ii =new  ImageIcon(img);
         lblpic.setIcon(ii);
     } catch (IOException ex) {
         Logger.getLogger(Products.class.getName()).log(Level.SEVERE, null, ex);
     }
   }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Products().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CatCb;
    private javax.swing.JButton DeleteBtn;
    private javax.swing.JButton EditBtn;
    private javax.swing.JTextField PNameTb;
    private javax.swing.JTextField PriceTb;
    private javax.swing.JTable ProductTable;
    private javax.swing.JTextField QtyTb;
    private javax.swing.JButton SaveBtn;
    private javax.swing.JComboBox<String> SupCb;
    private javax.swing.JTextField SupNamePf;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel l;
    private javax.swing.JLabel lblpic;
    private javax.swing.JLabel supSc;
    // End of variables declaration//GEN-END:variables

}
