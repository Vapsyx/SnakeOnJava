package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image skull;
    private Image tnt;
    private Image wood;
    private Image apple;
    private Image coble;
    private Image badApple;
    private int cobleX;
    private int cobleY;
    private int skullX;
    private int skullY;
    private int tntX;
    private int tntY;
    private int woodX;
    private int woodY;
    private int appleX;
    private int appleY;
    private int badAppleX;
    private int badAppleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private int score = 0;

    public GameField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListner());
        setFocusable(true);
    }

    public void initGame(){
        dots = 3;
        for(int i =0; i< dots; i++){
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250,this);
        timer.start();
        createApple();
        createCoble();
        createBadApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void createBadApple(){
        badAppleX = new Random().nextInt(20)*DOT_SIZE;
        badAppleY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void createCoble(){
        cobleX = new Random().nextInt(20)*DOT_SIZE;
        cobleY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void createSkull(){
        skullX = new Random().nextInt(20)*DOT_SIZE;
        skullY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void createTnt(){
        tntX = new Random().nextInt(20)*DOT_SIZE;
        tntY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void createWood(){
        woodX = new Random().nextInt(20)*DOT_SIZE;
        woodY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iib = new ImageIcon("badApple.png");
        badApple = iib.getImage();
        ImageIcon iic = new ImageIcon("coble.png");
        coble = iic.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
        ImageIcon iis = new ImageIcon("skull.png");
        skull = iis.getImage();
        ImageIcon iit = new ImageIcon("tnt.png");
        tnt = iit.getImage();
        ImageIcon iiw = new ImageIcon("wood.png");
        wood = iiw.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple, appleX, appleY,this);
            g.drawImage(badApple, badAppleX, badAppleY,this);
            g.drawImage(coble, cobleX, cobleY,this);
            g.drawImage(skull, skullX, skullY,this);
            g.drawImage(tnt, tntX, tntY,this);
            g.drawImage(wood, woodX, woodY,this);
            g.setColor(Color.white);
            g.drawString(String.valueOf(score),25,25);
            if(x[0] == appleX && y[0] == appleY){
                score++;
            }
            if(x[0] == badAppleX && y[0] == badAppleY){
                score--;
            }
            if(x[0] == cobleX && y[0] == cobleY){
                inGame = false;
            }
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else{
            String gameOver = "Game Over";
            g.setColor(Color.white);
            g.drawString(gameOver,150, SIZE/2);
            String strScore = "Yor score =";
            g.drawString(strScore,147, 180);
            g.drawString(String.valueOf(score),212,180);
        }
    }

    public void createWood5(){
        if(score > 5){
            createWood();
        }else {
            woodX = 1000;
            woodY = 1000;
        }
    }

    public void createTnt15(){
        if(score > 15){
            createTnt();
        }else {
            tntX = 1000;
            tntY = 1000;
        }
    }

    public void createSkull25(){
        if(score > 25){
            createSkull();
        }else{
            skullX = 1000;
            skullY = 1000;
        }
    }

    public void move(){
        for (int i = dots; i > 0 ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        }
        if(up){
            y[0] -= DOT_SIZE;
        }
        if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkWood(){
        if (x[0] == woodX && y[0] == woodY) {
            inGame = false;
        }
    }
    public void checkSkull(){
        if (x[0] == skullX && y[0] == skullY) {
            inGame = false;
            score = 0;
        }
    }
    public void checkTnt(){
        if (x[0] == tntX && y[0] == tntY) {
            dots = 2;
            score = -1;
            createApple();
            createCoble();
            createBadApple();
            createWood5();
            createTnt15();
            createSkull25();
            tntX = 1000;
            tntY = 1000;
        }
    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
            createCoble();
            createBadApple();
            createWood5();
            createTnt15();
            createSkull25();
        }
    }

    public void checkBadApple(){
        if(x[0] == badAppleX && y[0] == badAppleY){
            dots--;
            createApple();
            createCoble();
            createBadApple();
            createWood5();
            createTnt15();
            createSkull25();
        }
    }

    public void checkScore(){
        if(score == -3){
            inGame = false;
        }
    }

    public void checkCoble(){
        if (x[0] == cobleX && y[0] == cobleY) {
            inGame = false;
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkApple();
            checkSkull();
            checkWood();
            checkTnt();
            checkCoble();
            checkBadApple();
            checkCollisions();
            checkScore();
            move();
        }
        repaint();
    }

    class FieldKeyListner extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key==KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key==KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key==KeyEvent.VK_UP && !down){
                up = true;
                right = false;
                left = false;
            }
            if(key==KeyEvent.VK_DOWN && !up){
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
