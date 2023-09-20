package Tiles;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private Tile[][] board;
    private Tile sampleTile = new Tile(0, 0); //This tile is declared and initialized just to get the tile height and width
    private int player1XPos = -1;
    private int player1YPos = 9;
    private int player1TileNumber;
    private int player2XPos = -1;
    private int player2YPos = 9;
    private int player2TileNumber;
    private List<Integer> ladders = new ArrayList<>();
    private List<Integer> ladderDestination = new ArrayList<>();
    private List<Integer> snakes = new ArrayList<>();
    private List<Integer> snakeDestination = new ArrayList<>();

    public Board(JFrame frame, int x, int y) {

        int gap = 1;
        int tileHeight = sampleTile.getHeight();
        int tileWidth = sampleTile.getWidth();

        board = new Tile[y][x];

        frame.setVisible(true);

        for (int i = 0, yPos = 0; i < board.length; i++, yPos += (tileHeight + gap)) {
            for (int j = 0, xPos = 0; j < board[i].length; j++, xPos += (tileWidth + gap)) {
                board[i][j] = new Tile(xPos, yPos);
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                frame.add(board[i][j]);
            }
        }

        ladders.add(1);
        ladders.add(4);
        ladders.add(9);
        ladders.add(21);
        ladders.add(28);
        ladders.add(51);
        ladders.add(71);
        ladders.add(80);

        ladderDestination.add(38);
        ladderDestination.add(14);
        ladderDestination.add(31);
        ladderDestination.add(42);
        ladderDestination.add(84);
        ladderDestination.add(67);
        ladderDestination.add(91);
        ladderDestination.add(100);

        snakes.add(17);
        snakes.add(54);
        snakes.add(62);
        snakes.add(64);
        snakes.add(87);
        snakes.add(93);
        snakes.add(95);
        snakes.add(98);

        snakeDestination.add(7);
        snakeDestination.add(34);
        snakeDestination.add(19);
        snakeDestination.add(60);
        snakeDestination.add(24);
        snakeDestination.add(73);
        snakeDestination.add(75);
        snakeDestination.add(79);

    }

    public Tile[][] getBoard() {
        return board;
    }

    public void numberBoards() {
        int currentTileNumber = 1;

        for (int i = board.length - 1; i >= 0; i--) {
            if (i % 2 == 1)
                for (int j = 0; j < board[i].length; j++, currentTileNumber++) {
                    board[i][j].setText("" + currentTileNumber);
                }
            else
                for (int j = 9; j >= 0; j--, currentTileNumber++) {
                    board[i][j].setText("" + currentTileNumber);
                }
        }

    }

    //mulihok kaisa si player 1
    public void moveOnceP1() {
        Tile currentPos = board[player1YPos][(player1XPos == -1) ? 0 : player1XPos];
        int lastXPos = player1XPos;

        if (player1YPos % 2 == 1) {
            player1XPos = (player1XPos == 9) ? 9 : ++player1XPos;
            player1YPos = (lastXPos == 9) ? --player1YPos : player1YPos;
        } else {
            player1XPos = (player1XPos == 0) ? 0 : --player1XPos;
            player1YPos = (lastXPos == 0) ? --player1YPos : player1YPos;
        }

        currentPos.removeP1();
        board[player1YPos][player1XPos].occupyP1();
        player1TileNumber = board[player1YPos][player1XPos].getTileNumber();
    }

    //mulihok kaisa si player 2
    public void moveOnceP2() {
        Tile currentPos = board[player2YPos][(player2XPos == -1) ? 0 : player2XPos];
        int lastXPos = player2XPos;

        if (player2YPos % 2 == 1) {
            player2XPos = (player2XPos == 9) ? 9 : ++player2XPos;
            player2YPos = (lastXPos == 9) ? --player2YPos : player2YPos;
        } else {
            player2XPos = (player2XPos == 0) ? 0 : --player2XPos;
            player2YPos = (lastXPos == 0) ? --player2YPos : player2YPos;
        }

        currentPos.removeP2();
        board[player2YPos][player2XPos].occupyP2();
        player2TileNumber = board[player2YPos][player2XPos].getTileNumber();
    }

    //mulihok kaisa si player 1 pabalik
    public void moveBackwardOnceP1() {
        Tile currentPos = board[player1YPos][(player1XPos == -1) ? 0 : player1XPos];
        int lastXPos = player1XPos;

        if (player1YPos % 2 == 1) {
            player1XPos = (player1XPos == 0) ? 0 : --player1XPos;
            player1YPos = (lastXPos == 0) ? ++player1YPos : player1YPos;
        } else {
            player1XPos = (player1XPos == 9) ? 9 : ++player1XPos;
            player1YPos = (lastXPos == 9) ? ++player1YPos : player1YPos;
        }

        currentPos.removeP1();
        board[player1YPos][player1XPos].occupyP1();
        player1TileNumber = board[player1YPos][player1XPos].getTileNumber();
    }

    //mulihok kaisa si player 2 pabalik
    public void moveBackwardOnceP2() {
        Tile currentPos = board[player2YPos][(player2XPos == -1) ? 0 : player2XPos];
        int lastXPos = player2XPos;

        if (player2YPos % 2 == 1) {
            player2XPos = (player2XPos == 0) ? 0 : --player2XPos;
            player2YPos = (lastXPos == 0) ? ++player2YPos : player2YPos;
        } else {
            player2XPos = (player2XPos == 9) ? 9 : ++player2XPos;
            player2YPos = (lastXPos == 9) ? ++player2YPos : player2YPos;
        }

        currentPos.removeP2();
        board[player2YPos][player2XPos].occupyP2();
        player2TileNumber = board[player2YPos][player2XPos].getTileNumber();
    }

    //gibutang nimo tanan snake ug ladder
    public void implementSnakesAndLadders() {
        int currentTileNumber = 1;

        for (int i = board.length - 1; i >= 0; i--) {
            if (i % 2 == 1)
                for (int j = 0; j < board[i].length; j++, currentTileNumber++) {
                    if (ladders.contains(currentTileNumber))
                        board[i][j].placeLadder();

                    if (snakes.contains(currentTileNumber))
                        board[i][j].placeSnake();

                    board[i][j].setTileNumber(currentTileNumber);
                }
            else
                for (int j = 9; j >= 0; j--, currentTileNumber++) {
                    if (ladders.contains(currentTileNumber))
                        board[i][j].placeLadder();

                    if (snakes.contains(currentTileNumber))
                        board[i][j].placeSnake();

                    board[i][j].setTileNumber(currentTileNumber);
                }
        }
    }

    public boolean player1IsOnALadder() {
        if (player1XPos == -1) return false;
        return board[player1YPos][player1XPos].isLadder();
    }

    public boolean player2IsOnALadder() {
        if (player2XPos == -1) return false;
        return board[player2YPos][player2XPos].isLadder();
    }

    public boolean player1IsOnASnake() {
        if (player1XPos == -1) return false;
        return board[player1YPos][player1XPos].isSnake();
    }

    public boolean player2IsOnASnake() {
        if (player2XPos == -1) return false;
        return board[player2YPos][player2XPos].isSnake();
    }

    //this method returns 0 if the tile number is not a ladder location
    public int getSnakeOrLadderDestination(int tileNumber) {
        for (int i = 0; i < ladders.size(); i++) {
            if (ladders.get(i) == tileNumber)
                return ladderDestination.get(i);
        }

        for (int i = 0; i < snakes.size(); i++) {
            if (snakes.get(i) == tileNumber)
                return snakeDestination.get(i);
        }

        return 0;
    }

    public int getSnakeDestination(int tileNumber){
        for (int i = 0; i < snakes.size(); i++) {
            if (snakes.get(i) == tileNumber)
                return snakeDestination.get(i);
        }
        return 0;
    }

    public void moveP1To(int tileNumber) throws InterruptedException {
        if (board[player1YPos][player1XPos].getTileNumber() < tileNumber)
            while (board[player1YPos][player1XPos].getTileNumber() != tileNumber) {
                moveOnceP1();
                Thread.sleep(100);
            }
        else
            while (board[player1YPos][player1XPos].getTileNumber() != tileNumber) {
                moveBackwardOnceP1();
                Thread.sleep(100);
            }
    }

    public void moveP2To(int tileNumber) throws InterruptedException {
        if (board[player2YPos][player2XPos].getTileNumber() < tileNumber)
            while (board[player2YPos][player2XPos].getTileNumber() != tileNumber) {
                moveOnceP2();
                Thread.sleep(100);
            }
        else {
            while (board[player2YPos][player2XPos].getTileNumber() != tileNumber) {
                moveBackwardOnceP2();
                Thread.sleep(100);
            }
        }
    }

    public int getPlayer1TileNumber() {
        return player1TileNumber;
    }

    public int getPlayer2TileNumber() {
        return player2TileNumber;
    }

}
