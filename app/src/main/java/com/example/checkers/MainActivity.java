package com.example.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import javax.xml.validation.Validator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /* Initialize Board */
    private ImageButton[][] BoardButtons;
    private CheckersGame Checkers;
    private String DebugLog;
    private boolean isBlackTurn;
    private boolean ValidSelection;
    private int[] LastValidSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BoardButtons = new ImageButton[8][8];

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
        newGame();
    }

    public void newGame(){
        Checkers = new CheckersGame();
        updateBoard();
        isBlackTurn = false;
        PlayerTurnText();
        PiecesText();
        ValidSelection = false;
        LastValidSelection = new int[2];
    }

    @Override
    public void onClick(View view) {
        int[] ClickedButtonPos = new int[2];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (view.getId() == BoardButtons[i][j].getId()) {
                    ClickedButtonPos[0] = i;
                    ClickedButtonPos[1] = j;
                }
        if (!ValidSelection) {
            if (PlayerCanEat()) {
                showAvailableEatMovements(ClickedButtonPos);
            } else {
                showAvailableMovements(ClickedButtonPos);
            }
        } else {
            if (PlayerCanEat()) {
                doPlayerEat(LastValidSelection, ClickedButtonPos);
            } else {
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
                    if (BlackBoard[i][j] == 1)
                        BoardButtons[i][j].setImageResource(R.drawable.piece_black);
                    if (BlackBoard[i][j] == 2)
                        BoardButtons[i][j].setImageResource(R.drawable.piece_black_king);
                }
                if (RedBoard[i][j] > 0) {
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


    public void showAvailableMovements(int[] Pos) {
        resetButtonsColor();
        boolean[][] AvailableMovementBoard = new boolean[8][8];
        if (Checkers.getPlayerBoard(isBlackTurn)[Pos[0]][Pos[1]] > 0) {
            AvailableMovementBoard = Checkers.getAvailableMovementBoard(Pos, isBlackTurn);
            ValidSelection = true;
        } else Toast.makeText(this, "Selección inválida.", Toast.LENGTH_SHORT).show();
        if (ValidSelection) {
            LastValidSelection = Pos;
        }
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (AvailableMovementBoard[i][j]) {
                    BoardButtons[i][j].setBackgroundColor(Color.parseColor("#99FFD449"));
                }
    }

    public void showAvailableEatMovements(int[] Pos) {
        resetButtonsColor();
        boolean[][] AvailableMovementBoard = new boolean[8][8];
        if (validateCanEat(Pos)) {
            if (Checkers.getPlayerBoard(isBlackTurn)[Pos[0]][Pos[1]] > 0) {
                AvailableMovementBoard = Checkers.getAvailableMovementEatBoard(Pos, isBlackTurn);
                ValidSelection = true;
            }
        } else {
            Toast.makeText(this, "Puede comer con éstas fichas.", Toast.LENGTH_SHORT).show();
            showCanEat();
        }
        if (ValidSelection) {
            LastValidSelection = Pos;
        }
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (AvailableMovementBoard[i][j]) {
                    BoardButtons[i][j].setBackgroundColor(Color.parseColor("#99FFD449"));
                }
    }


    public boolean PlayerCanEat() {
        boolean[][] CanEatBoard = Checkers.getCanEatBoard(isBlackTurn);
        boolean CanEat = false;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (CanEatBoard[i][j]) {
                    CanEat = true;
                    break;
                }
        return CanEat;
    }

    public boolean validateCanEat(int[] Pos) {
        boolean[][] CanEatBoard;
        CanEatBoard = Checkers.getCanEatBoard(isBlackTurn);
        return CanEatBoard[Pos[0]][Pos[1]];
    }

    public void showCanEat() {
        boolean[][] CanEatBoard;
        CanEatBoard = Checkers.getCanEatBoard(isBlackTurn);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (CanEatBoard[i][j]) {
                    BoardButtons[i][j].setBackgroundColor(Color.parseColor("#99FFD449"));
                }
    }

    public void doPlayerMove(int[] InitialPos, int[] FinalPos) {
        boolean[][] AvailableMovementBoard = new boolean[8][8];
        if (Checkers.getPlayerBoard(isBlackTurn)[InitialPos[0]][InitialPos[1]] > 0)
            AvailableMovementBoard = Checkers.getAvailableMovementBoard(InitialPos, isBlackTurn);
        if (AvailableMovementBoard[FinalPos[0]][FinalPos[1]]) {
            Checkers.doMove(InitialPos, FinalPos, isBlackTurn);
            resetButtonsColor();
            updateBoard();
            ValidSelection = false;
            isBlackTurn = !isBlackTurn;
            PlayerTurnText();
        } else {
            resetButtonsColor();
            Toast.makeText(this, "Movimiento inválido", Toast.LENGTH_SHORT).show();
            ValidSelection = false;
        }
    }

    public void doPlayerEat(int[] InitialPos, int[] FinalPos) {
        boolean[][] AvailableMovementBoard = new boolean[8][8];
        int[] Eat;
        if (Checkers.getPlayerBoard(isBlackTurn)[InitialPos[0]][InitialPos[1]] > 0)
            AvailableMovementBoard = Checkers.getAvailableMovementEatBoard(InitialPos, isBlackTurn);
        if (AvailableMovementBoard[FinalPos[0]][FinalPos[1]]) {
            if (Checkers.getPlayerBoard(isBlackTurn)[InitialPos[0]][InitialPos[1]] == 2) {
                Eat = Checkers.getKingEatPos(InitialPos, FinalPos, isBlackTurn);
            } else {
                Eat = Checkers.getRegularEatPos(InitialPos, FinalPos, isBlackTurn);
            }
            Checkers.doEat(InitialPos, FinalPos, Eat, isBlackTurn);
            PiecesText();
            resetButtonsColor();
            updateBoard();
            ValidSelection = false;
            isBlackTurn = !isBlackTurn;
            PlayerTurnText();
        } else {
            resetButtonsColor();
            Toast.makeText(this, "Movimiento inválido", Toast.LENGTH_SHORT).show();
            ValidSelection = false;
        }
    }

    public void resetButtonsColor() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                BoardButtons[i][j].setBackgroundColor(Color.parseColor("#00FFFFFF"));
    }

    public void PlayerTurnText() {
        String t = isBlackTurn ? "Juegan las negras" : "Juegan las rojas";
        ((TextView)findViewById(R.id.PlayerTurn)).setText(t);

    }
    public void PiecesText() {
        ((TextView)findViewById(R.id.Red_PiecesCount)).setText(String.valueOf("Fichas Rojas: "+Checkers.getPiecesCount()[1]));
        ((TextView)findViewById(R.id.Black_PiecesCount)).setText(String.valueOf("Fichas Negras: "+Checkers.getPiecesCount()[0]));
    }
    public void RestartGame(View v) {
        newGame();
    }


}