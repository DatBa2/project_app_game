package game2048.view;

import game2048.controller.Game2048Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author Nguyen Giang
 */
public class Game2048 extends JFrame{
 
    static JMenuBar menuBar = new JMenuBar();
    
    static JMenu mSetting = new JMenu("Tùy chỉnh");
    static JMenu mTable = new JMenu("Kích thước");
    static JMenu mMode = new JMenu("Chế độ");
    static JMenu mInfo = new JMenu("Thông tin");
    
    static JRadioButtonMenuItem tb_4x4 = new JRadioButtonMenuItem("4x4");
    static JRadioButtonMenuItem tb_5x5 = new JRadioButtonMenuItem("5x5");
    static JRadioButtonMenuItem tb_6x6 = new JRadioButtonMenuItem("6x6");
    
    static JRadioButtonMenuItem cs_mode = new JRadioButtonMenuItem("Thông thường");
    static JRadioButtonMenuItem hd_mode = new JRadioButtonMenuItem("Siêu khó");
    
    static JMenuItem sub_rank = new JMenuItem("Bảng xếp hạng");
    static JMenuItem sub_about = new JMenuItem("Hướng dẫn");
    
    static JMenuItem sub_new = new JMenuItem("Chơi mới");
    static JMenuItem sub_exit = new JMenuItem("Thoát");
    
    static JFrame frame=new JFrame();

    public Game2048() {
        tb_4x4.setSelected(true);
        tb_5x5.setSelected(false);
        tb_6x6.setSelected(false);
        
        cs_mode.setSelected(true);
        hd_mode.setSelected(false);
        
        mTable.add(tb_4x4);
        mTable.add(tb_5x5);
        mTable.add(tb_6x6);
        
        mMode.add(cs_mode);
        mMode.add(hd_mode);
        
        mSetting.add(sub_new);
        mSetting.add(mTable);
        mSetting.add(mMode);
        mSetting.add(sub_exit);
        
        mInfo.add(sub_rank);
        mInfo.add(sub_about);
        
        menuBar.add(mSetting);
        menuBar.add(mInfo);
        
        frame.setJMenuBar(menuBar);
 
        frame.setTitle("Game 2048");
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setSize(350, 460);
        
        sub_new.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                  {
                    frame.setContentPane(new Game2048Controller());
                    frame.setVisible(true);
                  }
            });
        
        tb_4x4.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                  {
                    Game2048Controller.setKichThuoc(4);
                    tb_4x4.setSelected(true);
                    tb_5x5.setSelected(false);
                    tb_6x6.setSelected(false);
                    frame.setContentPane(new Game2048Controller());
                    frame.setSize(350, 450);
                    frame.setVisible(true);
                  }
            });
        
        tb_5x5.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                  {
                    Game2048Controller.setKichThuoc(5);
                    tb_4x4.setSelected(false);
                    tb_5x5.setSelected(true);
                    tb_6x6.setSelected(false);
                    frame.setContentPane(new Game2048Controller());
                    frame.setSize(440, 530);
                    frame.setVisible(true);
                  }
            });
        
        tb_6x6.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                  {
                    Game2048Controller.setKichThuoc(6);
                    tb_4x4.setSelected(false);
                    tb_5x5.setSelected(false);
                    tb_6x6.setSelected(true);
                    frame.setContentPane(new Game2048Controller());
                    frame.setSize(530, 610);
                    frame.setVisible(true);
                  }
            });
        
        cs_mode.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                  {
                    cs_mode.setSelected(true);
                    hd_mode.setSelected(false);
                    Game2048Controller.setHard(false);
                    frame.setContentPane(new Game2048Controller());
                    frame.setVisible(true);
                  }
            });
        
        hd_mode.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                  {
                    cs_mode.setSelected(false);
                    hd_mode.setSelected(true);
                    Game2048Controller.setHard(true);
                    frame.setContentPane(new Game2048Controller());
                    frame.setVisible(true);
                  }
            });
        
        sub_rank.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                  {
                    frame.setContentPane(new Game2048Rank());
                    frame.setSize(350, 450);
                    frame.setVisible(true);
                  }
            });
        
        sub_about.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                  {
                      JOptionPane.showMessageDialog(null, "Sử dụng các phím mũi "
                              + "tên để ghép các ô lại với nhau, nếu có 1 ô đạt "
                              + "giá trị 2048 là chiến thắng.\n- Chế độ thông thường: "
                              + "ghép 2 ô tăng gấp đôi giá trị.\n- Chế độ siêu khó: "
                              + "ghép 2 ô chỉ tăng giá trị của ô lên 1 đơn vị.", 
                              "Hướng dẫn", 1);
                  }
            });
        
        sub_exit.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                  {
                      frame.dispose();
                  }
            });
        
        try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("image/test.jpg")))));
        } catch (IOException ex) {
            Logger.getLogger(Game2048.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
}