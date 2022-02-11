package com.example.p2;

import java.util.Scanner;
import java.util.function.BinaryOperator;

public class tentaizu{

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int cases=input.nextInt();
        input.nextLine();
        for(int curCase = 0; curCase<cases; curCase++){
            tentaizuBoard board = new tentaizuBoard(7);
            board.solveBoard(input);
            board.printBoard();
            }
        }

    }

     class tentaizuBoard {
         int boardLngth;
         int boardSize;
         square[][] board;
         int rowRead, colRead;
         int rowNext, colNext;
         int rowCur, colCur;

         tentaizuBoard(int boardLngth) {
             this.boardLngth = boardLngth;
             this.boardSize = boardLngth * boardLngth;
             board = new square[boardLngth][boardLngth];
             for(int i=0; i<boardLngth; i++){
                 for(int j=0; j<boardLngth; j++){
                     board[i][j]=new square('.');
                 }
             }
         }

         boolean solveBoard(Scanner input) {
             for (int i = 0; i < boardLngth; i++) {
                 String row = input.nextLine();
                 for (int j = 0; j < boardLngth; j++) {
                     char car = row.charAt(j);
                     if (car != '.') {
                         board[i][j].value = car;
                         rowCur=i; colCur=j;
                         placeStars(1);
                         //correctBoard();

                     }
                 }
             }
             return true;
         }

         boolean runNum() {
             int num = board[rowCur][colCur].value - 48;
             placeStars(1);
             return true;
         }

         private boolean placeStars(int num) {
             colNext=colCur-1; rowNext=rowCur-1;
             int value = board[rowCur][colCur].value-48;
             value -= starCounter(true);
             if(value>0)
             for(int i=1; i<value; i++){
                 addStar(true);
             }
         }

         boolean findStar() {
             if (colNext > -1 && colNext < boardLngth && rowNext > -1 && rowNext < boardLngth) {
                 if (board[rowNext][colNext].value == '*' && !owned(true)) {
                     return true;
                 }
             }
             if (colNext - colCur == -1 && rowNext - rowCur == -1) {
                 return false;
             } else if (colNext - colCur == 1 && rowNext - rowCur == 1) {
                 rowNext--;
             } else if (colNext - colCur == 1 && rowNext - rowCur == -1) {
                 colNext--;
             } else if (colNext - colCur == -1 && rowNext - rowCur == 1) {
                 colNext++;
             } else if (rowNext - rowCur == 1) {
                 colNext++;
             } else if (rowNext - rowCur == -1) {
                 colNext--;
             } else if (colNext - colCur == 1) {
                 rowNext--;
             } else if (colNext - colCur == -1) {
                 rowNext++;
             }
             return findStar();
         }

         private boolean owned(boolean first) {
             if(first){
                 colNext=colCur-1;
                 rowNext=rowCur;
             }
             if(colNext>-1 && colNext<boardLngth && rowNext<boardLngth && rowNext>-1){
                 int value = board[rowNext][colNext].value-48;
                 if(value>=0 && value<=8 && (rowNext<rowCur || colNext<colCur)){
                     return true;
                 }
             }
             if (colNext - colCur == -1 && rowNext - rowCur == -1) {
                 return false;
             } else if (colNext - colCur == 1 && rowNext - rowCur == 1) {
                 rowNext--;
             } else if (colNext - colCur == 1 && rowNext - rowCur == -1) {
                 colNext--;
             } else if (colNext - colCur == -1 && rowNext - rowCur == 1) {
                 colNext++;
             } else if (rowNext - rowCur == 1) {
                 colNext++;
             } else if (rowNext - rowCur == -1) {
                 colNext--;
             } else if (colNext - colCur == 1) {
                 rowNext--;
             } else if (colNext - colCur == -1) {
                 rowNext++;
             }
             return owned(false);
         }

         boolean addStar(boolean next) {
             if(!next && validStar()){
                 board[rowNext][colNext].value='*';
                 board[rowNext][colNext].rowOwn=rowCur;
                 board[rowNext][colNext].colOwn=colCur;
             }

             if (colNext - colCur == -1 && rowNext - rowCur == -1) {
                 colNext++;
             } else if (colNext - colCur == 1 && rowNext - rowCur == 1) {
                 colNext--;
             } else if (colNext - colCur == 1 && rowNext - rowCur == -1) {
                 rowNext++;
                 System.out.println(rowNext+ "  " + colNext);
             } else if (colNext - colCur == -1 && rowNext - rowCur == 1) {
                 rowNext--;
             } else if (rowNext - rowCur == 1) {
                 colNext--;
             } else if (rowNext - rowCur == -1) {
                 colNext++;
             } else if (colNext - colCur == 1) {
                 rowNext++;
             } else if (colNext - colCur == -1) {
                 return false;
             }

             if(validStar()){
                 board[rowNext][colNext].value='*';
                 board[rowNext][colNext].rowOwn=rowCur;
                 board[rowNext][colNext].colOwn=colCur;
                 return true;
             }
             return addStar(true);
         }

         private boolean validStar() {
             int row=rowNext, col=colNext;
             if(colNext<boardLngth && colNext>=0 && rowNext<boardLngth && rowNext>=0){
                 if(board[rowNext][colNext].value=='.' && !owned(true)){
                     rowNext=row; colNext=col;
                     return true;
                 }
                 if(rowNext*boardLngth+colNext > rowRead*boardLngth+colRead){
                     rowNext=row; colNext=col;
                     return true;
                 }
             }
             return false;
         }
         void printBoard(){
             for(int i=0; i<boardLngth; i++){
                 for(int j=0; j<boardLngth; j++){
                     System.out.print(board[i][j].value);
                 }
                 System.out.println();
             }
         }
         boolean validBoard(){
             int row=rowCur, col= colCur;
              for(int i=rowCur; i>=0; i--){
                  for(int j=colCur; j>=0; j--){
                      int value = board[i][j].value-48;
                      if(value>-1 && value<=8){
                          rowCur=i; colCur=j;
                          if(value>=starCounter(true)){
                              rowCur=row; colCur=col;
                              return false;
                          }
                      }
                  }
              }
              rowCur=row; colCur=col;
              return true;
         }
         int starCounter(boolean first){
             if(first){
                 colNext=colCur-1;
                 rowNext=rowCur;
             }
             if(first && colNext>-1 && colNext<boardLngth && rowNext<boardLngth && rowNext>-1){
                 if(board[rowNext][colNext].value =='*'){
                     return 1+starCounter(false);
                 }
             }
             if (colNext - colCur == -1 && rowNext - rowCur == -1) {
                 return 0;
             } else if (colNext - colCur == 1 && rowNext - rowCur == 1) {
                 rowNext--;
             } else if (colNext - colCur == 1 && rowNext - rowCur == -1) {
                 colNext--;
             } else if (colNext - colCur == -1 && rowNext - rowCur == 1) {
                 colNext++;
             } else if (rowNext - rowCur == 1) {
                 colNext++;
             } else if (rowNext - rowCur == -1) {
                 colNext--;
             } else if (colNext - colCur == 1) {
                 rowNext--;
             } else if (colNext - colCur == -1) {
                 rowNext++;
             }
             if(colNext>-1 && colNext<boardLngth && rowNext<boardLngth && rowNext>-1){
                 if(board[rowNext][colNext].value =='*'){
                     return 1+starCounter(false);
                 }
             }
             return starCounter(false);
         }

         int numCounter(boolean first){
             if(first){
                 colNext=colCur-1;
                 rowNext=rowCur;
             }
             if(colNext>-1 && colNext<boardLngth && rowNext<boardLngth && rowNext>-1){
                 int value = board[rowNext][colNext].value-48;
                 if(value>=0 && value<=8){
                     return 1+starCounter(false);
                 }
             }
             if (colNext - colCur == -1 && rowNext - rowCur == -1) {
                 return 0;
             } else if (colNext - colCur == 1 && rowNext - rowCur == 1) {
                 rowNext--;
             } else if (colNext - colCur == 1 && rowNext - rowCur == -1) {
                 colNext--;
             } else if (colNext - colCur == -1 && rowNext - rowCur == 1) {
                 colNext++;
             } else if (rowNext - rowCur == 1) {
                 colNext++;
             } else if (rowNext - rowCur == -1) {
                 colNext--;
             } else if (colNext - colCur == 1) {
                 rowNext--;
             } else if (colNext - colCur == -1) {
                 rowNext++;
             }
             return numCounter(false);
         }
     }
     class square{
        char value;
        int rowOwn, colOwn;
        square(char car){
            value = car;
            rowOwn=-1; colOwn=-1;
        }

     }

