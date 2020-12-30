/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game2048.controller;

import game2048.model.Tile;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author Nguyen Giang
 */
public class Game2048Controller extends JPanel{
  private static int kichThuoc=4;
  private static boolean hard=false;
  private static final Color BG_COLOR = new Color(0xbbada0);
  private static final String FONT_NAME = "Arial";
  private static final int TILE_SIZE = 64;
  private static final int TILES_MARGIN = 16;

  private Tile[] myTiles;
  boolean myWin = false;
  boolean myLose = false;
  long score[];
  int myScore = 0;
  JTextField textName=new JTextField();

  public Game2048Controller() {
    setFocusable(true);
    KeyStroke key_left = KeyStroke.getKeyStroke("LEFT");
    KeyStroke key_right = KeyStroke.getKeyStroke("RIGHT");
    KeyStroke key_up = KeyStroke.getKeyStroke("UP");
    KeyStroke key_down = KeyStroke.getKeyStroke("DOWN");
    KeyStroke key_escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key_left, "left");
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key_right, "right");
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key_up, "up");
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key_down, "down");
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key_escape, "escape");
    
    getActionMap().put("left", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(!canMove()) myLose = true;
        if(!myWin && !myLose) left();
        if(!myWin && !canMove()) myLose = true;
        repaint();
      }
    });
    
    getActionMap().put("right", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(!canMove()) myLose = true;
        if(!myWin && !myLose) right();
        if(!myWin && !canMove()) myLose = true;
        repaint();
      }
    });
    
    getActionMap().put("up", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(!canMove()) myLose = true;
        if(!myWin && !myLose) up();
        if(!myWin && !canMove()) myLose = true;
        repaint();
      }
    });
    
    getActionMap().put("down", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(!canMove()) myLose = true;
        if(!myWin && !myLose) down();
        if(!myWin && !canMove()) myLose = true;
        repaint();
      }
    });
    
    getActionMap().put("escape", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        resetGame();
        repaint();
      }
    });
    resetGame();
  }

  public void resetGame() {
    checkHightScore();
    myScore = 0;
    myWin = false;
    myLose = false;
    myTiles = new Tile[kichThuoc * kichThuoc];
    for (int i = 0; i < myTiles.length; i++) {
      myTiles[i] = new Tile();
    }
    addTile();
    addTile();
  }

  public void left() {
    boolean needAddTile = false;
    for (int i = 0; i < kichThuoc; i++) {
      Tile[] line = getLine(i);
      Tile[] merged = mergeLine(moveLine(line));
      setLine(i, merged);
      if (!needAddTile && !compare(line, merged)) {
        needAddTile = true;
      }
    }

    if (needAddTile) {
      addTile();
    }
  }

  public void right() {
    myTiles = rotate(180);
    left();
    myTiles = rotate(180);
  }

  public void up() {
    myTiles = rotate(270);
    left();
    myTiles = rotate(90);
  }

  public void down() {
    myTiles = rotate(90);
    left();
    myTiles = rotate(270);
  }

  private Tile tileAt(int x, int y) {
    return myTiles[x + y * kichThuoc];
  }

  private void addTile() {
    List<Tile> list = availableSpace();
    if (!availableSpace().isEmpty()) {
      int index = (int) (Math.random() * list.size()) % list.size();
      Tile emptyTime = list.get(index);
      if(hard) emptyTime.setValue(Math.random() < 0.9 ? 2 : 3);
      else emptyTime.setValue(Math.random() < 0.9 ? 2 : 4);
    }
  }

  private List<Tile> availableSpace() {
    final List<Tile> list = new ArrayList<Tile>(kichThuoc*kichThuoc);
    for (Tile t : myTiles) {
      if (t.isEmpty()) {
        list.add(t);
      }
    }
    return list;
  }

  private boolean isFull() {
    return availableSpace().size() == 0;
  }

  boolean canMove() {
    if (!isFull()) {
      return true;
    }
    for (int x = 0; x < kichThuoc; x++) {
      for (int y = 0; y < kichThuoc; y++) {
        Tile t = tileAt(x, y);
        if ((x < kichThuoc-1 && t.getValue() == tileAt(x + 1, y).getValue())
          || ((y < kichThuoc-1) && t.getValue() == tileAt(x, y + 1).getValue())) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean compare(Tile[] line1, Tile[] line2) {
    if (line1 == line2) {
      return true;
    } else if (line1.length != line2.length) {
      return false;
    }

    for (int i = 0; i < line1.length; i++) {
      if (line1[i].getValue() != line2[i].getValue()) {
        return false;
      }
    }
    return true;
  }

  private Tile[] rotate(int angle) {
    Tile[] newTiles = new Tile[kichThuoc * kichThuoc];
    int offsetX = kichThuoc-1, offsetY = kichThuoc-1;
    if (angle == 90) {
      offsetY = 0;
    } else if (angle == 270) {
      offsetX = 0;
    }

    double rad = Math.toRadians(angle);
    int cos = (int) Math.cos(rad);
    int sin = (int) Math.sin(rad);
    for (int x = 0; x < kichThuoc; x++) {
      for (int y = 0; y < kichThuoc; y++) {
        int newX = (x * cos) - (y * sin) + offsetX;
        int newY = (x * sin) + (y * cos) + offsetY;
        newTiles[(newX) + (newY) * kichThuoc] = tileAt(x, y);
      }
    }
    return newTiles;
  }

  private Tile[] moveLine(Tile[] oldLine) {
    LinkedList<Tile> l = new LinkedList<Tile>();
    for (int i = 0; i < kichThuoc; i++) {
      if (!oldLine[i].isEmpty())
        l.addLast(oldLine[i]);
    }
    if (l.size() == 0) {
      return oldLine;
    } else {
      Tile[] newLine = new Tile[kichThuoc];
      ensureSize(l, kichThuoc);
      for (int i = 0; i < kichThuoc; i++) {
        newLine[i] = l.removeFirst();
      }
      return newLine;
    }
  }

  private Tile[] mergeLine(Tile[] oldLine) {
    LinkedList<Tile> list = new LinkedList<Tile>();
    for (int i = 0; i < kichThuoc && !oldLine[i].isEmpty(); i++) {
      int num = oldLine[i].getValue();
      if (i < kichThuoc-1 && oldLine[i].getValue() == oldLine[i + 1].getValue()) {
        if(hard) num++;
        else num *= 2;
        myScore += num;
        int ourTarget = 2048;
        if (num == ourTarget) {
          myWin = true;
        }
        i++;
      }
      list.add(new Tile(num));
    }
    if (list.size() == 0) {
      return oldLine;
    } else {
      ensureSize(list, kichThuoc);
      return list.toArray(new Tile[kichThuoc]);
    }
  }

  private static void ensureSize(java.util.List<Tile> l, int s) {
    while (l.size() != s) {
      l.add(new Tile());
    }
  }

  private Tile[] getLine(int index) {
    Tile[] result = new Tile[kichThuoc];
    for (int i = 0; i < kichThuoc; i++) {
      result[i] = tileAt(i, index);
    }
    return result;
  }

  private void setLine(int index, Tile[] re) {
    System.arraycopy(re, 0, myTiles, index * kichThuoc, kichThuoc);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(BG_COLOR);
    g.fillRect(0, 0, this.getSize().width, this.getSize().height);
    for (int y = 0; y < kichThuoc; y++) {
      for (int x = 0; x < kichThuoc; x++) {
        drawTile(g, myTiles[x + y * kichThuoc], x, y);
      }
    }
  }

  private void drawTile(Graphics g2, Tile tile, int x, int y) {
    Graphics2D g = ((Graphics2D) g2);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
    int value = tile.getValue();
    int xOffset = offsetCoors(x);
    int yOffset = offsetCoors(y);
    g.setColor(tile.getBackground());
    g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14);
    g.setColor(tile.getForeground());
    final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
    final Font font = new Font(FONT_NAME, Font.BOLD, size);
    g.setFont(font);

    String s = String.valueOf(value);
    final FontMetrics fm = getFontMetrics(font);

    final int w = fm.stringWidth(s);
    final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

    if (value != 0)
      g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);

    if (myWin || myLose) {
      g.setColor(new Color(255, 255, 255, 30));
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setColor(new Color(78, 139, 202));
      g.setFont(new Font(FONT_NAME, Font.BOLD, 48));
      if (myWin) {
        g.drawString("Chiến thắng!", 68, 150);
      }
      if (myLose) {
        g.drawString("Game over!", 50, 130);
        g.drawString("Bạn thua!", 64, 200);
      }
      if (myWin || myLose) {
        g.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        g.setColor(new Color(128, 128, 128, 128));
        g.drawString("Nhấn ESC để chơi lại", 80, getHeight() - 40);
      }
    }
    g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
    g.drawString("Điểm: " + myScore, 52*kichThuoc, 80*kichThuoc+40);

  }

    private static int offsetCoors(int arg) {
        return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
    }
    
    private void docFile() {
        score=new long[5];
        try {
            Scanner input=new Scanner(new File("THANHTICH.TXT"));
            for(int i=0;i<5;i++) score[i]=Long.parseLong(input.nextLine());
        } catch (IOException e) {}
    }

    private void checkHightScore() {
        FileWriter fw = null;
        docFile();
        
        int i=0;
        while(i<5 && score[i]>myScore) i++;
        if(i<5)
          {
              System.out.println("Luu diem cao!");
              for(int j=4;j>i;j--) score[j]=score[j-1];
              score[i]=myScore;
          }
        
        try {
            fw = new FileWriter(new File("THANHTICH.TXT"));
            for(int j=0;j<5;j++) fw.write(score[j]+"\n");
        } catch (IOException e) {
        } finally {
            if (fw != null){
                try {
                    fw.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public static void setKichThuoc(int kichThuoc) {
        Game2048Controller.kichThuoc = kichThuoc;
    }

    public static void setHard(boolean hard) {
        Game2048Controller.hard = hard;
    }

}
