/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic_tac_toe;

/**
 *
 * @author duynn
 */
public class BanCo {

    private boolean win;
    private int mark1, mark2;
    private int[] a; //0 bên A đánh, 1 bên B đánh, 2 ô trống
    private int turn;

    public boolean isWin() {
        return win;
    }

    public int getMark1() {
        return mark1;
    }

    public int getMark2() {
        return mark2;
    }

    public int[] getA() {
        return a;
    }

    public int getTurn() {
        return turn;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    
    public BanCo(int mark1, int mark2 ) {
        win = false;
        turn = 0;
        this.mark1 = mark1;
        this.mark2 = mark2;
        a = new int[9];
        for (int i = 0; i < 9; i++) {
            a[i] = 2;
        }
    }
    
    public void newGame(){
        win = false;
        turn = 0;
        a = new int[9];
        for (int i = 0; i < 9; i++) {
            a[i] = 2;
        }
    }
    public void changeTurn(){
        turn = 1- turn;
    }
    public void tie()
    {
        mark1++; mark2++;
    }
    public void win()
    {
        mark1+=3;
    }
    public void lose()
    {
        mark2+=3;
    }
    
    /**
     * Kiểm tra xem đã có người chiến thắng chưa 
     * thắng khi tạo được 1 hàng ngang dọc hay chéo
     * @return int thể hiện trạng thái
     * 0 tương đương người thứ nhất 
     * 1 tương đương người thứ hai
     * 2 là hoà
     * -1 là chưa kết thúc
     */
    public int checkWin(int arr[]) {
        //0 1 2
        //3 4 5
        //6 7 8
        //hàng ngang thứ 1
        if (arr[0] != 2 && arr[0] == arr[1] && arr[1] == arr[2]) {
            return arr[0];
        }
        //hàng ngang thứ 2
        if (arr[3] != 2 && arr[3] == arr[4] && arr[4] == arr[5]) {
            return arr[3];
        }
        //hàng ngang thứ 3
        if (arr[6] != 2 && arr[6] == arr[7] && arr[7] == arr[8]) {
            return arr[6];
        }
        //hàng dọc thứ 1
        if (arr[0] != 2 && arr[0] == arr[3] && arr[3] == arr[6]) {
            return arr[0];
        }
        //hàng dọc thứ 2
        if (arr[1] != 2 && arr[1] == arr[4] && arr[4] == arr[7]) {
            return arr[1];
        }
        //hàng dọc thứ 3
        if (arr[2] != 2 && arr[2] == arr[5] && arr[5] == arr[8]) {
            return arr[2];
        }
        //đường chéo chính
        if (arr[0] != 2 && arr[0] == arr[4] && arr[4] == arr[8]) {
            return arr[0];
        }
        //đường chéo phụ
        if (arr[2] != 2 && arr[2] == arr[4] && arr[4] == arr[6]) {
            return arr[2];
        }
        //vẫn còn ô trống
        for (int i = 0; i < 9; i++) {
            if (arr[i] == 2) {
                return -1;
            }
        }
        //hoà
        return 2;
    }
    
    /**
     * Thêm 1 nước đánh vào bàn cờ
     * @param pos: vị trí thêm vào
     * @return int turn - người đánh nước đó
     */
    public int addPoint(int pos){
        if(!win)
        {
            if(a[pos]==2)
            {
                a[pos]=turn;
            }
        }
        return turn;
    }
   
    
}
