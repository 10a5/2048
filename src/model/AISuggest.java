package model;

public class AISuggest {
    private int[][] numberss , number1 , number2;
    public AISuggest(int[][] number){
        this.number1 = new int[4][4];
        this.number2 = new int[4][4];
        this.numberss = new int[4][4];
        for(int l = 0 ; l < 4 ; l++){
            System.arraycopy(number[l], 0, this.numberss[l], 0, 4);
        }
    }
    public int choose(int depth , int[][] number){
        if(depth == 2){
            return getScore(number);
        }
        int score1 = -1 , dir = 0;
        for(int i = 0 ; i < 4 ; i++){
            for(int l = 0 ; l < 4 ; l++){
                System.arraycopy(number[l], 0, number1[l], 0, 4);
            }
            int score = 0  ;
            System.out.println("A" + dir + " " + score1 + " " + depth);
            switch (i){
                case 0:
                    for(int l = 0 ; l < 4 ; l++){
                        System.arraycopy(number1[l], 0, numberss[l], 0, 4);
                    }
                    if(Right()){
                        numberss = Add(depth);
                        score = choose(depth + 1 , numberss);
                        System.out.println(i + " " + score + " " + depth);
                        if(score1 < score){
                            score1 = score;
                            dir = i;
                        }
                    }
                    break;
                case 1:
                    for(int l = 0 ; l < 4 ; l++){
                        System.arraycopy(number1[l], 0, numberss[l], 0, 4);
                    }
                    if(Left()){
                        numberss = Add(depth);
                        score = choose(depth + 1 , numberss);
                        System.out.println(i + " " + score + " " + depth);
                        if(score1 < score){
                            score1 = score;
                            dir = i;
                        }
                    }
                    break;
                case 2:
                    for(int l = 0 ; l < 4 ; l++){
                        System.arraycopy(number1[l], 0, numberss[l], 0, 4);
                    }
                    if(Up()){
                        numberss = Add(depth);
                        score = choose(depth + 1 , numberss);
                        System.out.println(i + " " + score + " " + depth);
                        if(score1 < score){
                            score1 = score;
                            dir = i;
                        }
                    }
                    break;
                case 3:
                    for(int l = 0 ; l < 4 ; l++){
                        System.arraycopy(number1[l], 0, numberss[l], 0, 4);
                    }
                    if(Down()){
                        numberss = Add(depth);
                        score = choose(depth + 1 , numberss);
                        System.out.println(i + " " + score + " " + depth);
                        if(score1 < score){
                            score1 = score;
                            dir = i;
                        }
                    }
                    break;
            }
        }
        if(depth == 1){
            return dir;
        }
        return score1;
    }
    public int[][] Add(int depth ){
        int[][] number = new int[4][4];
        for(int l = 0 ; l < 4 ; l++){
            System.arraycopy(numberss[l], 0, number[l], 0, 4);
        }
        if(number[0][0] == 0){
            number[0][0] = 2;
            return number;
        }
        int scoreAdd1 = 2147483647 , score = 0;
        int[][] ansNumber = new int[4][4];
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                if(numberss[i][j] == 0){
                    for(int l = 0 ; l < 4 ; l++){
                        System.arraycopy(number[l], 0, numberss[l], 0, 4);
                    }
                    numberss[i][j] = 2;
                    score = getScore(numberss);
                    if(score < scoreAdd1){
                        scoreAdd1 = score;
                        for(int l = 0 ; l < 4 ; l++){
                            System.arraycopy(numberss[l], 0, ansNumber[l], 0, 4);
                        }
                    }
                    for(int l = 0 ; l < 4 ; l++){
                        System.arraycopy(number[l], 0, numberss[l], 0, 4);
                    }
                    numberss[i][j] = 4;
                    score = getScore(numberss);
                    if(score < scoreAdd1){
                        scoreAdd1 = score;
                        for(int l = 0 ; l < 4 ; l++){
                            System.arraycopy(numberss[l], 0, ansNumber[l], 0, 4);
                        }
                    }
                }
            }
        }
        return ansNumber;
    }
    public int getScore(int[][] number){
        int score = 0 , maxNum = 0;
        for(int i = 0 ; i < 4 ; i++){
            for(int j = 0 ; j < 4 ; j++){
                if(number[i][j] == 0 ){
                    score += 6;
                }
                if(number[i][j] > maxNum){
                    maxNum = number[i][j];
                }
            }
        }
        score += GetClose(number);
        for (int i = 0 ; i < 3 ; i++){
            for (int j = 0 ; j < 3 ; j++){
                if(number[i][j] == number[i+1][j]){
                    score += number[i][j] * 120;
                }
                if(number[i][j] == number[i][j+1]){
                    score += number[i][j] * 120;
                }
            }
        }
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                score += number[i][j] * number[i][j] * 10;
            }
        }

        if(number[0][0] == maxNum || number[3][0] == maxNum || number[0][3] == maxNum || number[3][3] == maxNum){score += 50000000;}
        score += maxNum * 10;
        return score;
    }
    private int GetClose(int[][] number){
        int ans = 0;
        int[][] tempNum = new int[4][4];
        for (int i = 0 ; i < 4 ; i++){
            System.arraycopy(number[i],0,tempNum[i],0,4);
        }
        int maxNum = 0, r = 0 , c = 0;
        for(int i = 0 ; i < 4 ; i++){
            for(int j = 0 ; j < 4 ; j++){
                if(tempNum[i][j] > maxNum){
                    maxNum = tempNum[i][j];
                    r=i;
                    c=j;
                }
            }
        }
        if(r == 0 && c == 0){
            if(tempNum[r+1][c] > tempNum[r][c+1]){
                ans += tempNum[r+1][c] * 100;
                if(tempNum[r+1][c] >= tempNum[r+2][c]){
                    if(tempNum[r+2][c] >= tempNum[r+3][c]){
                        ans += tempNum[r+3][c] * 70;
                        if(tempNum[r+3][c] >= tempNum[r+3][c+1]){
                            ans += tempNum[r+3][c+1] * 70;
                            if(tempNum[r+3][c] == tempNum[r+3][c+1]){
                                ans += tempNum[r+3][c+1] * 70;
                            }
                        }
                    }
                    ans += tempNum[r+2][c] * 70;
                }
            }
            else {
                ans += tempNum[r][c+1] * 100;
                if(tempNum[r][c+1] >= tempNum[r][c+2]){
                    if(tempNum[r][c+2] >= tempNum[r][c+3]){
                        ans += tempNum[r][c+3] * 70;
                        if(tempNum[r][c+3] >= tempNum[r+1][c+3]){
                            ans += tempNum[r+1][c+3] * 70;
                            if(tempNum[r][c+3] == tempNum[r+1][c+3]){
                                ans += tempNum[r+1][c+3] * 70;
                            }
                        }
                    }
                    ans += tempNum[r][c+2] * 70;
                }
            }
        }
        if(r == 3 && c == 0){
            if(tempNum[r-1][c] > tempNum[r][c+1]){
                ans += tempNum[r-1][c] * 100;
                if(tempNum[r-1][c] >= tempNum[r-2][c]){
                    if(tempNum[r-2][c] >= tempNum[r-3][c]){
                        if(tempNum[r-3][c] >= tempNum[r-3][c+1]){
                            ans += tempNum[r-3][c+1] * 70;
                            if(tempNum[r-3][c] == tempNum[r-3][c+1]){ans += tempNum[r-3][c+1] * 70;}
                        }
                        ans += tempNum[r-3][c] * 70;
                    }
                    ans += tempNum[r-2][c] * 70;
                }
            }
            else {
                ans += tempNum[r][c+1] * 100;
                if(tempNum[r][c+1] >= tempNum[r][c+2]){
                    if(tempNum[r][c+2] >= tempNum[r][c+3]){
                        if(tempNum[r][c+3] >= tempNum[r-1][c+3]){
                            ans += tempNum[r-1][c+3] * 70;
                            if(tempNum[r][c+3] == tempNum[r-1][c+3]){ans += tempNum[r-1][c+3] * 70;}
                        }
                        ans += tempNum[r][c+3] * 70;
                    }
                    ans += tempNum[r][c+2] * 70;
                }
            }
        }
        if(r == 0 && c == 3){
            if(tempNum[r+1][c] > tempNum[r][c-1]){
                ans += tempNum[r+1][c] * 100;
                if(tempNum[r+1][c] >= tempNum[r+2][c]){
                    if(tempNum[r+2][c] >= tempNum[r+3][c]){
                        if(tempNum[r+3][c] >= tempNum[r+3][c-1]){
                            ans += tempNum[r+3][c-1] * 70;
                            if(tempNum[r+3][c] == tempNum[r+3][c-1]){ans += tempNum[r+3][c-1] * 70;}
                        }
                        ans += tempNum[r+3][c] * 70;
                    }
                    ans += tempNum[r+2][c] * 70;
                }
            }
            else {
                ans += tempNum[r][c-1] * 100;
                if(tempNum[r][c-1] >= tempNum[r][c-2]){
                    if(tempNum[r][c-2] >= tempNum[r][c-3]){
                        if(tempNum[r+3][c] >= tempNum[r+3][c-1]){
                            ans += tempNum[r+3][c-1] * 70;
                            if(tempNum[r+3][c] == tempNum[r+3][c-1]){ans += tempNum[r+3][c-1] * 70;}
                        }
                        ans += tempNum[r][c-3] * 70;
                    }
                    ans += tempNum[r][c-2] * 70;
                }
            }
        }
        if(r == 3 && c == 3){
            if(tempNum[r-1][c] > tempNum[r][c-1]){
                ans += tempNum[r-1][c] * 100;
                if(tempNum[r-1][c] >= tempNum[r-2][c]){
                    if(tempNum[r-2][c] >= tempNum[r-3][c]){
                        if(tempNum[r-3][c] >= tempNum[r-3][c-1]){
                            ans += tempNum[r-3][c-1] * 70;
                            if(tempNum[r-3][c] == tempNum[r-3][c-1]){ans += tempNum[r-3][c-1] * 70;}
                        }
                        ans += tempNum[r-3][c] * 70;
                    }
                    ans += tempNum[r-2][c] * 70;
                }
            }
            else {
                ans += tempNum[r][c-1] * 100;
                if(tempNum[r][c-1] >= tempNum[r][c-2]){
                    if(tempNum[r][c-2] >= tempNum[r][c-3]){
                        if(tempNum[r][c-3] >= tempNum[r-1][c-3]){
                            ans += tempNum[r-1][c-3] * 70;
                            if(tempNum[r][c-3] == tempNum[r-1][c-3]){ans += tempNum[r-1][c-3] * 70;}
                        }
                        ans += tempNum[r][c-3] * 70;
                    }
                    ans += tempNum[r][c-2] * 70;
                }
            }
        }
        return ans;
    }
    public boolean moveRight() {
        boolean ifMoved = false;
        for (int i = 0; i < numberss.length; i++) {
            for(int t = 0 ; t < numberss[i].length ; t++){
                for(int j = numberss[i].length - 1 ; j > 0 ; j--){
                    if(numberss[i][j] == 0 && numberss[i][j-1] != 0){
                        numberss[i][j] = numberss[i][j-1];
                        numberss[i][j-1] = 0;
                        ifMoved = true;
                    }
                }
            }
        }
        return ifMoved;
    }
    public boolean addRight(){
        boolean ifMoved = false;
        for (int i = 0 ; i < numberss.length ; i++){
            for (int j = numberss[i].length - 1 ; j > 0; j--){
                if(numberss[i][j] == numberss[i][j-1] && numberss[i][j] != 0){
                    numberss[i][j] *= 2;
                    numberss[i][j-1] = 0;
                    ifMoved = true;
                }
            }
        }
        return ifMoved;
    }
    public boolean moveLeft() {
        boolean ifMoved = false;
        for (int i = 0; i < numberss.length; i++) {
            for(int t = 0 ; t < numberss[i].length ; t++){
                for(int j = 0 ; j < numberss[i].length - 1 ; j++){
                    if(numberss[i][j] == 0 && numberss[i][j+1] != 0){
                        numberss[i][j] = numberss[i][j+1];
                        numberss[i][j+1] = 0;
                        ifMoved = true;
                    }
                }
            }
        }
        return ifMoved;
    }
    public boolean addLeft(){
        boolean ifMoved = false;
        for(int i = 0 ; i < numberss.length ; i++){
            for (int j = 0 ; j < numberss[i].length - 1; j++){
                if(numberss[i][j] == numberss[i][j+1] && numberss[i][j] != 0){
                    numberss[i][j] *= 2;
                    numberss[i][j+1] = 0;
                    ifMoved = true;
                }
            }
        }
        return ifMoved;
    }
    public boolean moveUp() {
        boolean ifMoved = false;
        for (int j = 0; j < numberss[0].length; j++) {
            for(int t = 0 ; t < numberss.length ; t++){
                for(int i = 0 ; i < numberss.length - 1 ; i++){
                    if(numberss[i][j] == 0 && numberss[i+1][j] != 0){
                        numberss[i][j] = numberss[i+1][j];
                        numberss[i+1][j] = 0;
                        ifMoved = true;
                    }
                }
            }
        }
        return ifMoved;
    }
    public boolean addUp(){
        boolean ifMoved = false;
        for(int j = 0 ; j < numberss[0].length ; j++){
            for (int i = 0 ; i < numberss.length - 1; i++){
                if(numberss[i][j] == numberss[i+1][j] && numberss[i][j] != 0){
                    numberss[i][j] *= 2;
                    numberss[i+1][j] = 0;
                    ifMoved = true;
                }
            }
        }
        return ifMoved;
    }
    public boolean moveDown() {
        boolean ifMoved = false;
        for (int j = 0; j < numberss[0].length; j++) {
            for(int t = 0 ; t < numberss.length ; t++){
                for(int i = numberss.length - 1 ; i > 0 ; i--){
                    if(numberss[i][j] == 0 && numberss[i-1][j] != 0){
                        numberss[i][j] = numberss[i-1][j];
                        numberss[i-1][j] = 0;
                        ifMoved = true;
                    }
                }
            }
        }
        return ifMoved;
    }
    public boolean addDown(){
        boolean ifMoved = false;
        for (int j = 0 ; j < numberss[0].length ; j++){
            for (int i = numberss.length - 1 ; i > 0; i--){
                if(numberss[i][j] == numberss[i-1][j] && numberss[i][j] != 0){
                    numberss[i][j] *= 2;
                    numberss[i-1][j] = 0;
                    ifMoved = true;
                }
            }
        }
        return ifMoved;
    }
    public boolean Right(){
        boolean tag = this.moveRight();
        if(this.addRight()){tag = true;}
        if(this.moveRight()){tag = true;}
        return tag;
    }
    public boolean Left(){
        boolean tag = this.moveLeft();
        if(this.addLeft()){tag = true;}
        if(this.moveLeft()){tag = true;}
        return tag;
    }
    public boolean Up(){
        boolean tag = this.moveUp();
        if(this.addUp()){tag = true;}
        if(this.moveUp()){tag = true;}
        return tag;
    }
    public boolean Down(){
        boolean tag = this.moveDown();
        if(this.addDown()){tag = true;}
        if(this.moveDown()){tag = true;}
        return tag;
    }
}
