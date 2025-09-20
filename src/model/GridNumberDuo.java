package model;

import java.util.Arrays;
import java.util.Random;

public class GridNumberDuo {
    private final int X_COUNT;
    private final int Y_COUNT;

    private int[][] numbers , lastStep , recordStep , beforeMove , finalLocation;
    private boolean[] successfulPoints;
    private int newI,newJ;
    static Random random = new Random();

    public GridNumberDuo(int xCount, int yCount) {
        this.X_COUNT = xCount;
        this.Y_COUNT = yCount;
        this.numbers = new int[this.X_COUNT][this.Y_COUNT];
        this.recordStep = new int[this.X_COUNT][this.Y_COUNT];
        this.lastStep = new int[this.X_COUNT][this.Y_COUNT];
        this.finalLocation = new int[this.X_COUNT][this.Y_COUNT];
        this.successfulPoints = new boolean[16];
        this.newI = 0;
        this.newJ = 0;
        this.initialNumbers();
    }

    public void initialNumbers() {
        for(int i = 0 ; i < numbers.length ; i++){
            for (int j = 0 ; j < numbers[i].length ; j++){
                numbers[i][j] = 0;
            }
        }
        int randomNum = random.nextInt(16);
        numbers[randomNum/4][randomNum%4] = 2;
        int randomNum2 = random.nextInt(16);
        while(randomNum2 == randomNum){
            randomNum2 = random.nextInt(16);
        }
        numbers[randomNum2/4][randomNum2%4] = 4;
        for (int i = 0 ; i < numbers.length ; i++){
            for(int j = 0 ; j < numbers[i].length ; j++){
                recordStep[i][j] = numbers[i][j];
                finalLocation[i][j] = i + j * 4;
//                System.out.println(finalX[i][j] + " " + finalY[i][j]);
            }
        }
        for (int i = 0 ; i < 16 ; i++){
            successfulPoints[i] = false;
        }
    }
    //todo: finish the method of four direction moving.
//    public boolean moveRight() {
//        rememberNumber();
//        boolean ifMoved = false;
//        for (int i = 0; i < numbers.length; i++) {
//            for(int t = 0 ; t < numbers[i].length ; t++){
//                for(int j = numbers[i].length - 1 ; j > 0 ; j--){
//                    if(numbers[i][j] == 0 && numbers[i][j-1] != 0){
//                        numbers[i][j] = numbers[i][j-1];
//                        numbers[i][j-1] = 0;
//                        ifMoved = true;
//                    }
//                }
//            }
//            for (int j = numbers[i].length - 1 ; j > 0; j--){
//                if(numbers[i][j] == numbers[i][j-1] && numbers[i][j] != 0){
//                    numbers[i][j] *= 2;
//                    numbers[i][j-1] = 0;
//                    successfulPoints[i + j * 4] = true;
//                    ifMoved = true;
//                }
//            }
//            for(int t = 0 ; t < numbers[i].length ; t++){
//                for(int j = numbers[i].length - 1 ; j > 0 ; j--){
//                    if(numbers[i][j] == 0 && numbers[i][j-1] != 0){
//                        numbers[i][j] = numbers[i][j-1];
//                        numbers[i][j-1] = 0;
//                        ifMoved = true;
//                    }
//                }
//            }
//        }
//        if(!ifMoved){
//            return false;
//        }
//        changeRecord();
//        addNumber();
//        if(!testSpace()){
//            System.out.println("You lose");
//            return false;
//        }
////        numbers[0][0] = 1024;
//        return true;
//    }
    public boolean moveRight() {
        boolean ifMoved = false;
        for (int i = 0; i < numbers.length; i++) {
            for(int t = 0 ; t < numbers[i].length ; t++){
                for(int j = numbers[i].length - 1 ; j > 0 ; j--){
                    if(numbers[i][j] == 0 && numbers[i][j-1] != 0){
                        numbers[i][j] = numbers[i][j-1];
//                        finalX[i][j-1] = j;
                        finalLocation[i][j] = finalLocation[i][j-1];
                        numbers[i][j-1] = 0;
                        ifMoved = true;
                    }
                }
            }
        }
        return ifMoved;
    }
    public int addRight(){
        int score = 0;
        for (int i = 0 ; i < numbers.length ; i++){
            for (int j = numbers[i].length - 1 ; j > 0; j--){
                if(numbers[i][j] == numbers[i][j-1] && numbers[i][j] != 0){
                    numbers[i][j] *= 2;
                    numbers[i][j-1] = 0;
//                    finalX[i][j-1]++;
                    finalLocation[i][j] = finalLocation[i][j] * 100 + finalLocation[i][j-1];
                    successfulPoints[i + j * 4] = true;
                    score += numbers[i][j];
                }
            }
        }
        return score;
    }
    public boolean moveLeft() {
        boolean ifMoved = false;
        for (int i = 0; i < numbers.length; i++) {
            for(int t = 0 ; t < numbers[i].length ; t++){
                for(int j = 0 ; j < numbers[i].length - 1 ; j++){
                    if(numbers[i][j] == 0 && numbers[i][j+1] != 0){
                        numbers[i][j] = numbers[i][j+1];
                        numbers[i][j+1] = 0;
//                        finalX[i][j+1] = j;
                        finalLocation[i][j] = finalLocation[i][j+1];
                        ifMoved = true;
                    }
                }
            }
        }
        return ifMoved;
    }
    public int addLeft(){
        int score = 0;
        for(int i = 0 ; i < numbers.length ; i++){
            for (int j = 0 ; j < numbers[i].length - 1; j++){
                if(numbers[i][j] == numbers[i][j+1] && numbers[i][j] != 0){
                    numbers[i][j] *= 2;
                    numbers[i][j+1] = 0;
//                    finalX[i][j+1]--;
                    finalLocation[i][j] = finalLocation[i][j] * 100 + finalLocation[i][j+1];
                    successfulPoints[i + j * 4] = true;
                    score += numbers[i][j];
                }
            }
        }
        return score;
    }
    public boolean moveUp() {
        boolean ifMoved = false;
        for (int j = 0; j < numbers[0].length; j++) {
            for(int t = 0 ; t < numbers.length ; t++){
                for(int i = 0 ; i < numbers.length - 1 ; i++){
                    if(numbers[i][j] == 0 && numbers[i+1][j] != 0){
                        numbers[i][j] = numbers[i+1][j];
                        numbers[i+1][j] = 0;
//                        finalY[i+1][j] = i;
                        finalLocation[i][j] = finalLocation[i+1][j];
                        ifMoved = true;
                    }
                }
            }
        }
        return ifMoved;
    }
    public int addUp(){
        int score = 0;
        for(int j = 0 ; j < numbers[0].length ; j++){
            for (int i = 0 ; i < numbers.length - 1; i++){
                if(numbers[i][j] == numbers[i+1][j] && numbers[i][j] != 0){
                    numbers[i][j] *= 2;
                    numbers[i+1][j] = 0;
//                    finalY[i+1][j]--;
                    finalLocation[i][j] = finalLocation[i][j] * 100 + finalLocation[i+1][j];
                    successfulPoints[i + j * 4] = true;
                    score += numbers[i][j];
                }
            }
        }
        return score;
    }
    public boolean moveDown() {
        boolean ifMoved = false;
        for (int j = 0; j < numbers[0].length; j++) {
            for(int t = 0 ; t < numbers.length ; t++){
                for(int i = numbers.length - 1 ; i > 0 ; i--){
                    if(numbers[i][j] == 0 && numbers[i-1][j] != 0){
                        numbers[i][j] = numbers[i-1][j];
                        numbers[i-1][j] = 0;
//                        finalY[i-1][j] = i;
                        finalLocation[i][j] = finalLocation[i-1][j];
                        ifMoved = true;
                    }
                }
            }
        }
        return ifMoved;
    }
    public int addDown(){
        int score = 0;
        for (int j = 0 ; j < numbers[0].length ; j++){
            for (int i = numbers.length - 1 ; i > 0; i--){
                if(numbers[i][j] == numbers[i-1][j] && numbers[i][j] != 0){
                    numbers[i][j] *= 2;
                    numbers[i-1][j] = 0;
//                    finalY[i-1][j]++;
                    finalLocation[i][j] = finalLocation[i][j] * 100 + finalLocation[i-1][j];
                    successfulPoints[i + j * 4] = true;
                    score += numbers[i][j];
                }
            }
        }
        return score;
    }
    public void BeforeMove(){
        for (int i = 0 ; i < numbers.length ; i++){
            for (int j = 0 ; j < numbers[i].length ; j++){
                beforeMove[i][j] = numbers[i][j];
            }
        }
    }
    public boolean afterMove(){
        changeRecord();
//        addNumber();
        if(!testSpace()){
            System.out.println("You lose");
            return false;
        }
//        numbers[0][0] = 1024;
        return true;
    }
    public void addNumber() {
        int[] Hang = new int[17], Lie = new int[17];
        int availableSpace = 0;
        for(int i = 0 ; i < numbers.length ; i++){
            for(int j = 0 ; j < numbers[i].length ; j++){
                if(numbers[i][j] == 0){
                    Hang[availableSpace] = i;
                    Lie[availableSpace] = j;
                    availableSpace++;
                }
            }
        }
        int rand = random.nextInt(availableSpace);
        int i = Hang[rand] , j = Lie[rand];
        newI = i;
        newJ = j;
        numbers[i][j] = 2;
        if(random.nextInt(6) == 0){
            numbers[i][j] = 4;
        }
    }

