package view;

import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;


public class GamePanelDuo extends ListenerPanel implements ActionListener{
    private final int COUNT = 4;
    private GridComponent[][] grids;
    public boolean canMove;
    public int dir;

    private GridNumberDuo model;
    private JLabel stepLabel;
    private int score,dScore;
    private final int GRID_SIZE;
    private GameFrame gameFrame;
    private Timer timer;
    private int[] kirakiraMark;
    private int[][] temp;
    private boolean startTicking;
    private ArrayList<ImageInfo> imageInfos;
    static Random random = new Random();

    public GamePanelDuo(int size , GameFrame frame) {
        this.canMove = false;
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.imageInfos = new ArrayList<>();
//        this.setBackground(new Color(0, 0, 0, 0));
        this.setBackground(null);
        this.setOpaque(false);
        this.setSize(size, size);
        this.gameFrame = frame;
        this.GRID_SIZE = size / COUNT;
        this.temp = new int[4][4];
        this.grids = new GridComponent[COUNT][COUNT];
        this.model = new GridNumberDuo(COUNT, COUNT);
        this.kirakiraMark = new int[16];
        this.startTicking = false;
        this.initialGame();

    }

    public GridNumberDuo getModel() {
        return model;
    }

    public void initialGame() {
        this.score = 0;
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                grids[i][j] = new GridComponent(i, j, model.getNumber(i, j), this.GRID_SIZE);
                grids[i][j].setLocation(j * GRID_SIZE, i * GRID_SIZE);
                this.add(grids[i][j]);
            }
        }
//        model.printNumber();//check the 4*4 numbers in game
        this.repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (ImageInfo imageInfo : imageInfos) {
            if (imageInfo.isVisible()) {
                g.drawImage(imageInfo.getImage(), imageInfo.getX(), imageInfo.getY(), this);
            }
        }
    }

    //    @Override
