/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineguide;

import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.sql.Connection;
import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.regex.Pattern;
import net.proteanit.sql.DbUtils;
import table.TableCustom;
import java.util.Random;

/**
 *
 * @author USER
 */
public class Dashboard extends javax.swing.JFrame {
    Koneksi k = new Koneksi();
    private PreparedStatement stat;
    private ResultSet rs;
    private DefaultTableModel model = null;
    private String filename;
    private HashMap<String, String> columnMapping;
    private HashMap<String, String> columnUser;
    private String currentSortColumn;
    private boolean isAscending;
    private int currentPage = 1;
    private Integer rowsPerPage = 10;
    /**
     * Creates new form Dashboard
     */
    Color panDefault, panEnter, panDelete;
    private String judul,tahun,negara,genre,pemeran, sinopsis, posterfilm;
    public Dashboard() {
        initComponents();
        k.connect();
        tampilData(currentPage);
        tampilUser(currentPage);
        panEnter = new Color(255,96,0);
        panDefault = new Color(69,69,69);
        panDelete = new Color(237,43,42);
        lebarKolom();
        columnMapping = new HashMap<>();
        columnUser = new HashMap<>();
        columnMapping.put("ID Film", "id_film");
        columnMapping.put("Judul", "judul");
        columnMapping.put("Tahun", "tahun");
        columnMapping.put("Negara", "Negara");
        columnMapping.put("Genre", "genre");
        columnMapping.put("Pemeran Utama", "pemeran_utama");
        columnUser.put("ID User", "id_user");
        columnUser.put("Nama", "nama");
        columnUser.put("Username", "username");
        columnUser.put("Email", "email");
        columnUser.put("Role", "Role");
        currentSortColumn = ""; // Kolom sorting awal kosong
        isAscending = true; // Sorting awal secara ascending
        table.TableCustom.apply(scrollPaneWin111, TableCustom.TableType.MULTI_LINE);
        table.TableCustom.apply(scrollPaneWin113, TableCustom.TableType.MULTI_LINE);
        infoLabel.setText("<html>CineGuide adalah aplikasi informasi film yang komprehensif yang dikembangkan menggunakan Java NetBeans. Aplikasi ini dirancang untuk memberikan pengguna akses ke informasi terperinci tentang film-film terbaru, termasuk judul, tahun rilis, aktor, sinopsis, negara, dan banyak lagi. Dengan CineGuide, pengguna dapat dengan mudah menelusuri daftar film yang tersedia, mencari film berdasarkan genre atau kata kunci, dan mendapatkan informasi lengkap tentang film yang menarik minat mereka.<html>");
        
    }
    
