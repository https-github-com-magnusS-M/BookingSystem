/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieticketbooking.user;


public class Seat {
    String number;
    int col;
    int row;
    public Seat(String num,int row,int col){
        this.col=col;
        this.row=row;
        this.number=num;
    }
    public int getCol(){
        return col;
    }
    public int getRow(){
        return row;
    }
    public String getNumber(){
        return number;
    }
}
