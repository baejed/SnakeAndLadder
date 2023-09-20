/*
Developed by:
                Roger Madulara
                Alraffy Catao
                Sandara Da-Anoy

                DA-anoy
                MAdulara
                CATao

                DaMadCat productions
 */
import Tiles.Board;
import Tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    static int movesToMake = 5;
    static int movesMade = 0;
    static boolean dieRolled = false;
    static boolean player1Turn = true;
    static boolean doneMoving = false;
    static boolean moveBack = false;

    public static void main(String[] args) throws InterruptedException {

        JFrame gameFrame = new JFrame();
        Board snakeAndLadderBoard = new Board(gameFrame, 10, 10);

        JLabel background = new JLabel();
        JLabel die = new JLabel();
        JLabel textLabel = new JLabel();

        //necessary images
        //most of the images used are just taken from the internet
        ImageIcon boardTexture = new ImageIcon("Snake and Ladder board.png");
        ImageIcon dieIcon = new ImageIcon("die.png");
        ImageIcon appIcon = new ImageIcon("Icon.png");
        ImageIcon[] dieTexture = new ImageIcon[6];
        initializeDieFaces(dieTexture);

        JButton moveButton = new JButton("Move");
        JButton rollDieButton = new JButton("Roll Die");

        background.setBounds(0, 0, 929, 929);
        background.setOpaque(false);
        background.setBackground(Color.WHITE);
        background.setVisible(true);
        background.setIcon(boardTexture);

        die.setBounds(972, 20, 175, 175);
        die.setIcon(dieIcon);
        die.setVisible(true);

        textLabel.setBounds(930, 400, 259, 40);
        textLabel.setText("Player 1's turn");
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setVisible(true);

        gameFrame.setSize(1199, 968);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setLayout(null);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.getContentPane().setBackground(Color.WHITE);
        gameFrame.setResizable(true);
        gameFrame.setTitle("DaMadCat's Snake and Ladder");
        gameFrame.setIconImage(appIcon.getImage());
        gameFrame.setResizable(false);

        rollDieButton.setFont(new Font("Arial", Font.BOLD, 20));
        rollDieButton.setFocusable(false);
        rollDieButton.setBounds(989, 220, 140, 60);

        moveButton.setFont(new Font("Arial", Font.BOLD, 20));
        moveButton.setFocusable(false);
        moveButton.setBounds(989, 800, 140, 60);
        moveButton.setEnabled(false);

        gameFrame.add(background);
        gameFrame.add(die);
        gameFrame.add(textLabel);
        gameFrame.add(moveButton);
        gameFrame.add(rollDieButton);

        //We could've just made moving, automatic.
        //But we did not, instead, we made moving in the board a manual thing to encourage interaction from the player
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (player1Turn && moveBack)
                    snakeAndLadderBoard.moveBackwardOnceP1();
                else if (moveBack)
                    snakeAndLadderBoard.moveBackwardOnceP2();
                else if (player1Turn && !moveBack)
                    snakeAndLadderBoard.moveOnceP1();
                else
                    snakeAndLadderBoard.moveOnceP2();

                if(snakeAndLadderBoard.getPlayer1TileNumber() == 100 || snakeAndLadderBoard.getPlayer2TileNumber() == 100)
                    moveBack = true;

                if (++movesMade == movesToMake) {
                    String text;

                    moveButton.setEnabled(false);
                    rollDieButton.setEnabled(true);
                    movesMade = 0;
                    player1Turn = !player1Turn;
                    doneMoving = true;
                    moveBack = false;

                    text = player1Turn ? "Player 1's turn" : "Player 2's turn";
                    textLabel.setText(text);
                }
            }
        });

        //The button only changes some variable values.
        //The rollDie function is ran inside the "while" loop.
        //The button is disabled after pressing it, to avoid bugs and spamming
        rollDieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dieRolled = true;
                rollDieButton.setEnabled(false);
                doneMoving = false;
            }
        });

        snakeAndLadderBoard.implementSnakesAndLadders();

        while (true) {

            //Rolls the dice, if the "Roll" button is pressed
            if (dieRolled) {
                movesToMake = rollDie(dieTexture, die, moveButton);
                dieRolled = false;
            }

            //This block contains the necessary actions for player 1 if he/she lands on either a snake or a ladder.
            //We could've just made the code block a method, but it was too late, it would take us a lot of debugging
            //to optimize it, so we just left it as it is. Either way, it works.
            if ((snakeAndLadderBoard.player1IsOnALadder() || snakeAndLadderBoard.player1IsOnASnake()) && doneMoving) {
                int destination = snakeAndLadderBoard.getSnakeOrLadderDestination(snakeAndLadderBoard.getPlayer1TileNumber());
                textLabel.setText(!player1Turn ? "Player 1's turn" : "Player 2's turn");

                if ((snakeAndLadderBoard.player1IsOnALadder()))
                    ladderAlert();
                else
                    snakeAlert();

                rollDieButton.setEnabled(false);
                snakeAndLadderBoard.moveP1To(destination);
                rollDieButton.setEnabled(true);
                textLabel.setText(player1Turn ? "Player 1's turn" : "Player 2's turn");
            }

            //This block contains the necessary actions for player 2 if he/she lands on either a snake or a ladder.
            //We could've just made the code block a method, but it was too late, it would take us a lot of debugging
            //to optimize it, so we just left it as it is. Either way, it works.
            if ((snakeAndLadderBoard.player2IsOnALadder() || snakeAndLadderBoard.player2IsOnASnake()) && doneMoving) {
                int destination = snakeAndLadderBoard.getSnakeOrLadderDestination(snakeAndLadderBoard.getPlayer2TileNumber());
                textLabel.setText(!player1Turn ? "Player 1's turn" : "Player 2's turn");

                if ((snakeAndLadderBoard.player2IsOnALadder()))
                    ladderAlert();
                else
                    snakeAlert();

                rollDieButton.setEnabled(false);
                snakeAndLadderBoard.moveP2To(destination);
                rollDieButton.setEnabled(true);
                textLabel.setText(player1Turn ? "Player 1's turn" : "Player 2's turn");
            }

            //The loop breaks if either player 1 or 2 landed on the 100th tile.
            //In a very simple term, the loop breaks when a player wins.
            if((snakeAndLadderBoard.getPlayer1TileNumber() == 100 || snakeAndLadderBoard.getPlayer2TileNumber() == 100) && doneMoving)
                break;

        }

        youWin(snakeAndLadderBoard.getPlayer1TileNumber() == 100);

    }

    public static void initializeDieFaces(ImageIcon[] dieFaces) {
        int dieNum = 1;
        for (int i = 0; i < dieFaces.length; i++, dieNum++) {
            dieFaces[i] = new ImageIcon(dieNum + ".png");
        }
    }

    public static int rollDie(ImageIcon[] dieFaces, JLabel die, JButton moveButton) throws InterruptedException {
        int roll = 0;

        for (int i = 0; i < 6; i++) {
            Thread.sleep(200);
            roll = (int) (Math.random() * 6);
            die.setIcon(dieFaces[roll]);
        }

        moveButton.setEnabled(true);

        return roll + 1;
        // kung gusto ka mag buot kung pila ka lihok ang idagan sa imong player
    }

    public static void ladderAlert() {

        JOptionPane optionPane = new JOptionPane(new JLabel("You got on a ladder!", JLabel.CENTER), JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION, null, new String[]{"OK"});
        JDialog dialog = optionPane.createDialog("Ladder Alert");
        dialog.setModal(true);
        dialog.setVisible(true);

    }

    public static void snakeAlert() {

        JOptionPane optionPane = new JOptionPane(new JLabel("You got bit by a snake!", JLabel.CENTER), JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION, null, new String[]{"OK"});
        JDialog dialog = optionPane.createDialog("Snake Alert");
        dialog.setModal(true);
        dialog.setVisible(true);

    }

    public static void youWin(boolean player1Won) {
        String text = player1Won ? "Congratulations Player 1!!!" : "Congratulations Player 2!!!";
        JOptionPane optionPane = new JOptionPane(new JLabel(text + " You Win", JLabel.CENTER), JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_OPTION, null, new String[]{"OK"});
        JDialog dialog = optionPane.createDialog("Game Over");
        dialog.setModal(true);
        dialog.setVisible(true);
        System.exit(0);

    }

}