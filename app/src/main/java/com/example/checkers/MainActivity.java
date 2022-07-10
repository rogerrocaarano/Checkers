package com.example.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    /* Initialize Board */
    private ImageButton[][] BoardButtons;
    private CheckersGame Checkers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BoardButtons = new ImageButton[8][8];
        Checkers=new CheckersGame();
        for (int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++) {
                String BoardButtonID = "B_" + i + j;
                int BoardResID = getResources().getIdentifier(BoardButtonID, "id", getPackageName());
                BoardButtons[i][j] = findViewById(BoardResID);
            }
        }
        updateBoard();
    }

    public void updateBoard() {
        boolean[][] Board =Checkers.getBoard();
        int[][] BlackBoard =Checkers.getBlackBoard();
        int[][] RedBoard =Checkers.getRedBoard();
        for (int i=0;i<8;i++)
            for (int j=0;j<8;j++) {
                if (BlackBoard[i][j]>0) {
                    switch (BlackBoard[i][j]) {
                        case 1:
                            BoardButtons[i][j].setImageResource(R.drawable.piece_black);
                        case 2:
                            BoardButtons[i][j].setImageResource(R.drawable.piece_black_king);
                    }
                }
                if (RedBoard[i][j]>0) {
                    switch (RedBoard[i][j]) {
                        case 1:
                            BoardButtons[i][j].setImageResource(R.drawable.piece_red);
                        case 2:
                            BoardButtons[i][j].setImageResource(R.drawable.piece_red_king);
                    }
                }
                if (!Board[i][j]) {
                    BoardButtons[i][j].setImageResource(R.drawable.piece_transparent);
                }

            }


    }
}