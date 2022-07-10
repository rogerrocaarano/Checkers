package com.example.checkers;

public class CheckersGame {
    private boolean Board[][];
    private int[][] BlackBoard;
    private int[][] RedBoard;
    private CheckersPieces BlackPieces;
    private CheckersPieces RedPieces;

    public CheckersGame() {
        Board = new boolean[8][8];
        BlackBoard = new int[8][8];
        RedBoard = new int[8][8];
        BlackPieces = new CheckersPieces(true, 12);
        RedPieces = new CheckersPieces(false, 12);
        updateBoards();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                Board[i][j] = BlackBoard[i][j] > 0 || RedBoard[i][j] > 0;
            }
    }

    public void updateBoards() {
        BlackBoard = BlackPieces.getBoard();
        RedBoard = RedPieces.getBoard();
    }

    public boolean[][] getBoard() {
        return Board;
    }

    public int[][] getBlackBoard() {
        return BlackBoard;
    }

    public int[][] getRedBoard() {
        return RedBoard;
    }

    /* TODO: Add king-eating conditions */
    public boolean[][] getCanEatBoard(boolean Color) {
        boolean[][] CanEatBoard = new boolean[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                CanEatBoard[i][j] = false;
            }
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                /* For black Pieces */
                if (Color) {
                    if (i + 2 <= 7 && j - 2 >= 0 && !Board[i + 2][j - 2])
                        if (BlackBoard[i][j] > 0 && RedBoard[i + 1][j - 1] > 0)
                            CanEatBoard[i][j] = true;
                    if (i + 2 <= 7 && j + 2 <= 7 && !Board[i + 2][j + 2])
                        if (BlackBoard[i][j] > 0 && RedBoard[i + 1][j + 1] > 0)
                            CanEatBoard[i][j] = true;
                }
                /* For red Pieces */
                if (!Color) {
                    if (i - 2 >= 0 && j - 2 >= 0 && !Board[i - 2][j - 2])
                        if (RedBoard[i][j] > 0 && BlackBoard[i - 1][j - 1] > 0)
                            CanEatBoard[i][j] = true;
                    if (i - 2 >= 0 && j + 2 <= 7 && !Board[i - 2][j + 2])
                        if (RedBoard[i][j] > 0 && BlackBoard[i - 1][j + 1] > 0)
                            CanEatBoard[i][j] = true;
                }
            }
        return CanEatBoard;
    }

    /* TODO: Add King movement */
    public boolean[][] getAvailableMovementBoard(int[] Pos, boolean Color) {
        boolean[][] AvailableMovementBoard = new boolean[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                AvailableMovementBoard[i][j] = false;
            }
        /* For black Pieces Movement */
        if (Color) {
            switch (Pos[1]) {
                case 0:
                    if (!Board[Pos[0] + 1][Pos[1] + 1])
                        AvailableMovementBoard[Pos[0] + 1][Pos[1] + 1] = true;
                    break;
                case 7:
                    if (!Board[Pos[0] + 1][Pos[1] - 1])
                        AvailableMovementBoard[Pos[0] + 1][Pos[1] - 1] = true;
                    break;
                default:
                    if (!Board[Pos[0] + 1][Pos[1] - 1])
                        AvailableMovementBoard[Pos[0] + 1][Pos[1] - 1] = true;
                    if (!Board[Pos[0] + 1][Pos[1] + 1])
                        AvailableMovementBoard[Pos[0] + 1][Pos[1] + 1] = true;
                    break;
            }
        }
        /* For red Pieces Movement */
        if (!Color) {
            switch (Pos[1]) {
                case 0:
                    if (!Board[Pos[0] - 1][Pos[1] + 1])
                        AvailableMovementBoard[Pos[0] - 1][Pos[1] + 1] = true;
                    break;
                case 7:
                    if (!Board[Pos[0] - 1][Pos[1] - 1])
                        AvailableMovementBoard[Pos[0] - 1][Pos[1] - 1] = true;
                    break;
                default:
                    if (!Board[Pos[0] - 1][Pos[1] - 1])
                        AvailableMovementBoard[Pos[0] - 1][Pos[1] - 1] = true;
                    if (!Board[Pos[0] - 1][Pos[1] + 1])
                        AvailableMovementBoard[Pos[0] - 1][Pos[1] + 1] = true;
                    break;
            }
        }
        return AvailableMovementBoard;
    }
}
