package Ludo;

import static Ludo.Main.board;
import static Ludo.Main.colorBackground;

public class Move {

    public static boolean move(int i, int j) {
        if (board[i][j].getCoin() != null && !Dice.enable) {
            int row = board[i][j].getCoin().getRow();
            colorBackground = board[i][j].color;
            if (board[i][j].getCoin().isCurrent()) {
                System.out.println(board[i][j].getCoin().getNumber());
                if (board[i][j].getCoin().getNumber() + Dice.num < Path.pathX[0].length) {
                    int x = Path.pathX[row][board[i][j].getCoin().getNumber() + Dice.num];
                    int y = Path.pathY[row][board[i][j].getCoin().getNumber() + Dice.num];
                    if (board[x][y].getCoin() == null) {
                        board[i][j].getCoin().setNumber(board[i][j].getCoin().getNumber() + Dice.num);
                        x = Path.pathX[row][board[i][j].getCoin().getNumber()];
                        y = Path.pathY[row][board[i][j].getCoin().getNumber()];

                        board[x][y].setCoin(board[i][j].getCoin());
                        board[i][j].setCoin(null);
                        colorBackground = board[x][y].color;
                        System.out.println("**********************");
                        Dice.enable = true;
                        System.out.println("change 1");
                        changeTurn(Main.turn);
                        Gui.turn();
                        return true;
                    } else {
                        if (!checkCoin(Main.turn)) {
                            changeTurn(Main.turn);
                            Dice.enable = true;
                            Gui.turn();
                            System.out.println("change2");
                        }
                        return false;
                    }
                } else {
                    if (!checkCoin(Main.turn)) {
                        Dice.enable = true;
                        System.out.println("change3");
                        changeTurn(Main.turn);
                        Gui.turn();
                    }
                    return false;
                }
            } else {
                int x = Path.pathX[row][0];
                int y = Path.pathY[row][0];
                boolean bool = Logic.start(Dice.num, board[i][j].getCoin(), board[x][y], board[i][j]);
                if (bool) {
                    Dice.enable = true;
                }
                if (!checkCoin(Main.turn)) {
                    System.out.println("injaaaaaaaaa");
                    Dice.enable = true;
                    System.out.println("lase move enebla dice 11" + Dice.enable);
                    System.out.println("change 4");
                    changeTurn(Main.turn);
                    Gui.turn();
                }
                System.out.println("lase move enebla dice 12" + Dice.enable);

                return bool;
            }
        }
        System.out.println("lase move enebla dice 2" + Dice.enable);
        return false;
    }

    public static void changeTurn(Type type) {
        if (Dice.num != 6) {
            System.out.println("in change turn");
            switch (type) {
                case BLUE:
                    Main.turn = Type.RED;
                    break;
                case RED:
                    Main.turn = Type.GREEN;
                    break;

                case GREEN:
                    Main.turn = Type.YELLOW;
                    break;
                case YELLOW:
                    Main.turn = Type.BLUE;
                    break;
            }
            System.out.println("turn in method " + Main.turn);
        }
    }

    private static boolean checkCoin(Type type) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].getCoin() != null && board[i][j].getCoin().type.equals(type)) {
                    if (board[i][j].getCoin().getNumber() + Dice.num < Path.pathX[0].length && board[i][j].getCoin().getNumber() + Dice.num >= 0) {
                        int x = Path.pathX[board[i][j].getCoin().getRow()][board[i][j].getCoin().getNumber() + Dice.num];
                        int y = Path.pathY[board[i][j].getCoin().getRow()][board[i][j].getCoin().getNumber() + Dice.num];
                        if (board[x][y].getCoin() == null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
