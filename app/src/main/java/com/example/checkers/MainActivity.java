package com.example.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /* Initialize Board */
    private ImageButton[][] BoardButtons;
    private CheckersGame Checkers;
    private String DebugLog;

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
        updateBoard();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                BoardButtons[i][j].setOnClickListener(this);
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
                }
            }
    }

    public void showAvailableMovements(int[]Pos) {
        resetButtonsColor();
        boolean[][]AvailableMovementBoard=Checkers.getAvailableMovementBoard(Pos,true);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (AvailableMovementBoard[i][j]) {
                    BoardButtons[i][j].setBackgroundColor(Color.parseColor("#99FFD449"));
                }
            }
    }

    public void resetButtonsColor() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                BoardButtons[i][j].setBackgroundColor(Color.parseColor("#00FFFFFF"));
            }
    }

    @Override
    public void onClick(View view) {
        int[]ClickedButtonPos=new int[2];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (view.getId()==BoardButtons[i][j].getId()) {
                    ClickedButtonPos[0]=i;
                    ClickedButtonPos[1]=j;
                    Toast.makeText(this,"button"+String.valueOf(i)+String.valueOf(j),Toast.LENGTH_SHORT).show();
                }
            }
        showAvailableMovements(ClickedButtonPos);
    }
}