    public boolean testSpace(){
        for (int i = 0 ; i < numbers.length ; i++){
            for(int j = 0 ; j < numbers[i].length ; j++){
                if(numbers[i][j] == 0){
                    return true;
                }
                if(i > 0){
                    if(numbers[i][j] == numbers[i-1][j]){
                        return true;
                    }
                }
                if(j > 0){
                    if(numbers[i][j] == numbers[i][j-1]){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getNumber(int i, int j) {
        return numbers[i][j];
    }
    public void setNumber(int i, int j,int num) {
        numbers[i][j] = num;
    }

    public void printNumber() {
        for (int[] line : numbers) {
            System.out.println(Arrays.toString(line));
        }
    }
    public void rememberNumber(){
        for (int i = 0 ; i < numbers.length ; i++){
            for (int j = 0 ; j < numbers[i].length ; j++){
                lastStep[i][j] = numbers[i][j];
            }
        }
    }
    public void changeRecord(){
        for (int i = 0 ; i < numbers.length ; i++){
            for (int j = 0 ; j < numbers[i].length ; j++){
                recordStep[i][j] = lastStep[i][j];
            }
        }
    }
    public boolean regret(){
        boolean tag = false;
        for (int i = 0 ; i < numbers.length ; i++){
            for (int j = 0 ; j < numbers[i].length ; j++){
                if(numbers[i][j] != recordStep[i][j]){
                    tag = true;
                }
                numbers[i][j] = recordStep[i][j];
            }
        }
        return tag;
    }
    public int AIGetDirection(){
        int[][] number = new int[4][4];
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                number[i][j] = numbers[i][j];
            }
        }
        AISuggest aiSuggest = new AISuggest(number);
        return aiSuggest.choose(1,number);
    }
    public int Score(){
        int[][] number = new int[4][4];
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                number[i][j] = numbers[i][j];
            }
        }
        AISuggest aiSuggest = new AISuggest(number);
        return aiSuggest.getScore(numbers);
    }
    public boolean getSuccessfulPoints(int i){
        return successfulPoints[i];
    }
    public void turnOffSuccessfulPoint(int i){
        if(!successfulPoints[i]){
            System.out.println("错误:10145");
        }
        successfulPoints[i] = false;
    }
    public int getFinalLocation(int i , int j){
        return finalLocation[i][j];
    }
    public void  setFinalLocation(int i , int j , int k){
        finalLocation[i][j] = k;
    }
    public int getNewI(){
        return newI;
    }

    public int getNewJ() {
        return newJ;
    }
}
