package com.example.checkers;

import android.os.Debug;
import android.util.Log;

public class CheckersPieces {
private boolean Black;
private int Board[][];
private int PiecesCount;
private String DebugLog;
public CheckersPieces() {
    Board=new int[8][8];
    Black=true;
    PiecesCount=0;
}
public CheckersPieces(boolean PiecesColor, int NumberOfPieces) {
    Log.d(DebugLog, "LOG: Initializing PiecesColor="+String.valueOf(PiecesColor));
    Black=PiecesColor;
    boolean BlackSw=true;
    Board = new int[8][8];
    PiecesCount=NumberOfPieces;
    int Pieces=NumberOfPieces;
    if (PiecesColor) { // true: top side of the board.
            for (int i = 0; i < 8; i++) {
                BlackSw = !BlackSw;
                for (int j = 0; j < 8; j++) {
                    if (BlackSw && Pieces>0) {
                        Board[i][j] = 1;
                        Log.d(DebugLog,"LOG: Board"+String.valueOf(i)+String.valueOf(j)+": "+String.valueOf(Board[i][j]));
                        Pieces=Pieces-1;
                    } else Board[i][j]=0;
                    BlackSw = !BlackSw;
                }
            }
    }
    if(!PiecesColor) { // false: lower side of the board.
            for (int i = 7; i >= 0; i--) {
                BlackSw = !BlackSw;
                for (int j = 7; j >= 0; j--) {
                    if (BlackSw && Pieces>0) {
                        Board[i][j] = 1;
                        Log.d(DebugLog,"LOG: Board"+String.valueOf(i)+String.valueOf(j)+": "+String.valueOf(Board[i][j]));
                        Pieces=Pieces-1;
                    } else Board[i][j]=0;
                    BlackSw = !BlackSw;
                }
            }
    }
}

    public int[][] getBoard() {
        return Board;
    }

    public boolean isBlack() {
        return Black;
    }

    public int getPiecesCount() {
        return PiecesCount;
    }

    public void Move(int[]OldPos, int[]NewPos) {
        Board[NewPos[0]][NewPos[1]]=Board[OldPos[0]][OldPos[1]];
        Board[OldPos[0]][OldPos[1]]=0;
    }
    public void RmPiece(int[]Pos) {
        Board[Pos[0]][Pos[1]]=0;
        PiecesCount=PiecesCount-1;
    }
    public void Crown(int[]Pos) {
        Board[Pos[0]][Pos[1]]=2;
    }
    public boolean getCrownStatus(int[]Pos) {
        return Board[Pos[0]][Pos[1]] == 2;
    }
}
