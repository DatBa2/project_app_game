/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co_den_trang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author duynn
 */
public class FormBanCo extends javax.swing.JFrame {

    /**
     * Creates new form FormBanCo
     */
    private BanCo banCo;
    private Bot bot;
    Timer timer;

    private JPanel oBanCo; //gồm các nút bấm
    private JButton bts[][];

    private JPanel oKetQua;
    private JButton res1, res2;

    //mau sac
    private Color A = Color.darkGray;
    private Color B = Color.white;
    private Color background = Color.gray;
    private Color color[] = {A, B, background};

    public FormBanCo() {
        init();
        paint();
        timer = new Timer(350, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!banCo.isWin()) {
                    bot.chonNuocDi();
                    update();
                    timer.stop();
                }
            }
        });
    }

    private void init() {
        banCo = new BanCo(); // mac dinh kich thuoc 10x10
        bot = new Bot(banCo);
        int m = banCo.getA().length, n = banCo.getA()[0].length;
        //panel
        oBanCo = new JPanel();
        oBanCo.setLayout(new GridLayout(m, n));
        oKetQua = new JPanel();
        //tạo các nút bấm
        bts = new JButton[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                bts[i][j] = new JButton("   ");
                oBanCo.add(bts[i][j]);
                bts[i][j].setActionCommand(i + " " + j);
                bts[i][j].setBackground(background);
                bts[i][j].addActionListener(new MyActionListener(this));
            }
        }
        //tao ô kết quả
        res1 = new JButton("2");
        res2 = new JButton("2");
        Font font = new Font("Times New Roman", Font.BOLD, 20);
        res1.setFont(font);
        res2.setFont(font);
        res1.setBackground(A);
        res2.setBackground(B);
        res1.setForeground(B);
        res2.setForeground(A);
        oKetQua.add(res1);
        oKetQua.add(res2);
        //them vao frame
        this.getContentPane().add(oBanCo, BorderLayout.CENTER);
        this.getContentPane().add(oKetQua, BorderLayout.SOUTH);
        setSize(m * 50, n * 50);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Cờ đen trắng");
        JOptionPane.showMessageDialog(rootPane, "Bạn là quân đen");
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
            java.util.logging.Logger.getLogger(FormBanCo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormBanCo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormBanCo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormBanCo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormBanCo().setVisible(true);
            }
        });
    }

    private void paint() {
        int a[][] = banCo.getA();
        int m = banCo.getA().length, n = banCo.getA()[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                bts[i][j].setBackground(color[a[i][j]]);
            }
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public BanCo getBanCo() {
        return banCo;
    }

    private void update() {
        int temp[] = banCo.countPoint(); //đã đổi turn
        //for(int i:temp) System.out.println(" "+i);
        res1.setText("" + temp[0]);
        res2.setText("" + temp[1]);
        banCo.checkWin();
        if (banCo.isWin()) {
            timer.stop();
            String s = "Trắng";
            if (temp[0] > temp[1]) {
                s = "Đen";
            }
            JOptionPane.showMessageDialog(null, s + " đã chiến thắng!");
        }
        paint();
    }

    private static class MyActionListener implements ActionListener {

        private FormBanCo home;

        public MyActionListener(FormBanCo home) {
            this.home = home;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!home.getBanCo().isWin()) {
                int i = 0, j = 0;
                String s = e.getActionCommand();
                int k = s.indexOf(32);
                i = Integer.parseInt(s.substring(0, k));
                j = Integer.parseInt(s.substring(k + 1, s.length()));
                if (home.getBanCo().getA()[i][j] == 2) {
                    home.getBanCo().anQuanCo(i, j);
                    home.update();
                    home.getTimer().start();
                }
            }
        }
    }           
}