    class tambahfilm extends Dashboard {
        String judul ="",tahun ="",negara="",genre="",pemeran="", sinopsis="", poster="";
        public tambahfilm() {
            judul = txtJudul.getText();
            tahun = txtTahun.getText();
            negara = txtNegara.getText();
            genre = txtGenre.getSelectedItem().toString();
            pemeran = txtPemeran.getText();
            sinopsis = txtSinopsis1.getText();
            
        }
    }
    private void refreshTable() {
    try {
        DefaultTableModel model = (DefaultTableModel) tblHome.getModel();
        model.setRowCount(0); // Menghapus semua baris pada tabel

        String query = "SELECT * FROM film";
        PreparedStatement pst = k.getCon().prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            String id_film = rs.getString("id_film");
            String judul = rs.getString("judul");
            String tahun = rs.getString("tahun");
            String negara = rs.getString("negara");
            String genre = rs.getString("genre");
            String pemeran_utama = rs.getString("pemeran_utama");
            String sinopsis = rs.getString("sinopsis");

            model.addRow(new Object[]{id_film, judul, tahun, negara, genre, pemeran_utama, sinopsis});
        }

        rs.close();
        pst.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex);
    }
}
    class editfilm extends Dashboard{
        String judul ="",tahun ="",negara="",genre="",pemeran="", sinopsis="", poster="", id = "";
        public editfilm() {
            judul = txtJudul1.getText();
            tahun = txtTahun1.getText();
            negara = txtNegara1.getText();
            genre = txtGenre1.getSelectedItem().toString();
            pemeran = txtPemeran1.getText();
            sinopsis = txtSinopsis3.getText();
            id = txtID1.getText();
        }
    }
        
    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public void setNegara(String negara) {
        this.negara = negara;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPemeran(String pemeran) {
        this.pemeran = pemeran;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
    
    public void setPoster(String posterfilm) {
        this.posterfilm = posterfilm;
    }
    
    
    public void lebarKolom(){ 
        TableColumn column;
        tblHome.getTableHeader().setOpaque(false);
        tblHome.getTableHeader().setBackground(Color.WHITE);
        tblHome.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN); 
        column = tblHome.getColumnModel().getColumn(0); 
        column.setPreferredWidth(50);
        column = tblHome.getColumnModel().getColumn(1); 
        column.setPreferredWidth(160); 
        column = tblHome.getColumnModel().getColumn(2); 
        column.setPreferredWidth(50); 
        column = tblHome.getColumnModel().getColumn(3); 
        column.setPreferredWidth(80); 
        column = tblHome.getColumnModel().getColumn(4); 
        column.setPreferredWidth(80);
        column = tblHome.getColumnModel().getColumn(5); 
        column.setPreferredWidth(150); 
        column = tblHome.getColumnModel().getColumn(6); 
        column.setPreferredWidth(200);
    }
    
    
    public void tampilData (int currentPage) {
        
        try {
            int offset = (currentPage - 1) * rowsPerPage;
            this.stat = k.getCon().prepareStatement("SELECT * FROM film LIMIT " + rowsPerPage + " OFFSET " + offset);
            this.rs = this.stat.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tblHome.getModel();
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);
            while(rs.next()) {
                Object[] data = {
                    rs.getString("id_film"),
                    rs.getString("judul"),
                    rs.getString("tahun"),
                    rs.getString("negara"),
                    rs.getString("genre"),
                    rs.getString("pemeran_utama"),
                    rs.getString("sinopsis"),
                    };
                    model.addRow(data);
            }
            tblHome.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void tampilUser (int currentPage) {
        
        try {
            int offset = (currentPage - 1) * rowsPerPage;
            this.stat = k.getCon().prepareStatement("SELECT * FROM user LIMIT " + rowsPerPage + " OFFSET " + offset);
            this.rs = this.stat.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tblUser.getModel();
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);
            while(rs.next()) {
                Object[] data = {
                    rs.getString("id_user"),
                    rs.getString("nama"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("role"),
                    };
                    model.addRow(data);
            }
            tblUser.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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

        bodyPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        genreFilm = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        home = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tambahFilm = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        editFilm = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        info = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        logout = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pengguna = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        scrollPaneWin111 = new raven.scroll.win11.ScrollPaneWin11();
        tblHome = new javax.swing.JTable();
        imageLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        tahunLabel = new javax.swing.JLabel();
        sinopsisiLabel = new javax.swing.JLabel();
        comboSort = new combobox.Combobox();
        txtCari = new textfield.TextField();
        jLabel23 = new javax.swing.JLabel();
        btnSort = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        rowsPerPageComboBox = new javax.swing.JComboBox<>();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        tambahPanel = new javax.swing.JPanel();
        txtID = new textfield.TextField();
        txtJudul = new textfield.TextField();
        txtNegara = new textfield.TextField();
        txtTahun = new textfield.TextField();
        txtGenre = new combobox.Combobox();
        txtPemeran = new textfield.TextField();
        txtSinopsis = new textarea.TextAreaScroll();
        txtSinopsis1 = new textarea.TextArea();
        poster = new javax.swing.JLabel();
        btnPoster = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnReset = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        infoPanel = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        infoLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        editPanel = new javax.swing.JPanel();
        txtID1 = new textfield.TextField();
        txtJudul1 = new textfield.TextField();
        txtNegara1 = new textfield.TextField();
        txtTahun1 = new textfield.TextField();
        txtGenre1 = new combobox.Combobox();
        txtPemeran1 = new textfield.TextField();
        txtSinopsis2 = new textarea.TextAreaScroll();
        txtSinopsis3 = new textarea.TextArea();
        poster1 = new javax.swing.JLabel();
        btnPoster1 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        btnReset1 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        penggunaPanel = new javax.swing.JPanel();
        scrollPaneWin113 = new raven.scroll.win11.ScrollPaneWin11();
        tblUser = new javax.swing.JTable();
        txtCariUser = new textfield.TextField();
        comboSortUser = new combobox.Combobox();
        btnSortUser = new javax.swing.JButton();
        btnFirstUser = new javax.swing.JButton();
        btnBackUser = new javax.swing.JButton();
        rowsPerPageComboBoxUser = new javax.swing.JComboBox<>();
        btnNextUser = new javax.swing.JButton();
        btnLastUser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CineGuide");

        bodyPanel.setBackground(new java.awt.Color(255, 255, 255));

        menuPanel.setBackground(new java.awt.Color(255, 165, 89));

        genreFilm.setBackground(new java.awt.Color(69, 69, 69));

        javax.swing.GroupLayout genreFilmLayout = new javax.swing.GroupLayout(genreFilm);
        genreFilm.setLayout(genreFilmLayout);
        genreFilmLayout.setHorizontalGroup(
            genreFilmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        genreFilmLayout.setVerticalGroup(
            genreFilmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Schadow BT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(69, 69, 69));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/logo (4).png"))); // NOI18N
        jLabel1.setText("CineGuide");

        home.setBackground(new java.awt.Color(255, 165, 89));
        home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                homeMousePressed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-home-30.png"))); // NOI18N
        jLabel2.setText("Home");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout homeLayout = new javax.swing.GroupLayout(home);
        home.setLayout(homeLayout);
        homeLayout.setHorizontalGroup(
            homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        homeLayout.setVerticalGroup(
            homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tambahFilm.setBackground(new java.awt.Color(255, 165, 89));
        tambahFilm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tambahFilmMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-add-30.png"))); // NOI18N
        jLabel3.setText("Tambah Film");

        javax.swing.GroupLayout tambahFilmLayout = new javax.swing.GroupLayout(tambahFilm);
        tambahFilm.setLayout(tambahFilmLayout);
        tambahFilmLayout.setHorizontalGroup(
            tambahFilmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambahFilmLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );
        tambahFilmLayout.setVerticalGroup(
            tambahFilmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahFilmLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        editFilm.setBackground(new java.awt.Color(255, 165, 89));
        editFilm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editFilmMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-edit-30.png"))); // NOI18N
        jLabel4.setText("Edit Film");

        javax.swing.GroupLayout editFilmLayout = new javax.swing.GroupLayout(editFilm);
        editFilm.setLayout(editFilmLayout);
        editFilmLayout.setHorizontalGroup(
            editFilmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editFilmLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        editFilmLayout.setVerticalGroup(
            editFilmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editFilmLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        info.setBackground(new java.awt.Color(255, 165, 89));
        info.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-info-30.png"))); // NOI18N
        jLabel5.setText("Info Aplikasi");

        javax.swing.GroupLayout infoLayout = new javax.swing.GroupLayout(info);
        info.setLayout(infoLayout);
        infoLayout.setHorizontalGroup(
            infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap())
        );
        infoLayout.setVerticalGroup(
            infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        logout.setBackground(new java.awt.Color(255, 165, 89));
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-logout-30.png"))); // NOI18N
        jLabel6.setText("Logout");

        javax.swing.GroupLayout logoutLayout = new javax.swing.GroupLayout(logout);
        logout.setLayout(logoutLayout);
        logoutLayout.setHorizontalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6)
        );
        logoutLayout.setVerticalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pengguna.setBackground(new java.awt.Color(255, 165, 89));
        pengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                penggunaMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-user-30 (1).png"))); // NOI18N
        jLabel11.setText("Pengguna");

        javax.swing.GroupLayout penggunaLayout = new javax.swing.GroupLayout(pengguna);
        pengguna.setLayout(penggunaLayout);
        penggunaLayout.setHorizontalGroup(
            penggunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(penggunaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        penggunaLayout.setVerticalGroup(
            penggunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, penggunaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(genreFilm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(61, 61, 61)
                .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tambahFilm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editFilm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editFilm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tambahFilm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pengguna, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(genreFilm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mainPanel.setLayout(new java.awt.CardLayout());

        homePanel.setBackground(new java.awt.Color(255, 255, 255));

        scrollPaneWin111.setBackground(new java.awt.Color(255, 255, 255));

        tblHome.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblHome.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Film", "Judul", "Tahun", "Negara", "Genre", "Pemeran", "Sinopsis"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHome.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblHome.setGridColor(new java.awt.Color(255, 255, 255));
        tblHome.setOpaque(false);
        tblHome.setRowHeight(50);
        tblHome.setSelectionBackground(new java.awt.Color(255, 165, 89));
        tblHome.getTableHeader().setResizingAllowed(false);
        tblHome.getTableHeader().setReorderingAllowed(false);
        tblHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHomeMouseClicked(evt);
            }
        });
        scrollPaneWin111.setViewportView(tblHome);
        if (tblHome.getColumnModel().getColumnCount() > 0) {
            tblHome.getColumnModel().getColumn(0).setResizable(false);
            tblHome.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblHome.getColumnModel().getColumn(1).setResizable(false);
            tblHome.getColumnModel().getColumn(1).setPreferredWidth(180);
            tblHome.getColumnModel().getColumn(2).setResizable(false);
            tblHome.getColumnModel().getColumn(2).setPreferredWidth(20);
            tblHome.getColumnModel().getColumn(3).setResizable(false);
            tblHome.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblHome.getColumnModel().getColumn(4).setResizable(false);
            tblHome.getColumnModel().getColumn(4).setPreferredWidth(50);
            tblHome.getColumnModel().getColumn(5).setResizable(false);
            tblHome.getColumnModel().getColumn(6).setResizable(false);
            tblHome.getColumnModel().getColumn(6).setPreferredWidth(200);
        }

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        tahunLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tahunLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        comboSort.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ID Film", "Judul", "Tahun", "Negara", "Genre", "Pemeran Utama" }));
        comboSort.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboSort.setLabeText("Urutkan");
        comboSort.setLineColor(new java.awt.Color(255, 96, 0));
        comboSort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboSortMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                comboSortMousePressed(evt);
            }
        });
        comboSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSortActionPerformed(evt);
            }
        });

        txtCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCari.setLabelText("Cari");
        txtCari.setLineColor(new java.awt.Color(255, 96, 0));
        txtCari.setSelectionColor(new java.awt.Color(255, 165, 89));
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 165, 89));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("[HOME]");

        btnSort.setBackground(new java.awt.Color(255, 255, 255));
        btnSort.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-sort-24.png"))); // NOI18N
        btnSort.setBorder(null);
        btnSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortActionPerformed(evt);
            }
        });

        btnFirst.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnFirst.setText("First");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-back-25.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        rowsPerPageComboBox.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rowsPerPageComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "20", "25", "50", "100" }));
        rowsPerPageComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsPerPageComboBoxActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-next-25.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLast.setText("Last");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addComponent(scrollPaneWin111, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(titleLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(imageLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tahunLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(sinopsisiLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 26, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                                .addComponent(comboSort, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))
                            .addComponent(btnSort, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(31, 31, 31)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(307, 307, 307)
                .addComponent(btnFirst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rowsPerPageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLast)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(homePanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(btnSort)))
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)))
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneWin111, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(titleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tahunLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sinopsisiLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rowsPerPageComboBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNext, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        mainPanel.add(homePanel, "card2");

        tambahPanel.setBackground(new java.awt.Color(255, 255, 255));
        tambahPanel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tambahPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tambahPanelMouseClicked(evt);
            }
        });

        txtID.setEnabled(false);
        txtID.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtID.setLabelText("ID Film");
        txtID.setLineColor(new java.awt.Color(255, 96, 0));
        txtID.setSelectionColor(new java.awt.Color(255, 165, 89));
        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        txtJudul.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtJudul.setLabelText("Judul");
        txtJudul.setLineColor(new java.awt.Color(255, 96, 0));
        txtJudul.setSelectionColor(new java.awt.Color(255, 165, 89));
        txtJudul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJudulKeyTyped(evt);
            }
        });

        txtNegara.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNegara.setLabelText("Negara");
        txtNegara.setLineColor(new java.awt.Color(255, 96, 0));
        txtNegara.setSelectionColor(new java.awt.Color(255, 165, 89));

        txtTahun.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTahun.setLabelText("Tahun");
        txtTahun.setLineColor(new java.awt.Color(255, 96, 0));
        txtTahun.setSelectionColor(new java.awt.Color(255, 165, 89));
        txtTahun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTahunKeyTyped(evt);
            }
        });

        txtGenre.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Action", "Adventure", "Animation", "Biography", "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy", "Horror", "Romance", "Sci-Fi", "Thriller" }));
        txtGenre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtGenre.setLabeText("Genre");
        txtGenre.setLineColor(new java.awt.Color(255, 96, 0));

        txtPemeran.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPemeran.setLabelText("Pemeran");
        txtPemeran.setLineColor(new java.awt.Color(255, 96, 0));
        txtPemeran.setSelectionColor(new java.awt.Color(255, 165, 89));

        txtSinopsis.setBackground(new java.awt.Color(255, 255, 255));
        txtSinopsis.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSinopsis.setLabelText("Sinopsis");
        txtSinopsis.setLineColor(new java.awt.Color(255, 96, 0));

        txtSinopsis1.setColumns(20);
        txtSinopsis1.setRows(5);
        txtSinopsis1.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        txtSinopsis1.setSelectionColor(new java.awt.Color(255, 165, 89));
        txtSinopsis.setViewportView(txtSinopsis1);

        poster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnPoster.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPoster.setText("PILIH POSTER");
        btnPoster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPosterActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 165, 89));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("[TAMBAH FILM]");

        btnReset.setBackground(new java.awt.Color(69, 69, 69));
        btnReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnResetMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnResetMouseExited(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-reset-20.png"))); // NOI18N
        jLabel18.setText("RESET");

        javax.swing.GroupLayout btnResetLayout = new javax.swing.GroupLayout(btnReset);
        btnReset.setLayout(btnResetLayout);
        btnResetLayout.setHorizontalGroup(
            btnResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnResetLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        btnResetLayout.setVerticalGroup(
            btnResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnResetLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnTambah.setBackground(new java.awt.Color(255, 96, 0));
        btnTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTambahMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTambahMouseExited(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-add-20.png"))); // NOI18N
        jLabel17.setText("TAMBAH");

        javax.swing.GroupLayout btnTambahLayout = new javax.swing.GroupLayout(btnTambah);
        btnTambah.setLayout(btnTambahLayout);
        btnTambahLayout.setHorizontalGroup(
            btnTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnTambahLayout.setVerticalGroup(
            btnTambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnTambahLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout tambahPanelLayout = new javax.swing.GroupLayout(tambahPanel);
        tambahPanel.setLayout(tambahPanelLayout);
        tambahPanelLayout.setHorizontalGroup(
            tambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambahPanelLayout.createSequentialGroup()
                .addGroup(tambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tambahPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tambahPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(tambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtJudul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNegara, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(79, 79, 79)
                        .addGroup(tambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPemeran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSinopsis, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addComponent(txtGenre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addGroup(tambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnPoster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(poster, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31))
            .addGroup(tambahPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tambahPanelLayout.setVerticalGroup(
            tambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel10)
                .addGap(35, 35, 35)
                .addGroup(tambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tambahPanelLayout.createSequentialGroup()
                        .addGroup(tambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(tambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPemeran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(tambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSinopsis, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambahPanelLayout.createSequentialGroup()
                                .addComponent(txtTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(txtNegara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(tambahPanelLayout.createSequentialGroup()
                        .addComponent(btnPoster, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(poster, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43)
                .addGroup(tambahPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );

        mainPanel.add(tambahPanel, "card3");

        infoPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 165, 89));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("[INFO APLIKASI]");

        jLabel7.setFont(new java.awt.Font("Schadow BT", 1, 36)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/logo (2).png"))); // NOI18N
        jLabel7.setText("CineGuide");

        infoLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        infoLabel.setText("infoLabel");

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel8.setText("Version 1.2");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Copyright © CineGuide 2023");

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(infoPanelLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(0, 729, Short.MAX_VALUE))
                            .addGroup(infoPanelLayout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)))
                        .addContainerGap())
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(infoLabel))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 331, Short.MAX_VALUE)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addContainerGap())
        );

        mainPanel.add(infoPanel, "card5");

        editPanel.setBackground(new java.awt.Color(255, 255, 255));
        editPanel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtID1.setEnabled(true);
        txtID1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtID1.setLabelText("ID Film");
        txtID1.setLineColor(new java.awt.Color(255, 96, 0));
        txtID1.setSelectionColor(new java.awt.Color(255, 165, 89));
        txtID1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtID1CaretUpdate(evt);
            }
        });
        txtID1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtID1ActionPerformed(evt);
            }
        });

        txtJudul1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtJudul1.setLabelText("Judul");
        txtJudul1.setLineColor(new java.awt.Color(255, 96, 0));
        txtJudul1.setSelectionColor(new java.awt.Color(255, 165, 89));

        txtNegara1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNegara1.setLabelText("Negara");
        txtNegara1.setLineColor(new java.awt.Color(255, 96, 0));
        txtNegara1.setSelectionColor(new java.awt.Color(255, 165, 89));

        txtTahun1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTahun1.setLabelText("Tahun");
        txtTahun1.setLineColor(new java.awt.Color(255, 96, 0));
        txtTahun1.setSelectionColor(new java.awt.Color(255, 165, 89));
        txtTahun1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTahun1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTahun1KeyTyped(evt);
            }
        });

        txtGenre1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Action", "Adventure", "Animation", "Biography", "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy", "Horror", "Romance", "Sci-Fi", "Thriller" }));
        txtGenre1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtGenre1.setLabeText("Genre");
        txtGenre1.setLineColor(new java.awt.Color(255, 96, 0));

        txtPemeran1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPemeran1.setLabelText("Pemeran");
        txtPemeran1.setLineColor(new java.awt.Color(255, 96, 0));
        txtPemeran1.setSelectionColor(new java.awt.Color(255, 165, 89));

        txtSinopsis2.setBackground(new java.awt.Color(255, 255, 255));
        txtSinopsis2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSinopsis2.setLabelText("Sinopsis");
        txtSinopsis2.setLineColor(new java.awt.Color(255, 96, 0));

        txtSinopsis3.setColumns(20);
        txtSinopsis3.setRows(5);
        txtSinopsis3.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        txtSinopsis3.setSelectionColor(new java.awt.Color(255, 165, 89));
        txtSinopsis2.setViewportView(txtSinopsis3);

        poster1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnPoster1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPoster1.setText("PILIH POSTER");
        btnPoster1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoster1ActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 165, 89));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("[EDIT FILM]");

        btnReset1.setBackground(new java.awt.Color(69, 69, 69));
        btnReset1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReset1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReset1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReset1MouseExited(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-reset-20.png"))); // NOI18N
        jLabel19.setText("RESET");

        javax.swing.GroupLayout btnReset1Layout = new javax.swing.GroupLayout(btnReset1);
        btnReset1.setLayout(btnReset1Layout);
        btnReset1Layout.setHorizontalGroup(
            btnReset1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnReset1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        btnReset1Layout.setVerticalGroup(
            btnReset1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnReset1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnEdit.setBackground(new java.awt.Color(255, 96, 0));
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditMouseExited(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-edit-20.png"))); // NOI18N
        jLabel20.setText("EDIT");

        javax.swing.GroupLayout btnEditLayout = new javax.swing.GroupLayout(btnEdit);
        btnEdit.setLayout(btnEditLayout);
        btnEditLayout.setHorizontalGroup(
            btnEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnEditLayout.setVerticalGroup(
            btnEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnDelete.setBackground(new java.awt.Color(237, 43, 42));
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDeleteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDeleteMouseExited(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-delete-20 (1).png"))); // NOI18N
        jLabel21.setText("DELETE");

        javax.swing.GroupLayout btnDeleteLayout = new javax.swing.GroupLayout(btnDelete);
        btnDelete.setLayout(btnDeleteLayout);
        btnDeleteLayout.setHorizontalGroup(
            btnDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnDeleteLayout.setVerticalGroup(
            btnDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout editPanelLayout = new javax.swing.GroupLayout(editPanel);
        editPanel.setLayout(editPanelLayout);
        editPanelLayout.setHorizontalGroup(
            editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editPanelLayout.createSequentialGroup()
                .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(editPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReset1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(editPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtJudul1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNegara1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTahun1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(79, 79, 79)
                        .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPemeran1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSinopsis2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addComponent(txtGenre1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnPoster1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(poster1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31))
        );
        editPanelLayout.setVerticalGroup(
            editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel16)
                .addGap(35, 35, 35)
                .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editPanelLayout.createSequentialGroup()
                        .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGenre1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPemeran1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJudul1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSinopsis2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editPanelLayout.createSequentialGroup()
                                .addComponent(txtTahun1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(txtNegara1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(43, 43, 43)
                        .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnReset1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(editPanelLayout.createSequentialGroup()
                        .addComponent(btnPoster1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(poster1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43))
        );

        mainPanel.add(editPanel, "card3");

        penggunaPanel.setBackground(new java.awt.Color(255, 255, 255));

        scrollPaneWin113.setBackground(new java.awt.Color(255, 255, 255));

        tblUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID User", "Nama", "Username", "Email", "Role"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUser.setGridColor(new java.awt.Color(255, 255, 255));
        tblUser.setSelectionBackground(new java.awt.Color(255, 165, 89));
        scrollPaneWin113.setViewportView(tblUser);
        if (tblUser.getColumnModel().getColumnCount() > 0) {
            tblUser.getColumnModel().getColumn(0).setResizable(false);
            tblUser.getColumnModel().getColumn(1).setResizable(false);
            tblUser.getColumnModel().getColumn(2).setResizable(false);
            tblUser.getColumnModel().getColumn(3).setResizable(false);
            tblUser.getColumnModel().getColumn(4).setResizable(false);
        }

        txtCariUser.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCariUser.setLabelText("Cari");
        txtCariUser.setLineColor(new java.awt.Color(255, 96, 0));
        txtCariUser.setSelectionColor(new java.awt.Color(255, 165, 89));
        txtCariUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariUserKeyReleased(evt);
            }
        });

        comboSortUser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ID User", "Nama", "Username", "Email", "Role" }));
        comboSortUser.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboSortUser.setLabeText("Urutkan");
        comboSortUser.setLineColor(new java.awt.Color(255, 96, 0));
        comboSortUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboSortUserMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                comboSortUserMousePressed(evt);
            }
        });
        comboSortUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSortUserActionPerformed(evt);
            }
        });

        btnSortUser.setBackground(new java.awt.Color(255, 255, 255));
        btnSortUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-sort-24.png"))); // NOI18N
        btnSortUser.setBorder(null);
        btnSortUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortUserActionPerformed(evt);
            }
        });

        btnFirstUser.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnFirstUser.setText("First");
        btnFirstUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstUserActionPerformed(evt);
            }
        });

        btnBackUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-back-25.png"))); // NOI18N
        btnBackUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackUserActionPerformed(evt);
            }
        });

        rowsPerPageComboBoxUser.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rowsPerPageComboBoxUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "20", "25", "50", "100" }));
        rowsPerPageComboBoxUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rowsPerPageComboBoxUserActionPerformed(evt);
            }
        });

        btnNextUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-next-25.png"))); // NOI18N
        btnNextUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextUserActionPerformed(evt);
            }
        });

        btnLastUser.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLastUser.setText("Last");
        btnLastUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout penggunaPanelLayout = new javax.swing.GroupLayout(penggunaPanel);
        penggunaPanel.setLayout(penggunaPanelLayout);
        penggunaPanelLayout.setHorizontalGroup(
            penggunaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(penggunaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(penggunaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, penggunaPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(penggunaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, penggunaPanelLayout.createSequentialGroup()
                                .addComponent(comboSortUser, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))
                            .addComponent(btnSortUser, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addComponent(txtCariUser, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPaneWin113, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(penggunaPanelLayout.createSequentialGroup()
                .addGap(424, 424, 424)
                .addComponent(btnFirstUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBackUser, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rowsPerPageComboBoxUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNextUser, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLastUser)
                .addContainerGap(424, Short.MAX_VALUE))
        );
        penggunaPanelLayout.setVerticalGroup(
            penggunaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(penggunaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(penggunaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCariUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(penggunaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboSortUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(penggunaPanelLayout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addComponent(btnSortUser))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPaneWin113, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(penggunaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rowsPerPageComboBoxUser, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBackUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnFirstUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLastUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNextUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        mainPanel.add(penggunaPanel, "card6");

        javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        bodyPanelLayout.setVerticalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        Masuk masuk = new Masuk();
        masuk.setVisible(true);
    }//GEN-LAST:event_logoutMouseClicked

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseEntered

    private void tambahFilmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tambahFilmMouseClicked
        try{
            Connection con = k.getCon();
            Statement stat = con.createStatement();
            String sql = "SELECT MAX(RIGHT(id_film, 3)) FROM film";
            ResultSet rs = stat.executeQuery(sql);
            String noAuto, zeroPlus;
            zeroPlus = "";
            int p;
            if(rs.next()){
                noAuto = Integer.toString(rs.getInt(1) + 1);
                p = noAuto.length();
                for(int i = 1; i <= 3 - p; i++){
                    zeroPlus += "0";
                }
                txtID.setText("F-" + zeroPlus + noAuto);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        //remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        //add panel
        mainPanel.add(tambahPanel);
        mainPanel.repaint();
        mainPanel.revalidate();

    }//GEN-LAST:event_tambahFilmMouseClicked

    private void editFilmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editFilmMouseClicked
        // TODO add your handling code here:
                //remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        //add panel
        mainPanel.add(editPanel);
        mainPanel.repaint();
        mainPanel.revalidate();

    }//GEN-LAST:event_editFilmMouseClicked

    private void infoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoMouseClicked
        // TODO add your handling code here:
                //remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        //add panel
        mainPanel.add(infoPanel);
        mainPanel.repaint();
        mainPanel.revalidate();

    }//GEN-LAST:event_infoMouseClicked

    private void homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMouseClicked
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        mainPanel.add(homePanel);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_homeMouseClicked

    private void homeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_homeMousePressed

    private void btnPosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPosterActionPerformed
        // TODO add your handling code here:
        tambahfilm a = new tambahfilm();
        if (a.judul.equals("") || a.tahun.equals("") || a.negara.equals("") || a.genre.equals("") || a.pemeran.equals("") || a.sinopsis.equals("") ) {
            JOptionPane.showMessageDialog(null, "Isi Semua Data Terlebih Dahulu Sebelum Memilih Poster!");
        }else{
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(f.toString());
                Image img = icon.getImage().getScaledInstance(poster.getWidth(),
                    poster.getHeight(), Image.SCALE_DEFAULT);
                ImageIcon ic = new ImageIcon(img);
                poster.setIcon(ic);
                this.filename = f.getAbsolutePath();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
//        JFileChooser chooser = new JFileChooser();
//        chooser.showOpenDialog(null);
//        File f = chooser.getSelectedFile();
//        String path = f.getAbsolutePath();
//        try {
//            BufferedImage bi = ImageIO.read(new File(path));
//            Image img = bi.getScaledInstance(poster.getWidth(), poster.getHeight(), Image.SCALE_SMOOTH);
//            ImageIcon icon = new ImageIcon(img);
//            poster.setIcon(icon);
//            path2=path;
//        } catch (IOException ex) {
//            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_btnPosterActionPerformed

    private void btnTambahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseEntered
        // TODO add your handling code here:
        btnTambah.setBackground(panDefault);
    }//GEN-LAST:event_btnTambahMouseEntered

    private void btnTambahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseExited
        // TODO add your handling code here:
        btnTambah.setBackground(panEnter);
    }//GEN-LAST:event_btnTambahMouseExited

    private void btnResetMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseEntered
        // TODO add your handling code here:
        btnReset.setBackground(panEnter);
    }//GEN-LAST:event_btnResetMouseEntered

    private void btnResetMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseExited
        // TODO add your handling code here:
        btnReset.setBackground(panDefault);
    }//GEN-LAST:event_btnResetMouseExited

    private void btnResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseClicked
        // TODO add your handling code here:
        txtJudul.setText("");
        txtNegara.setText("");
        txtTahun.setText("");
        txtGenre.setSelectedItem("");
        txtPemeran.setText("");
        txtSinopsis1.setText("");
        poster.setText("");
        
    }//GEN-LAST:event_btnResetMouseClicked
    
    private void btnTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseClicked
        try{
            Connection con = k.getCon();
            Statement stat = con.createStatement();
            String sql = "SELECT MAX(RIGHT(id_film, 3)) FROM film";
            ResultSet rs = stat.executeQuery(sql);
            String noAuto, zeroPlus;
            zeroPlus = "";
            int p;
            if(rs.next()){
                noAuto = Integer.toString(rs.getInt(1) + 1);
                p = noAuto.length();
                for(int i = 1; i <= 3 - p; i++){
                    zeroPlus += "0";
                }
                txtID.setText("F-" + zeroPlus + noAuto);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        try {
            if(!Pattern.matches("^([0-9]{4,4})+$", txtTahun.getText())){
                JOptionPane.showMessageDialog(this, "Format Tahun Tidak Sesuai!");
            }else if(!Pattern.matches("^[a-zA-Z ]+$", txtNegara.getText())) {
                JOptionPane.showMessageDialog(this, "Format Penulisan Negara Hanya Boleh Huruf!");
            }else if(!Pattern.matches("^([a-zA-Z., ])+$", txtPemeran.getText())) {
                JOptionPane.showMessageDialog(this, "Format Penulisan Pemeran Tidak Sesuai!");
            }else {
                String newpath = "src/upload";
                File directory = new File(newpath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                Random rnd = new Random();
                int number = rnd.nextInt(999);
                File fileawal = null;
                File fileakhir = null;
                String ext = this.filename.substring(filename.lastIndexOf('.') + 1);
                fileawal = new File(filename);
                fileakhir = new File(newpath + "/" + txtID.getText() + "-" + number + "." + ext);
                System.out.println(fileakhir);
                tambahfilm tambah = new tambahfilm();
                Connection conn = k.getCon();
                PreparedStatement stat = conn.prepareStatement("INSERT INTO film VALUES(?,?,?,?,?,?,?,?)");
                stat.setString(1, txtID.getText());
                stat.setString(2, tambah.judul);
                stat.setString(3, tambah.tahun);
                stat.setString(4, tambah.negara);
                stat.setString(5, tambah.genre);
                stat.setString(6, tambah.pemeran);
                stat.setString(7, tambah.sinopsis);
                stat.setString(8, fileakhir.toString());
                stat.executeUpdate();
                txtID.setText("");
                txtJudul.setText("");
                txtTahun.setText("");
                txtNegara.setText("");
                txtGenre.setSelectedItem("");
                txtPemeran.setText("");
                txtSinopsis1.setText("");
                poster.setIcon(null);
                Files.copy(fileawal.toPath(), fileakhir.toPath());
                JOptionPane.showMessageDialog(null, "Data Film Berhasil Ditambah");

                stat.close();

                mainPanel.removeAll();
                mainPanel.repaint();
                mainPanel.revalidate();
                mainPanel.add(homePanel);
                mainPanel.repaint();
                mainPanel.revalidate();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        try{
            Connection conn = k.getCon();
            Statement state = conn.createStatement();
            String sql = "SELECT id_film, judul, tahun, negara, genre, pemeran_utama, sinopsis FROM film ORDER BY id_film ASC";
            ResultSet res = state.executeQuery(sql);
            
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Film");
            model.addColumn("Judul");
            model.addColumn("Tahun");
            model.addColumn("Negara");
            model.addColumn("Genre");
            model.addColumn("Pemeran Utama");
            model.addColumn("Sinopsis");
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);
            refreshTable();
        }catch(SQLException s){
            JOptionPane.showMessageDialog(null, s);
        }
    }//GEN-LAST:event_btnTambahMouseClicked

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void tblHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHomeMouseClicked
        int selectedRow = tblHome.getSelectedRow();
        String selectedValue = tblHome.getValueAt(selectedRow, 0).toString();
        try{
            Connection conn = k.getCon();
            Statement state = conn.createStatement();
            String sql = "SELECT * FROM film WHERE id_film = '" + selectedValue + "'";
            ResultSet r = state.executeQuery(sql);
            while(r.next()){
                ImageIcon filmPoster = new ImageIcon(r.getString("poster"));
                Image scalingImage = filmPoster.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_DEFAULT);
                ImageIcon scaledFilmPoster = new ImageIcon(scalingImage);
                imageLabel.setIcon(scaledFilmPoster);
                titleLabel.setText(r.getString("judul"));
                tahunLabel.setText(r.getString("tahun"));
                String sinopsis = r.getString("sinopsis");
                String snp = "<html><body style='width: 180px;text-align:justify;'>" + sinopsis + "</body></html>";
                sinopsisiLabel.setText(snp);
            }
        }catch(SQLException s){
            JOptionPane.showMessageDialog(null, s);
        }
    }//GEN-LAST:event_tblHomeMouseClicked

    private void txtID1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtID1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtID1ActionPerformed
    
    private void btnPoster1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoster1ActionPerformed
        editfilm a = new editfilm();
        if (a.judul.equals("") || a.tahun.equals("") || a.negara.equals("") || a.genre.equals("") || a.pemeran.equals("") || a.sinopsis.equals("") ) {
            JOptionPane.showMessageDialog(null, "Isi Semua Data Terlebih Dahulu Sebelum Memilih Foto!");
        }else{
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(f.toString());
                Image img = icon.getImage().getScaledInstance(poster1.getWidth(),
                    poster1.getHeight(), Image.SCALE_DEFAULT);
                ImageIcon ic = new ImageIcon(img);
                poster1.setIcon(ic);
                this.filename = f.getAbsolutePath();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnPoster1ActionPerformed

    private void btnReset1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReset1MouseClicked
        // TODO add your handling code here:
        txtID1.setText("");
        txtJudul1.setText("");
        txtTahun1.setText("");
        txtNegara1.setText("");
        txtGenre1.setSelectedItem("");
        txtPemeran1.setText("");
        txtSinopsis3.setText("");
        poster1.setText("");
    }//GEN-LAST:event_btnReset1MouseClicked

    private void btnReset1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReset1MouseEntered
        // TODO add your handling code here:
        btnReset1.setBackground(panEnter);
    }//GEN-LAST:event_btnReset1MouseEntered

    private void btnReset1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReset1MouseExited
        // TODO add your handling code here:
        btnReset1.setBackground(panDefault);
    }//GEN-LAST:event_btnReset1MouseExited

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        // TODO add your handling code here:
        try {
            if(!Pattern.matches("^([0-9]{4,4})+$", txtTahun1.getText())){
                JOptionPane.showMessageDialog(this, "Format Tahun Tidak Sesuai!");
            }else if(!Pattern.matches("^[a-zA-Z ]+$", txtNegara1.getText())) {
                JOptionPane.showMessageDialog(this, "Format Penulisan Negara Hanya Boleh Huruf!");
            }else if(!Pattern.matches("^([a-zA-Z., ])+$", txtPemeran1.getText())) {
                JOptionPane.showMessageDialog(this, "Format Penulisan Pemeran Tidak Sesuai!");
            }else{
                String newpath = "src/upload";
                File directory = new File(newpath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                Random rnd = new Random();
                int number = rnd.nextInt(999);
                File fileawal = null;
                File fileakhir = null;
                String ext = this.filename.substring(filename.lastIndexOf('.') + 1);
                fileawal = new File(filename);
                fileakhir = new File(newpath + "/" + txtID1.getText() + "-" + number + "." + ext);
                System.out.println(fileakhir);
                editfilm edit = new editfilm();
                this.stat = k.getCon().prepareStatement("UPDATE `cineguide`.`film` SET `judul` = ?, `tahun` = ?, `negara` = ?, `genre` = ?, `pemeran_utama` = ?, `sinopsis` = ?, `poster` = ? WHERE id_film = ?");
                stat.setString(1, edit.judul);
                stat.setString(2, edit.tahun);
                stat.setString(3, edit.negara);
                stat.setString(4, edit.genre);
                stat.setString(5, edit.pemeran);
                stat.setString(6, edit.sinopsis);
                stat.setString(7, fileakhir.toString());
                stat.setString(8, edit.id);
                stat.executeUpdate();
                txtID1.setText("");
                txtJudul1.setText("");
                txtTahun1.setText("");
                txtNegara1.setText("");
                txtGenre1.setSelectedItem("");
                txtPemeran1.setText("");
                txtSinopsis3.setText("");
                poster1.setIcon(null);
                Files.copy(fileawal.toPath(), fileakhir.toPath());

                JOptionPane.showMessageDialog(null, "Data Film Berhasil Diedit");
                mainPanel.removeAll();
                mainPanel.repaint();
                mainPanel.revalidate();
                mainPanel.add(homePanel);
                mainPanel.repaint();
                mainPanel.revalidate();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        try{
            Connection conn = k.getCon();
            Statement state = conn.createStatement();
            String sql = "SELECT id_film, judul, tahun, negara, genre, pemeran_utama, sinopsis FROM film ORDER BY id_film ASC";
            
            ResultSet res = state.executeQuery(sql);
            
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Film");
            model.addColumn("Judul");
            model.addColumn("Tahun");
            model.addColumn("Negara");
            model.addColumn("Genre");
            model.addColumn("Pemeran Utama");
            model.addColumn("Sinopsis");
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);
            refreshTable();
        }catch(SQLException s){
            JOptionPane.showMessageDialog(null, s);
        }
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseEntered
        // TODO add your handling code here:
        btnEdit.setBackground(panDefault);
    }//GEN-LAST:event_btnEditMouseEntered

    private void btnEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseExited
        // TODO add your handling code here:
        btnEdit.setBackground(panEnter);
    }//GEN-LAST:event_btnEditMouseExited

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked
        int opsi = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus film?", "Hapus", JOptionPane.YES_NO_OPTION);
        if(opsi==0) {
        try{
            Connection conn = k.getCon();
            PreparedStatement p = conn.prepareStatement("DELETE FROM film WHERE id_film = ?");
            p.setString(1, txtID1.getText());
            p.executeUpdate();
            p.close();
            txtID1.setText("");
            txtJudul1.setText("");
            txtTahun1.setText("");
            txtNegara1.setText("");
            txtGenre1.setSelectedItem("");
            txtPemeran1.setText("");
            txtSinopsis3.setText("");
            poster1.setIcon(null);
            
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
            mainPanel.removeAll();
            mainPanel.repaint();
            mainPanel.revalidate();
            mainPanel.add(homePanel);
            mainPanel.repaint();
            mainPanel.revalidate();
        }catch(SQLException s){
            JOptionPane.showMessageDialog(null, s);
        }
        }
        try{
            Connection conn = k.getCon();
            Statement state = conn.createStatement();
            String sql = "SELECT id_film, judul, tahun, negara, genre, pemeran_utama, sinopsis FROM film ORDER BY id_film ASC";
            ResultSet res = state.executeQuery(sql);
            
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Film");
            model.addColumn("Judul");
            model.addColumn("Tahun");
            model.addColumn("Negara");
            model.addColumn("Genre");
            model.addColumn("Pemeran Utama");
            model.addColumn("Sinopsis");
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);
            refreshTable();
        }catch(SQLException s){
            JOptionPane.showMessageDialog(null, s);
        }

    }//GEN-LAST:event_btnDeleteMouseClicked

    private void btnDeleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseEntered
        // TODO add your handling code here:
        btnDelete.setBackground(panDefault);
    }//GEN-LAST:event_btnDeleteMouseEntered

    private void btnDeleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseExited
        // TODO add your handling code here:
        btnDelete.setBackground(panDelete);
    }//GEN-LAST:event_btnDeleteMouseExited

    private void txtID1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtID1CaretUpdate
        try{
            Connection conn = k.getCon();
            Statement state = conn.createStatement();
            String sql = "SELECT * FROM film WHERE id_film = '" + this.txtID1.getText() + "'";
            ResultSet res = state.executeQuery(sql);
            while(res.next()){
                txtJudul1.setText(res.getString("judul"));
                txtGenre1.setSelectedItem(res.getString("genre"));
                txtPemeran1.setText(res.getString("pemeran_utama"));
                txtTahun1.setText(res.getString("tahun"));
                txtNegara1.setText(res.getString("negara"));
                txtSinopsis3.setText(res.getString("sinopsis"));
                this.filename = res.getString("poster");
                ImageIcon icon = new ImageIcon(res.getString("poster"));
                Image img = icon.getImage().getScaledInstance(poster1.getWidth(),
                    poster1.getHeight(), Image.SCALE_DEFAULT);
                ImageIcon ic = new ImageIcon(img);
                poster1.setIcon(ic);
            }
        }catch(SQLException  s){
        }
    }//GEN-LAST:event_txtID1CaretUpdate

    private void tambahPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tambahPanelMouseClicked
        
    }//GEN-LAST:event_tambahPanelMouseClicked

    private void txtJudulKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJudulKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtJudulKeyTyped

    private void txtTahunKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTahunKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!((c >= '0')&&(c <= '9')&& txtTahun.getText().length() < 4 || (c == KeyEvent.VK_BACKSPACE) || (c == KeyEvent.VK_DELETE))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtTahunKeyTyped

    private void txtTahun1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTahun1KeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtTahun1KeyReleased

    private void txtTahun1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTahun1KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!((c >= '0')&&(c <= '9')&& txtTahun1.getText().length() < 4 || (c == KeyEvent.VK_BACKSPACE) || (c == KeyEvent.VK_DELETE))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtTahun1KeyTyped

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
         String key=txtCari.getText();
         System.out.println(key);
         if(key!=""){
            cariData(key);
        } else {
            tampilData(currentPage);
        }
    }//GEN-LAST:event_txtCariKeyReleased

    private void comboSortMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboSortMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_comboSortMouseClicked

    private void comboSortMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboSortMousePressed
        
    }//GEN-LAST:event_comboSortMousePressed

    private void comboSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSortActionPerformed
        // TODO add your handling code here:
        String selectedColumn = comboSort.getSelectedItem().toString();
        sortData(selectedColumn);
    }//GEN-LAST:event_comboSortActionPerformed

    private void btnSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortActionPerformed
        // TODO add your handling code here:
        String selectedColumn = comboSort.getSelectedItem().toString();
        sortData(selectedColumn);
    }//GEN-LAST:event_btnSortActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        currentPage = 1; // Set currentPage ke halaman pertama
        tampilData(currentPage); // Tampilkan data halaman pertama
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
         int totalRows = getTotalRows();
        int totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        currentPage = totalPages; // Set currentPage ke halaman terakhir
        tampilData(currentPage); // Tampilkan data halaman terakhir
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
         if (currentPage > 1) {
        currentPage--;
        tampilData(currentPage);
    }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        int totalRows = getTotalRows();
        int totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        if (currentPage < totalPages) {
            currentPage++;
            tampilData(currentPage);
    }
    }//GEN-LAST:event_btnNextActionPerformed

    private void rowsPerPageComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rowsPerPageComboBoxActionPerformed
        // TODO add your handling code here:
        // Perbarui nilai rowsPerPage sesuai dengan nilai yang dipilih oleh pengguna
        String selectedValue = rowsPerPageComboBox.getSelectedItem().toString();
        rowsPerPage = Integer.parseInt(selectedValue);

        // Tampilkan data ulang dengan currentPage saat ini
        tampilData(currentPage);
    }//GEN-LAST:event_rowsPerPageComboBoxActionPerformed

    private void txtCariUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariUserKeyReleased
        // TODO add your handling code here:
        String key=txtCariUser.getText();
         System.out.println(key);
         if(key!=""){
            cariDataUser(key);
        } else {
            tampilUser(currentPage);
        }
        
    }//GEN-LAST:event_txtCariUserKeyReleased

    private void comboSortUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboSortUserMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSortUserMouseClicked

    private void comboSortUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboSortUserMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSortUserMousePressed

    private void comboSortUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSortUserActionPerformed
        // TODO add your handling code here:
        String selectedColumn = comboSortUser.getSelectedItem().toString();
        sortDataUser(selectedColumn);
    }//GEN-LAST:event_comboSortUserActionPerformed

    private void btnSortUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortUserActionPerformed
        // TODO add your handling code here:
        String selectedColumn = comboSortUser.getSelectedItem().toString();
        sortDataUser(selectedColumn);
    }//GEN-LAST:event_btnSortUserActionPerformed

    private void btnFirstUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstUserActionPerformed
        // TODO add your handling code here:
        currentPage = 1; // Set currentPage ke halaman pertama
        tampilUser(currentPage); // Tampilkan data halaman pertama
    }//GEN-LAST:event_btnFirstUserActionPerformed

    private void btnBackUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackUserActionPerformed
        // TODO add your handling code here:
        if (currentPage > 1) {
        currentPage--;
        tampilUser(currentPage);
        }
    }//GEN-LAST:event_btnBackUserActionPerformed

    private void rowsPerPageComboBoxUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rowsPerPageComboBoxUserActionPerformed
        // TODO add your handling code here:
        // Perbarui nilai rowsPerPage sesuai dengan nilai yang dipilih oleh pengguna
        String selectedValue = rowsPerPageComboBoxUser.getSelectedItem().toString();
        rowsPerPage = Integer.parseInt(selectedValue);

        // Tampilkan data ulang dengan currentPage saat ini
        tampilUser(currentPage);
    }//GEN-LAST:event_rowsPerPageComboBoxUserActionPerformed

    private void btnNextUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextUserActionPerformed
        // TODO add your handling code here:
        int totalRows = getTotalRowsUser();
        int totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        if (currentPage < totalPages) {
            currentPage++;
            tampilUser(currentPage);
    }
    }//GEN-LAST:event_btnNextUserActionPerformed

    private void btnLastUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastUserActionPerformed
        // TODO add your handling code here:
        int totalRows = getTotalRowsUser();
        int totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        currentPage = totalPages; // Set currentPage ke halaman terakhir
        tampilUser(currentPage); // Tampilkan data halaman terakhir
    }//GEN-LAST:event_btnLastUserActionPerformed

    private void penggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_penggunaMouseClicked
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        //add panel
        mainPanel.add(penggunaPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_penggunaMouseClicked
    private void cariData(String key){
        try{
            
            Connection conn= k.getCon();
            Statement st=conn.createStatement();
            DefaultTableModel model = (DefaultTableModel) tblHome.getModel();
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);
            
            ResultSet RsProduk = st.executeQuery("SELECT * from film WHERE id_film LIKE '%"+key+"%' "
                                                + "OR judul LIKE '%"+key+"%' OR tahun LIKE '%"+key+"%'OR negara LIKE '%"+key+"%'OR genre LIKE '%"+key+"%'OR pemeran_utama LIKE '%"+key+"%'OR sinopsis LIKE '%"+key+"%'");  
            while(RsProduk.next()){
                Object[] data={
                    RsProduk.getString("id_film"),
                    RsProduk.getString("judul"),
                    RsProduk.getString("tahun"),
                    RsProduk.getString("negara"),
                    RsProduk.getString("genre"),
                    RsProduk.getString("pemeran_utama"),
                    RsProduk.getString("sinopsis")
                };
               model.addRow(data);
            }                
        } catch (Exception ex) {
        System.err.println(ex.getMessage());
        }
    }
    private void cariDataUser(String key){
        try{
            
            Connection conn= k.getCon();
            Statement st=conn.createStatement();
            DefaultTableModel model = (DefaultTableModel) tblUser.getModel();
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);
            
            ResultSet RsProduk = st.executeQuery("SELECT * from user WHERE id_user LIKE '%"+key+"%' "
                                                + "OR nama LIKE '%"+key+"%' OR username LIKE '%"+key+"%'OR email LIKE '%"+key+"%'OR role LIKE '%"+key+"%'");  
            while(RsProduk.next()){
                Object[] data={
                    RsProduk.getString("id_user"),
                    RsProduk.getString("nama"),
                    RsProduk.getString("username"),
                    RsProduk.getString("email"),
                    RsProduk.getString("role")
                };
               model.addRow(data);
            }                
        } catch (Exception ex) {
        System.err.println(ex.getMessage());
        }
    }
    
    private void sortData(String headerTabel) {
        // Dapatkan nama kolom database yang sesuai berdasarkan pemetaan
        String kolomDatabase = columnMapping.get(headerTabel);

        // Periksa apakah kolom yang dipilih sama dengan kolom sorting saat ini
        // Jika sama, ubah arah sorting
        if (headerTabel.equals(currentSortColumn)) {
            isAscending = !isAscending;
        } else {
            // Jika kolom yang dipilih berbeda dengan kolom sorting saat ini,
            // atur kolom sorting baru dan arah sorting ke ascending
            currentSortColumn = headerTabel;
            isAscending = true;
        }

        // Buat query SQL dengan menggunakan nama kolom database yang sesuai dan arah sorting
        String query = "SELECT * FROM film ORDER BY " + kolomDatabase + (isAscending ? " ASC" : " DESC");

        try {
            Connection conn = k.getCon();
            Statement stat = conn.createStatement();
            rs = stat.executeQuery(query);

            DefaultTableModel model = (DefaultTableModel) tblHome.getModel();
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);
            while (rs.next()) {
                Object[] data = new Object[7];
                data[0] = rs.getString("id_film");
                data[1] = rs.getString("judul");
                data[2] = rs.getString("tahun");
                data[3] = rs.getString("negara");
                data[4] = rs.getString("genre");
                data[5] = rs.getString("pemeran_utama");
                data[6] = rs.getString("sinopsis");

                model.addRow(data);
            }

            tblHome.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void sortDataUser(String headerTabel) {
        // Dapatkan nama kolom database yang sesuai berdasarkan pemetaan
        String kolomDatabase = columnUser.get(headerTabel);

        // Periksa apakah kolom yang dipilih sama dengan kolom sorting saat ini
        // Jika sama, ubah arah sorting
        if (headerTabel.equals(currentSortColumn)) {
            isAscending = !isAscending;
        } else {
            // Jika kolom yang dipilih berbeda dengan kolom sorting saat ini,
            // atur kolom sorting baru dan arah sorting ke ascending
            currentSortColumn = headerTabel;
            isAscending = true;
        }

        // Buat query SQL dengan menggunakan nama kolom database yang sesuai dan arah sorting
        String query = "SELECT * FROM user ORDER BY " + kolomDatabase + (isAscending ? " ASC" : " DESC");

        try {
            Connection conn = k.getCon();
            Statement stat = conn.createStatement();
            rs = stat.executeQuery(query);

            DefaultTableModel model = (DefaultTableModel) tblUser.getModel();
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);
            while (rs.next()) {
                Object[] data = new Object[5];
                data[0] = rs.getString("id_user");
                data[1] = rs.getString("nama");
                data[2] = rs.getString("username");
                data[3] = rs.getString("email");
                data[4] = rs.getString("role");

                model.addRow(data);
            }

            tblUser.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private int getTotalRows() {
        try {
            Connection cn = k.getCon();
            Statement st = cn.createStatement();
            rs = st.executeQuery("SELECT COUNT(*) FROM film");
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;
    }
    private int getTotalRowsUser() {
        try {
            Connection cn = k.getCon();
            Statement st = cn.createStatement();
            rs = st.executeQuery("SELECT COUNT(*) FROM user");
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;
    }
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
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBackUser;
    private javax.swing.JPanel btnDelete;
    private javax.swing.JPanel btnEdit;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnFirstUser;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLastUser;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNextUser;
    private javax.swing.JButton btnPoster;
    private javax.swing.JButton btnPoster1;
    private javax.swing.JPanel btnReset;
    private javax.swing.JPanel btnReset1;
    private javax.swing.JButton btnSort;
    private javax.swing.JButton btnSortUser;
    private javax.swing.JPanel btnTambah;
    private combobox.Combobox comboSort;
    private combobox.Combobox comboSortUser;
    private javax.swing.JPanel editFilm;
    private javax.swing.JPanel editPanel;
    private javax.swing.JPanel genreFilm;
    private javax.swing.JPanel home;
    private javax.swing.JPanel homePanel;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JPanel info;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel logout;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel pengguna;
    private javax.swing.JPanel penggunaPanel;
    private javax.swing.JLabel poster;
    private javax.swing.JLabel poster1;
    private javax.swing.JComboBox<String> rowsPerPageComboBox;
    private javax.swing.JComboBox<String> rowsPerPageComboBoxUser;
    private raven.scroll.win11.ScrollPaneWin11 scrollPaneWin111;
    private raven.scroll.win11.ScrollPaneWin11 scrollPaneWin113;
    private javax.swing.JLabel sinopsisiLabel;
    private javax.swing.JLabel tahunLabel;
    private javax.swing.JPanel tambahFilm;
    private javax.swing.JPanel tambahPanel;
    private javax.swing.JTable tblHome;
    private javax.swing.JTable tblUser;
    private javax.swing.JLabel titleLabel;
    private textfield.TextField txtCari;
    private textfield.TextField txtCariUser;
    private combobox.Combobox txtGenre;
    private combobox.Combobox txtGenre1;
    private textfield.TextField txtID;
    private textfield.TextField txtID1;
    private textfield.TextField txtJudul;
    private textfield.TextField txtJudul1;
    private textfield.TextField txtNegara;
    private textfield.TextField txtNegara1;
    private textfield.TextField txtPemeran;
    private textfield.TextField txtPemeran1;
    private textarea.TextAreaScroll txtSinopsis;
    private textarea.TextArea txtSinopsis1;
    private textarea.TextAreaScroll txtSinopsis2;
    private textarea.TextArea txtSinopsis3;
    private textfield.TextField txtTahun;
    private textfield.TextField txtTahun1;
    // End of variables declaration//GEN-END:variables
}
