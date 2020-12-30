/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co_den_trang;

/**
 *
 * @author duynn
 */
public class BanCo {

    private int a[][]; // 0 ben A danh, 1 ben B danh, 2 chua danh
    private boolean win;
    private int turnWho; // lượt A hay B

    public int[][] getA() {
        return a;
    }

    public boolean isWin() {
        return win;
    }

    public int getTurnWho() {
        return turnWho;
    }
    
    public BanCo(int m) {
        a = new int[m][m];
        //set tat ca deu chua duoc danh
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = 2;
            }
        }
        //dat cac o mac dinh giua ban co
        for (int i = a.length / 2 - 1; i <= a.length / 2; i++) {
            for (int j = a[0].length / 2 - 1; j <= a[0].length / 2; j++) {
                a[i][j] = (i + j) % 2;
            }
        }
        
        win = false;
        turnWho =0;
    }

    public BanCo() {
        a = new int[10][10];
        //set tat ca deu chua duoc danh
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = 2;
            }
        }
        //dat cac o mac dinh giua ban co
        for (int i = a.length / 2 - 1; i <= a.length / 2; i++) {
            for (int j = a[0].length / 2 - 1; j <= a[0].length / 2; j++) {
                a[i][j] = (i + j) % 2;
            }
        }
        win = false;
        turnWho =0;
    }

    public void checkWin() {
        boolean check = true;
        for (int i[] : a) {
            for (int j : i) {
                if (j == 2) {
                    check = false;
                    break;
                }
            }
            if (!check) {
                break;
            }
        }
        win = check;
    }
    
    public int[] countPoint() { //trả về số quân cờ bên A, bên B
        int c1 = 0;
        int c2 = 0;
        for (int i[] : a) {
            for (int j : i) {
                if (j == 0) {
                    c1++;
                } else if (j == 1) {
                    c2++;
                }
            }
        }
        int arr[]={c1,c2};
        //đổi turn
        turnWho = 1 - turnWho;
        return arr;
    }
    
    public void addPoint(int i, int j) {
        if (a[i][j] == 2) {
            a[i][j] = turnWho;
        }
    }

    public void anQuanCo(int x, int y) {
        int m = a.length, n=a[0].length;
        addPoint(x, y);
        int i, j, h;
        //Xét tại vị trí đánh (8 hướng):
        
        //- Đổi màu các con cờ theo hàng ngang từ dưới lên trên
        //tại hàng x từ vị trí y+1 dịch số quân liên tục không phải của người đang đánh
        i = x;
        j = y + 1;
        while (j < n && a[i][j] == 1 - turnWho) {
            j++;
        }
        //nếu có quân của người đánh chặn đầu còn lại thì các quân ở giữa đổi màu
        if (j < n && a[i][j] == turnWho) {
            for (int k = y + 1; k < j; k++) {
                a[i][k] = turnWho;
            }
        }
        
        //- Đổi màu các con cờ theo hàng ngang từ trên xuống dưới
        i = x;
        j = y - 1;
        while (j >= 0 && a[i][j] == 1 - turnWho) {
            j--;
        }
        if (j >= 0 && a[i][j] == turnWho) {
            for (int k = j + 1; k < y; k++) {
                a[i][k] = turnWho;
            }
        }
        //- Từ trái sang phải
        i = x + 1;
        j = y;
        while (i < m && a[i][j] == 1 - turnWho) {
            i++;
        }
        if (i < m && a[i][j] == turnWho) {
            for (int k = x + 1; k < i; k++) {
                a[k][j] = turnWho;
            }
        }
        //- Từ phải sang trái
        i = x - 1;
        j = y;
        while (i >= 0 && a[i][j] == 1 - turnWho) {
            i--;
        }
        if (i >= 0 && a[i][j] == turnWho) {
            for (int k = i + 1; k < x; k++) {
                a[k][j] = turnWho;
            }
        }
        //- Chéo trên phải
        i = x + 1;
        j = y + 1;
        while (i < m && j < n && a[i][j] == 1 - turnWho) {
            i++;
            j++;
        }
        h = y + 1;
        if (i < m && j < n && a[i][j] == turnWho) {
            for (int k = x + 1; k < i; k++, h++) {
                a[k][h] = turnWho;
            }
        }
        //- Chéo dưới trái
        i = x - 1;
        j = y - 1;
        while (i >= 0 && j >= 0 && a[i][j] == 1 - turnWho) {
            i--;
            j--;
        }
        h = j + 1;
        if (i >= 0 && j >= 0 && a[i][j] == turnWho) {
            for (int k = i + 1; k < x; k++, h++) {
                a[k][h] = turnWho;
            }
        }
        //- Chéo dưới phải
        i = x + 1;
        j = y - 1;
        while (i < m && j >= 0 && a[i][j] == 1 - turnWho) {
            i++;
            j--;
        }
        h = j + 1;
        if (i < m && j >= 0 && a[i][j] == turnWho) {
            for (int k = i - 1; h < y; k--, h++) {
                a[k][h] = turnWho;
            }
        }
        //- Chéo trên trái
        i = x - 1;
        j = y + 1;
        while (i >= 0 && j < n && a[i][j] == 1 - turnWho) {
            i--;
            j++;
        }
        h = y + 1;
        if (i >= 0 && j < n && a[i][j] == turnWho) {
            for (int k = x - 1; h < j; k--, h++) {
                a[k][h] = turnWho;
            }
        }
    }
}
