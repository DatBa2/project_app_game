/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game2048.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JPanel;

/**
 *
 * @author Nguyen Giang
 */
public class Game2048Rank extends JPanel{
  private static final Color BG_COLOR = new Color(0xbbada0);
  private static final String FONT_NAME = "Arial";
  
  long score[];

  public Game2048Rank() {
    setFocusable(true);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    docFile();
    g.setFont(new Font(FONT_NAME, Font.PLAIN, 25));
    g.setColor(Color.BLACK);
    for(int i=1;i<=5;i++) g.drawString(i+". "+score[i-1]+" điểm", 100, 70*i);
  }

    private void docFile() {
        score=new long[5];
        try {
            Scanner input=new Scanner(new File("THANHTICH.TXT"));
            for(int i=0;i<5;i++) score[i]=Long.parseLong(input.nextLine());
        } catch (IOException e) {}
    }

}
