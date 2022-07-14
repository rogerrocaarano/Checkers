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
    }

    public void updateBoards() {
        BlackBoard = BlackPieces.getBoard();
        RedBoard = RedPieces.getBoard();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                Board[i][j] = BlackBoard[i][j] > 0 || RedBoard[i][j] > 0;
            }
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

    public int[][] getPlayerBoard(boolean isBlack) {
        return isBlack ? getBlackBoard() : getRedBoard();
    }

    /* TODO: Add king-eating conditions */
    public boolean[][] getCanEatBoard(boolean isBlack) {
        boolean[][] RegularCanEatBoard = getRegularCanEatBoard(isBlack);
        boolean[][] KingCanEatBoard = getKingCanEatBoard(isBlack);
        boolean[][] CanEatBoard = new boolean[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (RegularCanEatBoard[i][j] || KingCanEatBoard[i][j])
                    CanEatBoard[i][j] = true;
            }
        return CanEatBoard;
    }

    public boolean[][] getRegularCanEatBoard(boolean isBlack) {
        boolean[][] CanEatBoard = new boolean[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                CanEatBoard[i][j] = false;
            }
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                /* For black Pieces */
                if (isBlack && i <= 5) {
                    switch (j) {
                        case 0:
                        case 1: {
                            if (!Board[i + 2][j + 2])
                                if (BlackBoard[i][j] > 0 && RedBoard[i + 1][j + 1] > 0)
                                    CanEatBoard[i][j] = true;
                            break;
                        }
                        case 6:
                        case 7: {
                            if (!Board[i + 2][j - 2])
                                if (BlackBoard[i][j] > 0 && RedBoard[i + 1][j - 1] > 0)
                                    CanEatBoard[i][j] = true;
                            break;
                        }
                        default: {
                            if (!Board[i + 2][j - 2])
                                if (BlackBoard[i][j] > 0 && RedBoard[i + 1][j - 1] > 0)
                                    CanEatBoard[i][j] = true;
                            if (!Board[i + 2][j + 2])
                                if (BlackBoard[i][j] > 0 && RedBoard[i + 1][j + 1] > 0)
                                    CanEatBoard[i][j] = true;
                        }
                        break;
                    }
                }
                /* For red Pieces */
                if (!isBlack && i >= 2) {
                    switch (j) {
                        case 0:
                        case 1: {
                            if (!Board[i - 2][j + 2])
                                if (RedBoard[i][j] > 0 && BlackBoard[i - 1][j + 1] > 0)
                                    CanEatBoard[i][j] = true;
                            break;
                        }
                        case 6:
                        case 7: {
                            if (!Board[i - 2][j - 2])
                                if (RedBoard[i][j] > 0 && BlackBoard[i - 1][j - 1] > 0)
                                    CanEatBoard[i][j] = true;
                            break;
                        }
                        default: {
                            if (!Board[i - 2][j - 2])
                                if (RedBoard[i][j] > 0 && BlackBoard[i - 1][j - 1] > 0)
                                    CanEatBoard[i][j] = true;
                            if (!Board[i - 2][j + 2])
                                if (RedBoard[i][j] > 0 && BlackBoard[i - 1][j + 1] > 0)
                                    CanEatBoard[i][j] = true;
                        }
                        break;
                    }
                }
            }
        return CanEatBoard;
    }

    public boolean[][] getKingCanEatBoard(boolean isBlack) {
        boolean[][] CanEatBoard = new boolean[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                CanEatBoard[i][j] = false;
            }
        for (int i_Pos = 0; i_Pos < 8; i_Pos++)
            for (int j_Pos = 0; j_Pos < 8; j_Pos++) {
                int[] Pos = {i_Pos, j_Pos};
                if (getPlayerBoard(isBlack)[i_Pos][j_Pos] == 2) {
                    for (int i = Pos[0] + 1, j = Pos[1] + 1; i < 7 && j < 7; i++, j++)
                        if (Board[i][j] && getPlayerBoard(!isBlack)[i][j] > 0 && !Board[i + 1][j + 1])
                            CanEatBoard[i_Pos][j_Pos] = true;
                    for (int i = Pos[0] + 1, j = Pos[1] - 1; i < 7 && j > 0; i++, j--)
                        if (Board[i][j] && getPlayerBoard(!isBlack)[i][j] > 0 && !Board[i + 1][j - 1])
                            CanEatBoard[i_Pos][j_Pos] = true;
                    for (int i = Pos[0] - 1, j = Pos[1] + 1; i > 0 && j < 7; i--, j++)
                        if (Board[i][j] && getPlayerBoard(!isBlack)[i][j] > 0 && !Board[i - 1][j + 1])
                            CanEatBoard[i_Pos][j_Pos] = true;
                    for (int i = Pos[0] - 1, j = Pos[1] - 1; i > 0 && j > 0; i--, j--)
                        if (Board[i][j] && getPlayerBoard(!isBlack)[i][j] > 0 && !Board[i - 1][j - 1])
                            CanEatBoard[i_Pos][j_Pos] = true;
                }
            }
        return CanEatBoard;
    }

    public boolean[][] getAvailableMovementEatBoard(int[] Pos, boolean isBlack) {
        if (getPlayerBoard(isBlack)[Pos[0]][Pos[1]] == 2) {
            return getKingAvailableMovementEatBoard(Pos, isBlack);
        } else return getRegularAvailableMovementEatBoard(Pos, isBlack);
    }


    public boolean[][] getKingAvailableMovementEatBoard(int[] Pos, boolean isBlack) {
        boolean[][] AvailableMovementBoard = new boolean[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                AvailableMovementBoard[i][j] = false;
            }
        for (int i = Pos[0] + 1, j = Pos[1] + 1; i < 7 && j < 7; i++, j++) {
            if (getPlayerBoard(!isBlack)[i][j] > 0 && !Board[i + 1][j + 1])
                for (int i_Available = i + 1, j_Available = j + 1;
                     i_Available < 7 && j_Available < 7 && !Board[i_Available][j_Available];
                     i_Available++, j_Available++) {
                    AvailableMovementBoard[i_Available][j_Available] = true;
                }
        }
        for (int i = Pos[0] + 1, j = Pos[1] - 1; i < 7 && j > 0; i++, j--) {
            if (getPlayerBoard(!isBlack)[i][j] > 0 && !Board[i + 1][j - 1])
                for (int i_Available = i + 1, j_Available = j - 1;
                     i_Available < 7 && j_Available > 0 && !Board[i_Available][j_Available];
                     i_Available++, j_Available--)
                    AvailableMovementBoard[i_Available][j_Available] = true;
        }
        for (int i = Pos[0] - 1, j = Pos[1] + 1; i > 0 && j < 7; i--, j++) {
            if (getPlayerBoard(!isBlack)[i][j] > 0 && !Board[i - 1][j + 1])
                for (int i_Available = i - 1, j_Available = j + 1;
                     i_Available > 0 && j_Available < 7 && !Board[i_Available][j_Available];
                     i_Available--, j_Available++)
                    AvailableMovementBoard[i_Available][j_Available] = true;
        }
        for (int i = Pos[0] - 1, j = Pos[1] - 1; i > 0 && j > 0; i--, j--) {
            if (getPlayerBoard(!isBlack)[i][j] > 0 && !Board[i - 1][j - 1])
                for (int i_Available = i - 1, j_Available = j - 1;
                     i_Available > 0 && j_Available > 0 && !Board[i_Available][j_Available];
                     i_Available--, j_Available--)
                    AvailableMovementBoard[i_Available][j_Available] = true;
        }
        return AvailableMovementBoard;
    }

    public int[] getKingEatPos(int[] InitialPos, int[] FinalPos, boolean isBlack) {
        int[] EatPos = new int[2];
        if (FinalPos[0] > InitialPos[0]) {
            if (FinalPos[1] > InitialPos[1]) {
                for (int i = InitialPos[0] + 1, j = InitialPos[1] + 1; i < FinalPos[0] && j < FinalPos[1]; i++, j++)
                    if (getPlayerBoard(!isBlack)[i][j] > 0 && !Board[i + 1][j + 1]) {
                        EatPos[0] = i;
                        EatPos[1] = j;
                    }
            } else {
                for (int i = InitialPos[0] + 1, j = InitialPos[1] - 1; i < FinalPos[0] && j > FinalPos[1]; i++, j--)
                    if (getPlayerBoard(!isBlack)[i][j] > 0 && !Board[i + 1][j - 1]) {
                        EatPos[0] = i;
                        EatPos[1] = j;
                    }
            }
        } else {
            if (FinalPos[1] > InitialPos[1]) {
                for (int i = InitialPos[0] - 1, j = InitialPos[1] + 1; i > FinalPos[0] && j < FinalPos[1]; i--, j++)
                    if (getPlayerBoard(!isBlack)[i][j] > 0 && !Board[i - 1][j + 1]) {
                        EatPos[0] = i;
                        EatPos[1] = j;
                    }
            } else {
                for (int i = InitialPos[0] - 1, j = InitialPos[1] - 1; i > FinalPos[0] && j > FinalPos[1]; i--, j--)
                    if (getPlayerBoard(!isBlack)[i][j] > 0 && !Board[i - 1][j - 1]) {
                        EatPos[0] = i;
                        EatPos[1] = j;
                    }
            }
        }
        return EatPos;
    }

    public int[] getRegularEatPos(int[] InitialPos, int[] FinalPos, boolean isBlack) {
        int[] EatPos = new int[2];
        if (isBlack) {
            EatPos[0] = FinalPos[0] - 1;
            if (InitialPos[1] > FinalPos[1]) {
                EatPos[1] = InitialPos[1] - 1;
            } else EatPos[1] = InitialPos[1] + 1;
        } else {
            EatPos[0] = FinalPos[0] + 1;
            if (InitialPos[1] > FinalPos[1]) {
                EatPos[1] = InitialPos[1] - 1;
            } else EatPos[1] = InitialPos[1] + 1;
        }
        return EatPos;
    }

    public boolean[][] getRegularAvailableMovementEatBoard(int[] Pos, boolean Color) {
        boolean[][] AvailableMovementBoard = new boolean[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                AvailableMovementBoard[i][j] = false;
            }
        /* For black Pieces Movement */
        if (Color) {
            switch (Pos[1]) {
                case 0:
                case 1:
                    if (!Board[Pos[0] + 2][Pos[1] + 2] && RedBoard[Pos[0] + 1][Pos[1] + 1] > 0)
                        AvailableMovementBoard[Pos[0] + 2][Pos[1] + 2] = true;
                    break;
                case 6:
                case 7:
                    if (!Board[Pos[0] + 2][Pos[1] - 2] && RedBoard[Pos[0] + 1][Pos[1] - 1] > 0)
                        AvailableMovementBoard[Pos[0] + 2][Pos[1] - 2] = true;
                    break;
                default:
                    if (!Board[Pos[0] + 2][Pos[1] - 2] && RedBoard[Pos[0] + 1][Pos[1] - 1] > 0)
                        AvailableMovementBoard[Pos[0] + 2][Pos[1] - 2] = true;
                    if (!Board[Pos[0] + 2][Pos[1] + 2] && RedBoard[Pos[0] + 1][Pos[1] + 1] > 0)
                        AvailableMovementBoard[Pos[0] + 2][Pos[1] + 2] = true;
                    break;
            }
        }
        /* For red Pieces Movement */
        if (!Color) {
            switch (Pos[1]) {
                case 0:
                case 1:
                    if (!Board[Pos[0] - 2][Pos[1] + 2] && BlackBoard[Pos[0] - 1][Pos[1] + 1] > 0)
                        AvailableMovementBoard[Pos[0] - 2][Pos[1] + 2] = true;
                    break;
                case 6:
                case 7:
                    if (!Board[Pos[0] - 2][Pos[1] - 2] && BlackBoard[Pos[0] - 1][Pos[1] - 1] > 0)
                        AvailableMovementBoard[Pos[0] - 2][Pos[1] - 2] = true;
                    break;
                default:
                    if (!Board[Pos[0] - 2][Pos[1] - 2] && BlackBoard[Pos[0] - 1][Pos[1] - 1] > 0)
                        AvailableMovementBoard[Pos[0] - 2][Pos[1] - 2] = true;
                    if (!Board[Pos[0] - 2][Pos[1] + 2] && BlackBoard[Pos[0] - 1][Pos[1] + 1] > 0)
                        AvailableMovementBoard[Pos[0] - 2][Pos[1] + 2] = true;
                    break;
            }
        }
        return AvailableMovementBoard;
    }

    public boolean[][] getAvailableMovementBoard(int[] Pos, boolean isBlack) {
        if (getPlayerBoard(isBlack)[Pos[0]][Pos[1]] == 2) {
            return getAvailableKingMovementBoard(Pos);
        } else return getRegularAvailableMovementBoard(Pos, isBlack);
    }

    public boolean[][] getRegularAvailableMovementBoard(int[] Pos, boolean Color) {
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

    public boolean[][] getAvailableKingMovementBoard(int[] Pos) {
        boolean[][] AvailableMovementBoard = new boolean[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                AvailableMovementBoard[i][j] = false;
            }
        for (int i = Pos[0] + 1, j = Pos[1] + 1; i < 8 && j < 8 && !Board[i][j]; i++, j++) {
            AvailableMovementBoard[i][j] = true;
        }
        for (int i = Pos[0] + 1, j = Pos[1] - 1; i < 8 && j >= 0 && !Board[i][j]; i++, j--) {
            AvailableMovementBoard[i][j] = true;
        }
        for (int i = Pos[0] - 1, j = Pos[1] + 1; i >= 0 && j < 8 && !Board[i][j]; i--, j++) {
            AvailableMovementBoard[i][j] = true;
        }
        for (int i = Pos[0] - 1, j = Pos[1] - 1; i >= 0 && j >= 0 && !Board[i][j]; i--, j--) {
            AvailableMovementBoard[i][j] = true;
        }
        return AvailableMovementBoard;
    }


    public void doMove(int[] InitialPos, int[] FinalPos, boolean Color) {
        if (Color) {
            BlackPieces.Move(InitialPos, FinalPos);
            if (FinalPos[0] == 7 && BlackBoard[FinalPos[0]][FinalPos[1]] == 1)
                BlackPieces.Crown(FinalPos);
        }
        if (!Color) {
            RedPieces.Move(InitialPos, FinalPos);
            if (FinalPos[0] == 0 && RedBoard[FinalPos[0]][FinalPos[1]] == 1)
                RedPieces.Crown(FinalPos);
        }
        updateBoards();
    }

    public void doEat(int[] InitialPos, int[] FinalPos, int[] Eat, boolean Color) {
        if (!Color)
            BlackPieces.RmPiece(Eat);
        if (Color)
            RedPieces.RmPiece(Eat);
        doMove(InitialPos, FinalPos, Color);
    }
    public int[] getPiecesCount() {
        return new int[]{BlackPieces.getPiecesCount(),RedPieces.getPiecesCount()};
    }
}
