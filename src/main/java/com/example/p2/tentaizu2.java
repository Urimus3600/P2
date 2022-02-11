package com.example.p2;

import java.util.ArrayList;
import java.util.Scanner;

public class tentaizu2 {

}

class tentaizuBoard{
    int stars;
    int curID;
    int nextID;
    tentaizuSqaure[][] board;


    public void solveBoard(){
        solveBoard(board);
    }
    public void solveBoard(tentaizuSqaure[][] board){
        setOwnership();

    }
    void setOwnership(){
        for(int i=9; i>=0; i--){
            for(int j=9; j>=0; j--){
                if(board[i][j].isNum){
                    curID = toID(i,j);
                    setTerritory();
                }
            }
        }
    }
    void setTerritory(int ID){
        board[rowID(curID)][colID(curID)].owner=curID;
        nextID = curID;
        do{
            nextID = moveClock();
            board[rowID(nextID)][colID(nextID)].owner=curID;
            board[rowID(nextID)][colID(nextID)].num++;
        }while(nextID != curID);
    }

    public void readInBoard(Scanner input){
        board= new tentaizuSqaure[9][9];
        for(int i=0; i<9; i++){
            String row = input.nextLine();
            for(int j=0; j<9; j++){
                if(i==0 || i==8 || j==0 || j==8){
                    board[i][j]= new tentaizuSqaure('B', i, j);
                }
                else{
                    board[i][j]= new tentaizuSqaure(row.charAt(j), i, j);
                }
            }
        }
    }

    int rowID(int ID){
        return ID/9;
    }
    int colID(int ID){
        return ID%9-1;
    }
    int toID(int row, int col){
        return row*9+col+1;
    }
    findLastVariable(){

    }
    boolean validAdd(){
        if(curID==board[rowID(nextID)][colID(nextID)].owner && !board[rowID(nextID)][colID(nextID)].isNum &&
                !board[rowID(nextID)][colID(nextID)].isBlock && !board[rowID(nextID)][colID(nextID)].isStar){
            return true;
        }
        return false;
    }
    int moveClock(){
        int num = nextID-curID;
        switch(num){
            case 0:
            case -1:
            case 8:
                return nextID-9;
            case -9:
                return nextID+1;
            case -8:
            case 1:
                return nextID+9;
            case 10:
            case 9:
                return nextID-1;
            case -10:
                return curID;
            default:
                error("Incorrect Values MoveClock nextID: " + nextID + "curID: " + curID);
        }
        return 9;
    }

    int moveCounter(){
        int num = nextID-curID;
        switch(num){
            case 0:
                return nextID-10;
            case -10:
            case -1:
                return nextID+9;
            case 8:
            case 9:
                return nextID+1;
            case 10:
            case 1:
                return nextID-9;
            case -8:
                return nextID-1;
            case -9:
                return curID;
            default:
                error("Incorrect values for MoveCounter nextID: "+nextID+" curID: "+curID);
        }
        return 9;
    }

    void error(String error){
        System.out.println("Error: "+ error);
    }
}

class tentaizuSqaure{
    int[] territory;
    boolean isNum;
    boolean isStar;
    boolean isBlock;
    int owner;
    int ID;
    int num;
    tentaizuSqaure(char in, int row, int col){
        int value = in-48;
        isNum = value>=0 && value<=8;
        if(isNum){
            num = value;
        }
        else num=0;
        isBlock = in=='B';
        isStar = false;
        int ID = row*9+col+1;
    }
}
