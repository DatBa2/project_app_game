/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic_tac_toe;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author duynn
 */
public class FormTicTacToe extends JFrame implements ActionListener {

    private BanCo banCo;
    private Bot bot;
    private int luot;
    String icon;
    String str = "";
    int count;
    int countH[] = {0, 0};
    String text[] = {"X", "O"};
    private Color background_cl = Color.white;
    private Color number_cl[] = {Color.red, Color.blue};
    private JButton bt[] = new JButton[9];
    private JButton btMark1, btMark2;
    private JLabel turn;
    private JButton newGame_bt;
    private JPanel pnTurn, pn, pnRes;
    Timer timer;

    public static void main(String[] args) {
        new FormTicTacToe(0, 0).setVisible(true);
    }
    public FormTicTacToe(int mark1, int mark2) {
        banCo = new BanCo(mark1, mark2);
        bot = new Bot(banCo);
        luot = banCo.getTurn();
        init();
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!banCo.isWin()) {
                    banCo.addPoint(bot.autoPoint());
                    banCo.changeTurn();
                    luot = banCo.getTurn();
                    paint();
                    checkWin();
                    timer.stop();
                }
            }
        });
    }

    public void init() {
        setTitle("TicTacToe");
        pnTurn = new JPanel();
        pnTurn.setLayout(new FlowLayout());
        turn = new JLabel("Lượt của X");
        turn.setFont(new Font("UTM Micra", 1, 20));
        pnTurn.add(turn);
        pn = new JPanel();
        pn.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            bt[i] = new JButton(" ");
            pn.add(bt[i]);
            bt[i].setActionCommand(String.valueOf(i));
            bt[i].setBackground(background_cl);
            bt[i].addActionListener(this);
            bt[i].setFont(new Font("Antique", 1, 120));
        }

        pnRes = new JPanel();
        pnRes.setLayout(new FlowLayout());

        newGame_bt = new JButton("New game");
        newGame_bt.setForeground(Color.green);
        newGame_bt.addActionListener(this);
        newGame_bt.setFont(new Font("UTM Swiss 721 Black Condensed", 1, 18));
        newGame_bt.setActionCommand("-1");

        btMark1 = new JButton(String.valueOf(banCo.getMark1()));
        btMark1.setFont(new Font("UTM Nokia", 1, 25));

        btMark2 = new JButton(String.valueOf(banCo.getMark2()));
        btMark2.setFont(new Font("UTM Nokia", 1, 25));

        pnRes.add(btMark1);
        pnRes.add(newGame_bt);
        pnRes.add(btMark2);

        this.getContentPane().add(pnTurn, BorderLayout.NORTH);
        this.getContentPane().add(pn, BorderLayout.CENTER);
        this.getContentPane().add(pnRes, BorderLayout.SOUTH);
        this.setVisible(true);
        this.setSize(500, 570);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        count = countH[0] = countH[1] = 0;
    }

    public void paint() {
        //luot
        if (banCo.getTurn() == 0) {
            turn.setText("Lượt của X");
        } else {
            turn.setText("Lượt của O");
        }
        //o ban co
        int a[] = banCo.getA();
        for (int i = 0; i < 9; i++) {
            switch (a[i]) {
                case 0:
                    bt[i].setText("X");
                    bt[i].setForeground(number_cl[a[i]]);
                    break;
                case 1:
                    bt[i].setText("O");
                    bt[i].setForeground(number_cl[a[i]]);
                    break;
                default:
                    bt[i].setText(" ");
                    break;
            }
        }
        //diem
        btMark1.setText(""+banCo.getMark1());
        btMark2.setText(""+banCo.getMark2());
        //
        
        
    }
    public void checkWin()
    {
        int cw = banCo.checkWin(banCo.getA());
        if (cw != -1) {
            String mess ;
            if (cw == 2) {
                mess = "Hòa!";
                banCo.tie();
            } else {
                if (banCo.getTurn()!=luot) {
                    mess = "Bạn đã thắng!";
                    banCo.win();
                } else {
                    mess = "Bạn đã thua!";
                    banCo.lose();
                }
            }
            banCo.setWin(true);
            JOptionPane.showMessageDialog(null, mess);
            paint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(newGame_bt.getActionCommand())) {
            banCo.newGame();
            paint();
            if (Math.random() >= 0.5) {
                timer.start();
                icon = "O";
            } else {
                icon = "X";
            }
        } else if (!banCo.isWin()) {
            int k = Integer.parseInt(e.getActionCommand());
            if (banCo.getA()[k] == 2) {
                banCo.addPoint(k);
                luot = banCo.getTurn();
                banCo.changeTurn();
                paint();
                checkWin();
                timer.start();
            }
        }
    }

}
