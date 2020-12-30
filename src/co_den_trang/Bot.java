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
public class Bot {
    private BanCo banCo;

    public Bot() {
    }

    public Bot(BanCo banCo) {
        this.banCo = banCo;
    }
    
    public int[] creatBoder() {       // chọn ngẫu nhiên 1 vị trí ở viền
        int a[][]= banCo.getA();
        int m = a.length, n=a[0].length;
        
        int arr[] = new int[2];
        arr[0] = arr[1] = -1;
        //đếm xem vùng biên còn bao nhiêu ô chưa đánh
        int k = 0;
        //trái phải
        for (int i = 0; i < m; i++) {
            if (a[i][0] == 2) {
                k++;
            }
            if (a[i][n - 1] == 2) {
                k++;
            }
        }
        //trên dưới
        for (int j = 0; j < n; j++) {
            if (a[0][j] == 2) {
                k++;
            }
            if (a[m - 1][j] == 2) {
                k++;
            }
        }
        //chọn ngẫu nhiễn 1 toạ độ giá tri từ 1->k
        int h = (int) (Math.random() * (k - 1)) + 1;
        k = 0;
        //lấy vị toạ độ đã chọn ở trên
        for (int i = 0; i < m; i++) {
            if (a[i][0] == 2) {
                k++;
                if (h == k) {
                    arr[0] = i;
                    arr[1] = 0;
                }
            }
            if (a[i][n - 1] == 2) {
                k++;
                if (h == k) {
                    arr[0] = i;
                    arr[1] = n - 1;
                }
            }
        }
        for (int j = 0; j < n; j++) {
            if (a[0][j] == 2) {
                k++;
                if (h == k) {
                    arr[0] = 0;
                    arr[1] = j;
                }
            }
            if (a[m - 1][j] == 2) {
                k++;
                if (h == k) {
                    arr[0] = m - 1;
                    arr[1] = j;
                }
            }
        }
        //trả về toạ độ (x,y)
        return arr;
    }

    public int[] creatPoint() {   // chọn 1 vị trí ngẫu nhiên trên bàn cờ
        int a[][]= banCo.getA();
        int m = a.length, n=a[0].length;
        
        int arr[] = new int[2];
        int k = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] == 2) {
                    k++;
                }
            }
        }
        int h = (int) (Math.random() * (k - 1)) + 1;
        k = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] == 2) {
                    k++;
                    if (k == h) {
                        arr[0] = i;
                        arr[1] = j;
                    }
                }
            }
        }
        return arr;
    }

    public int demSoQuanCoTheAn(int x, int y) {
        int a[][] = banCo.getA();
        int turnWho = banCo.getTurnWho();
        int m = a.length, n=a[0].length;
        int dem = 0;
        int i, j, h;
        //xét đủ 8 hướng giống hàm ăn cờ của BanCo
        i = x;
        j = y + 1;
        while (j < n && a[i][j] == 1 - turnWho) {
            j++;
        }
        if (j < n && a[i][j] == turnWho) {
            for (int k = y + 1; k < j; k++) {
                dem++;
            }
        }
        i = x;
        j = y - 1;
        while (j >= 0 && a[i][j] == 1 - turnWho) {
            j--;
        }
        if (j >= 0 && a[i][j] == turnWho) {
            for (int k = j + 1; k < y; k++) {
                dem++;
            }
        }

        i = x + 1;
        j = y;
        while (i < m && a[i][j] == 1 - turnWho) {
            i++;
        }
        if (i < m && a[i][j] == turnWho) {
            for (int k = x + 1; k < i; k++) {
                dem++;
            }
        }
        i = x - 1;
        j = y;
        while (i >= 0 && a[i][j] == 1 - turnWho) {
            i--;
        }
        if (i >= 0 && a[i][j] == turnWho) {
            for (int k = i + 1; k < x; k++) {
                dem++;
            }
        }

        i = x + 1;
        j = y + 1;
        while (i < m && j < n && a[i][j] == 1 - turnWho) {
            i++;
            j++;
        }
        h = y + 1;
        if (i < m && j < n && a[i][j] == turnWho) {
            for (int k = x + 1; k < i; k++, h++) {
                dem++;
            }
        }
        i = x - 1;
        j = y - 1;
        while (i >= 0 && j >= 0 && a[i][j] == 1 - turnWho) {
            i--;
            j--;
        }
        h = j + 1;
        if (i >= 0 && j >= 0 && a[i][j] == turnWho) {
            for (int k = i + 1; k < x; k++, h++) {
                dem++;
            }
        }

        i = x + 1;
        j = y - 1;
        while (i < m && j >= 0 && a[i][j] == 1 - turnWho) {
            i++;
            j--;
        }
        h = j + 1;
        if (i < m && j >= 0 && a[i][j] == turnWho) {
            for (int k = i - 1; h < y; k--, h++) {
                dem++;
            }
        }
        i = x - 1;
        j = y + 1;
        while (i >= 0 && j < n && a[i][j] == 1 - turnWho) {
            i--;
            j++;
        }
        h = y + 1;
        if (i >= 0 && j < n && a[i][j] == turnWho) {
            for (int k = x - 1; h < j; k--, h++) {
                dem++;
            }
        }
        return dem;
    }

    public void chonNuocDi() {
        int a[][]= banCo.getA();
        int m=a.length, n = a[0].length;
        int arr[] = {-1, -1};
        if (Math.random() > 0.7) {
            arr = creatBoder();
        }
        if (arr[0] == -1) {
            arr = creatPoint();
        }
        int x = arr[0];
        int y = arr[1];
        int M = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (a[i][j] == 2) {
                    int temp = demSoQuanCoTheAn(i, j);
                    if (temp > M) {
                        M = temp;
                        x = i;
                        y = j;
                    }
                }
            }
        }
        banCo.anQuanCo(x, y);
    }

}
