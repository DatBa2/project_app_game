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
public class Bot {

    private BanCo banCo;

    public Bot(BanCo banCo) {
        this.banCo = banCo;
    }

    /**
     * Tìm nước đi để Bot có thể chiến thắng
     *
     * @return int nước đi có thể chiến thắng -1 nếu không tìm thấy
     */
    public int winPoint() {
        int a[] = banCo.getA();
        int turn = banCo.getTurn();
        for (int i = 0; i < 9; i++) {
            if (a[i] == 2) {
                a[i] = turn;
                if (banCo.checkWin(a) != -1) {
                    a[i] = 2;
                    return i;
                }
                a[i] = 2;
            }
        }
        return -1;
    }

    /**
     * Tìm nước nếu không chặn sẽ bị thua
     *
     * @return int nước đi nếu không chặn sẽ thua -1 nếu không tìm thấy
     */
    public int losePoint() {
        int a[] = banCo.getA();
        int turn = banCo.getTurn();
        for (int i = 0; i < 9; i++) {
            if (a[i] == 2) {
                a[i] = 1 - turn;
                if (banCo.checkWin(a) != -1) {
                    a[i] = 2;
                    return i;
                }
                a[i] = 2;
            }
        }
        return -1;
    }

    /**
     * Tạo một nước đi bất kì Thứ tự ưu tiên ở giữa -> 4 góc -> các nước còn lại
     *
     * @return int nước đi
     */
    public int creatRandomPoint() {
        int a[] = banCo.getA();
        //ở giữa
        if (a[4] == 2) {
            return 4;
        }
        //random 1 trong 4 góc nếu có
        int k = 0;
        k += (a[0] == 2) ? 1 : 0;
        k += (a[2] == 2) ? 1 : 0;
        k += (a[6] == 2) ? 1 : 0;
        k += (a[8] == 2) ? 1 : 0;
        if (k > 0) {
            int h = (int) ((k - 1) * Math.random() + 1);
            k = 0;
            k += (a[0] == 2) ? 1 : 0;
            if (k == h) {
                return 0;
            }
            k += (a[2] == 2) ? 1 : 0;
            if (k == h) {
                return 2;
            }
            k += (a[6] == 2) ? 1 : 0;
            if (k == h) {
                return 6;
            }
            k += (a[8] == 2) ? 1 : 0;
            if (k == h) {
                return 8;
            }
        }
        //random các nước còn lại
        for (int i = 0; i < 9; i++) {
            if (a[i] == 2) {
                k++;
            }
        }
        int h = (int) ((k - 1) * Math.random() + 1);
        k = 0;
        for (int i = 0; i < 9; i++) {
            if (a[i] == 2) {
                k++;
                if (k == h) {
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * Đánh 1 nước đi ngẫu nhiên tối ưu nhất
     * @return int nước đi
     */
    public int autoPoint() {
        int k = winPoint();
        if (k != -1) {
            return k;
        } else {
            k = losePoint();
            if (k != -1) {
                return k;
            } else {
                return creatRandomPoint();
            }
        }
    }
}
