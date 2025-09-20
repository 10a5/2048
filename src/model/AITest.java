//package model;
//
//public class AITest {
//    private static GridNumber number = new GridNumber(4,4);
//    public static void main(String[] args){
//        int count = 0;
//        for (int t = 0 ; t < 100 ; t++){
//            number.initialNumbers();
//            boolean is2048 = false , moved = false;
//            while(true){
//                int dir = number.AIGetDirection();
//                switch (dir){
//                    case 0 -> moved = Right();
//                    case 1 -> moved = Left();
//                    case 2 -> moved = Up();
//                    case 3 -> moved = Down();
//                }
//                if(!moved){
//                    break;
//                }
//                for (int i = 0 ; i < 4 ; i++){
//                    for (int j = 0 ; j < 4 ; j++){
//                        if(number.getNumber(i,j) == 2048){
//                            is2048 = true;
//                        }
//                    }
//                }
//                if(is2048){
//                    count++;
//                    break;
//                }
//                for (int i = 0 ; i < 4 ; i++){
//                    for (int j = 0 ; j < 4 ; j++){
//                        System.out.print(number.getNumber(i,j) + " ");
//                    }
//                    System.out.println(' ');
//                }
//            }
//        }
//        System.out.println(count);
//    }
//    public static boolean Right(){
//        boolean tag = number.moveRight();
//        if(number.addRight() != 0){tag = true;}
//        if(number.moveRight()){tag = true;}
//        return number.afterMove();
//    }
//    public static boolean Left(){
//        boolean tag = number.moveLeft();
//        if(number.addLeft() != 0){tag = true;}
//        if(number.moveLeft()){tag = true;}
//        return number.afterMove();
//    }
//    public static boolean Up(){
//        boolean tag = number.moveUp();
//        if(number.addUp() != 0){tag = true;}
//        if(number.moveUp()){tag = true;}
//        return number.afterMove();
//    }
//    public static boolean Down(){
//        boolean tag = number.moveDown();
//        if(number.addDown() != 0){tag = true;}
//        if(number.moveDown()){tag = true;}
//        return number.afterMove();
//    }
//}