//    public void actionPerformed(ActionEvent e) {
//        for (ImageInfo imageInfo : imageInfos) {
//            if(!imageInfo.updatePosition(getWidth(), getHeight())){
//                for (int i = 0; i < grids.length; i++) {
//                    for (int j = 0; j < grids[i].length; j++) {
//                        grids[i][j].setNumber(model.getNumber(i, j));
//                    }
//                }
//                repaint();
//                imageInfos = new ArrayList<>();
//                timer.stop();
//            }
//        }
//        repaint();
//    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<ImageInfo> a = new ArrayList<>();
        if (imageInfos.isEmpty()) {
            for (int i = 0 ; i < 4 ; i++){
                for (int j = 0 ; j < 4 ; j++){
                    grids[i][j].setNumber(temp[i][j]);
                }
            }
            for (int i = 0; i < grids.length; i++) {
                for (int j = 0; j < grids[i].length; j++) {
                    grids[i][j].setNumber(model.getNumber(i, j));
                }
            }
            repaint();
            imageInfos = new ArrayList<>();
            timer.stop();
        }
        for (ImageInfo imageInfo : imageInfos) {
            if (!imageInfo.updatePosition(getWidth(), getHeight())) {
                a.add(imageInfo);
            }
        }
        for (ImageInfo imageInfo : a) {
            imageInfos.remove(imageInfo);
        }
        repaint();
    }

    public void updateGridsNumber(int dir) {
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                System.out.print(model.getFinalLocation(i,j) + " ");
            }
            System.out.println();
        }
        if(dir != 4){
//            int dx = 0, dy = 0;
//            switch (dir){
//                case 0 :
//                    dx = 1;
//                    dy = 0;
//                    break;
//                case 1 :
//                    dx = -1;
//                    dy = 0;
//                    break;
//                case 2 :
//                    dx = 0;
//                    dy = -1;
//                    break;
//                case 3 :
//                    dx = 0;
//                    dy = 1;
//                    break;
//            }
//            for (int i = 0 ; i < grids.length ; i++){
//                for (int j = 0 ; j < grids[i].length ; j++){
//                    boolean checked = false , bonus = false;
//                    for(int y = i ; y < 4 && y > 0 ; y += dy){
//                        for (int x = j ; x < 4 && x > 0 ; x += dx){
//                            if(grids[i][j].getNumber() != 0 && !checked){
//                                if(grids[i][j].getNumber() != grids[i][j].getNumber()){
//                                    checked = true;
//                                }else {
//                                    checked = true;
//                                    bonus = true;
//                                }
//                            }
//                        }
//                    }
//
//                }
//            }
            int[][] finalX = new int[4][4] , finalY = new int[4][4];
            for(int i = 0 ; i < 4 ; i++){
                for(int j = 0 ; j < 4 ; j++){
                    if(model.getNumber(i,j) != 0 && (i != model.getNewI() || j != model.getNewJ())){
                        if(model.getFinalLocation(i,j) > 16){
                            int a = model.getFinalLocation(i,j) / 100 , b = model.getFinalLocation(i,j) % 100;
                            finalX[a%4][a/4] = j;
                            finalX[b%4][b/4] = j;
                            finalY[a%4][a/4] = i;
                            finalY[b%4][b/4] = i;
                        }
                        else {
                            int a = model.getFinalLocation(i,j);
                            finalX[a%4][a/4] = j;
                            finalY[a%4][a/4] = i;
                        }
                    }
                }
            }
            for (int i = 0 ; i < grids.length ; i++){
                for (int j = 0 ; j < grids[i].length ; j++){
                    if(grids[i][j].getNumber() != 0){

                        imageInfos.add(new ImageInfo(".\\src\\resourses\\numbers\\" + grids[i][j].getNumber() + ".png" , 160 * j,160 * i,finalX[i][j] * 160 , finalY[i][j] * 160));
                        temp[i][j] = grids[i][j].getNumber();
                        if(j != finalX[i][j] || i != finalY[i][j]){
                            grids[i][j].setNumber(0);
                        }
                    }
                }
            }
            timer = new Timer(2 , this);
            timer.start();
        }else{
            for (int i = 0; i < grids.length; i++) {
                for (int j = 0; j < grids[i].length; j++) {
                    grids[i][j].setNumber(model.getNumber(i, j));
                }
            }
            repaint();
        }


    }

    public void regret(){
        if(model.regret()){
            this.updateGridsNumber(4);
            regretMove();
        }
    }


    /**
     * Implement the abstract method declared in ListenerPanel.
     * Do move right.
     */
    @Override
    public void doMoveRight() {
        dir = 0;
        if(!canMove){
            return;
        }
        dScore = 0;
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                model.setFinalLocation(i,j,i+j*4);
            }
        }
        System.out.println("Click VK_RIGHT");
        this.model.rememberNumber();
        boolean tag = false;
        if(this.model.moveRight()){tag = true;}
        int tempScore = this.model.addRight();
        if(tempScore != 0){tag = true;}
        if(this.model.moveRight()){tag = true;}
        if(tag){
            if(this.model.afterMove()){
                this.afterMove();
                dScore += tempScore;
            }
            this.updateGridsNumber(0);
        }
        canMove = false;
    }

    @Override
    public void doMoveLeft() {
        dir = 1;
        if(!canMove){
            return;
        }
        dScore = 0;
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                model.setFinalLocation(i,j,i+j*4);
            }
        }
        System.out.println("Click VK_LEFT");
        this.model.rememberNumber();
        boolean tag = false;
        if(this.model.moveLeft()){tag = true;}
        int tempScore = this.model.addLeft();
        if(tempScore != 0){
            tag = true;
            dScore += tempScore;
        }
        if(this.model.moveLeft()){tag = true;}
        if(tag){
            if(this.model.afterMove()){
                this.afterMove();
                dScore += tempScore;
            }
            this.updateGridsNumber(1);
        }
        canMove = false;
    }

    @Override
    public void doMoveUp() {
        dir = 2;
        if(!canMove){
            return;
        }
        dScore = 0;
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                model.setFinalLocation(i,j,i+j*4);
            }
        }
        System.out.println("Click VK_UP");
        this.model.rememberNumber();
        boolean tag = false;
        if(this.model.moveUp()){tag = true;}
        int tempScore = this.model.addUp();
        if(tempScore != 0){
            tag = true;
            dScore += tempScore;
        }
        if(this.model.moveUp()){tag = true;}
        if(tag){
            if(this.model.afterMove()){
                this.afterMove();
                dScore += tempScore;
            }
            this.updateGridsNumber(2);
        }
        canMove = false;
    }

    @Override
    public void doMoveDown() {
        dir = 3;
        if(!canMove){
            return;
        }
        dScore = 0;
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                model.setFinalLocation(i,j,i+j*4);
            }
        }
        System.out.println("Click VK_DOWN");
        this.model.rememberNumber();
        boolean tag = false;
        if(this.model.moveDown()){tag = true;}
        int tempScore = this.model.addDown();
        if(tempScore != 0){
            tag = true;
            dScore += tempScore;
        }
        if(this.model.moveDown()){tag = true;}
        if(tag){
            if(this.model.afterMove()){
                this.afterMove();
            }
            this.updateGridsNumber(3);
        }
        canMove = false;
    }

    public void afterMove() {
        if(!startTicking){
            gameFrame.startTicking();
            startTicking = true;
        }
        this.score += dScore;
        this.stepLabel.setText(String.format("Score: %d", this.score));
        this.stepLabel.setFont(new Font("serif", Font.ITALIC, 18));
        this.stepLabel.setLocation(870,380);
        for (int i = 0 ; i < 16 ; i++){
            if(model.getSuccessfulPoints(i)){
//                System.out.println("111\n");
                model.turnOffSuccessfulPoint(i);
                gameFrame.startKirakiraAnimation((i / 4) * GRID_SIZE + 50 ,(i % 4) * GRID_SIZE + 50);
            }
        }
    }

    public void regretMove() {
        this.score -= dScore / 2;
        if(this.score != 0){
            this.stepLabel.setText(String.format("Score: %d", this.score));
        } else{
            this.stepLabel.setText(String.format("Score: %d", this.score));
        }
    }

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }
    public GridNumberDuo getGridNumber(){
        return model;
    }
    public void draw(){
        this.repaint();
    }
}