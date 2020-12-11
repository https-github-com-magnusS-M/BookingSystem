/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieticketbooking.admin;


public class RemovedSeat {
    private int colIndex;
    private int rowIndex;

    public RemovedSeat(int col,int row) {
        colIndex=col;
        rowIndex=row;
    }
    public int getCol(){
        return colIndex;
    }
     public int getRow(){
        return rowIndex;
    }
    
}
