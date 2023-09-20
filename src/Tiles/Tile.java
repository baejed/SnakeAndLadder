package Tiles;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {

    private int tileWidth = 92;
    private int tileHeight = 92;
    private Color tileColor = Color.red;
    private JLabel numberLabel = new JLabel();

    //player 1 properties
    private ImageIcon player1Icon = new ImageIcon("Player1.png");
    private JLabel player1 = new JLabel(player1Icon);

    //player 2 properties
    private ImageIcon player2Icon = new ImageIcon("Player2.png");
    private JLabel player2 = new JLabel(player2Icon);
    private boolean isLadder = false;
    private boolean isSnake = false;
    private int tileNumber;

    public Tile(int XLocation, int YLocation) {

        this.setBounds(XLocation, YLocation, tileWidth, tileHeight);
        this.setOpaque(false);
        this.setBackground(tileColor);
        this.setVisible(true);
        this.setLayout(null);

        numberLabel.setBounds(0, 0, 25, 20);
        numberLabel.setBackground(tileColor);
        numberLabel.setVisible(true);
        numberLabel.setHorizontalAlignment(JLabel.LEFT);
        numberLabel.setFont(new Font("Arial", Font.BOLD, 15));

        player1.setBounds(0, 0, tileWidth, tileHeight);
        player1.setVisible(false);

        player2.setBounds(0, 0, tileWidth, tileHeight);
        player2.setVisible(false);

        this.add(numberLabel);
        this.add(player1);
        this.add(player2);

    }

    public void setText(String text) {
        numberLabel.setText(text);
    }

    public void occupyP1() {
        player1.setVisible(true);
    }

    public void occupyP2() {
        player2.setVisible(true);
    }

    public void removeP1(){
        player1.setVisible(false);
    }

    public void removeP2(){
        player2.setVisible(false);
    }
    public void placeLadder(){
        isLadder = true;
    }
    public boolean isLadder(){
        return isLadder;
    }

    public int getTileNumber(){
        return tileNumber;
    }

    public void setTileNumber(int tileNumber){
        this.tileNumber = tileNumber;
    }

    public void placeSnake(){
        isSnake = true;
    }

    public boolean isSnake(){
        return isSnake;
    }

}
