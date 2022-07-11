package com.example.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import javax.xml.validation.Validator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /* Initialize Board */
    private ImageButton[][] BoardButtons;
    private CheckersGame Checkers;
    private String DebugLog;
    private boolean isBlackTurn;
    private boolean ValidSelection;
    private int[]LastValidSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BoardButtons = new ImageButton[8][8];
        Checkers = new CheckersGame();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                String BoardButtonID = "B_" + i + j;
                int BoardResID = getResources().getIdentifier(BoardButtonID, "id", getPackageName());
                BoardButtons[i][j] = findViewById(BoardResID);
            }
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                BoardButtons[i][j].setOnClickListener(this);
            }
        updateBoard();
        isBlackTurn = false;
        ValidSelection=false;
        LastValidSelection=new int[2];
    }

    @Override
    public void onClick(View view) {
        int[] ClickedButtonPos = new int[2];
        if (validateCanEat() && !ValidSelection) {
            showCanEat();
        } else {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    if (view.getId() == BoardButtons[i][j].getId()) {
                        ClickedButtonPos[0] = i;
                        ClickedButtonPos[1] = j;
                    }
            Log.d(DebugLog, "LOG: onClick::ValidSelection:"+String.valueOf(ValidSelection)+" Clicked::"+String.valueOf(ClickedButtonPos[0])+String.valueOf(ClickedButtonPos[1]));
            if (!ValidSelection) {
                showAvailableMovements(ClickedButtonPos);
            } else {
                Log.d(DebugLog,"LOG: Sent to doPlayerMove Procedure...");
                doPlayerMove(LastValidSelection, ClickedButtonPos);
            }
        }
    }

    public void updateBoard() {
        boolean[][] Board = Checkers.getBoard();
        int[][] BlackBoard = Checkers.getBlackBoard();
        int[][] RedBoard = Checkers.getRedBoard();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (BlackBoard[i][j] > 0) {
                    Log.d(DebugLog, "LOG: BlackBoard" + String.valueOf(i) + String.valueOf(j) + ": " + String.valueOf(BlackBoard[i][j]));
                    if (BlackBoard[i][j] == 1)
                        BoardButtons[i][j].setImageResource(R.drawable.piece_black);
                    if (BlackBoard[i][j] == 2)
                        BoardButtons[i][j].setImageResource(R.drawable.piece_black_king);
                }
                if (RedBoard[i][j] > 0) {
                    Log.d(DebugLog, "LOG: RedBoard" + String.valueOf(i) + String.valueOf(j) + ": " + String.valueOf(RedBoard[i][j]));
                    if (RedBoard[i][j] == 1)
                        BoardButtons[i][j].setImageResource(R.drawable.piece_red);
                    if (RedBoard[i][j] == 2)
                        BoardButtons[i][j].setImageResource(R.drawable.piece_red_king);
                }
                if (!Board[i][j]) {
                    BoardButtons[i][j].setImageResource(R.drawable.piece_transparent);
                    Log.d(DebugLog, "LOG: Board::Transparent" + String.valueOf(i) + String.valueOf(j) + ": " + String.valueOf(RedBoard[i][j]));
                }
            }
    }


    public void showAvailableMovements(int[] Pos) {
        resetButtonsColor();
        boolean[][] AvailableMovementBoard = new boolean[8][8];
        int[][] BlackBoard = Checkers.getBlackBoard();
        int[][] RedBoard = Checkers.getRedBoard();
        if (isBlackTurn && BlackBoard[Pos[0]][Pos[1]] > 0) {
            AvailableMovementBoard = Checkers.getAvailableMovementBoard(Pos, true);
            ValidSelection=true;
            Log.d(DebugLog, "LOG: showAvailableMovements::Valid Selection:: Black:"+String.valueOf(Pos[0])+String.valueOf(Pos[1]));
        }
        else if (!isBlackTurn && RedBoard[Pos[0]][Pos[1]] > 0) {
            AvailableMovementBoard = Checkers.getAvailableMovementBoard(Pos, false);
            ValidSelection=true;
            Log.d(DebugLog, "LOG: showAvailableMovements::Valid Selection:: Red:"+String.valueOf(Pos[0])+String.valueOf(Pos[1]));
        }
        else Toast.makeText(this, "Selección inválida.", Toast.LENGTH_SHORT).show();
        if (ValidSelection) {
            LastValidSelection=Pos;
        }
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (AvailableMovementBoard[i][j]) {
                    BoardButtons[i][j].setBackgroundColor(Color.parseColor("#99FFD449"));
                }
    }

    public boolean validateCanEat() {
        boolean[][] CanEatBoard;
        CanEatBoard= Checkers.getCanEatBoard(isBlackTurn);
        boolean CanEat=false;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (CanEatBoard[i][j]) {
                    CanEat=true;
                }
        return CanEat;
    }

    public void showCanEat() {
        boolean[][] CanEatBoard;
        CanEatBoard= Checkers.getCanEatBoard(isBlackTurn);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (CanEatBoard[i][j]) {
                    BoardButtons[i][j].setBackgroundColor(Color.parseColor("#99FFD449"));
                }
    }

    public void doPlayerMove(int[]InitialPos,int[]FinalPos) {
        Log.d(DebugLog,"LOG: doPlayerMove::InitialPos:"+String.valueOf(InitialPos[0])+String.valueOf(InitialPos[1])+" FinalPos:"+String.valueOf(FinalPos[0])+String.valueOf(FinalPos[1]));
        boolean[][] AvailableMovementBoard = new boolean[8][8];
        int[][] BlackBoard = Checkers.getBlackBoard();
        int[][] RedBoard = Checkers.getRedBoard();
        if (isBlackTurn && BlackBoard[InitialPos[0]][InitialPos[1]] > 0)
            AvailableMovementBoard = Checkers.getAvailableMovementBoard(InitialPos, true);
        if (!isBlackTurn && RedBoard[InitialPos[0]][InitialPos[1]] > 0)
            AvailableMovementBoard = Checkers.getAvailableMovementBoard(InitialPos, false);
        Log.d(DebugLog,"LOG: doPlayerMove::AvailableMovementBoard"+String.valueOf(AvailableMovementBoard[FinalPos[0]][FinalPos[1]]));
        if (AvailableMovementBoard[FinalPos[0]][FinalPos[1]]) {
            Log.d(DebugLog,"LOG: doPlayerMove::Moving... InitialPos:"+String.valueOf(InitialPos[0])+String.valueOf(InitialPos[1])+" FinalPos:"+String.valueOf(FinalPos[0])+String.valueOf(FinalPos[1])+" isBlackTurn:"+String.valueOf(isBlackTurn));
            Checkers.doMove(InitialPos,FinalPos,isBlackTurn);
            resetButtonsColor();
            updateBoard();
            ValidSelection=false;
            isBlackTurn=!isBlackTurn;
        } else {
            resetButtonsColor();
            Toast.makeText(this, "Movimiento inválido", Toast.LENGTH_SHORT).show();
            ValidSelection=false;
        }
    }

    public void resetButtonsColor() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                BoardButtons[i][j].setBackgroundColor(Color.parseColor("#00FFFFFF"));
    }


}