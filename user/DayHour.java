/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieticketbooking.user;

import java.util.Vector;


public class DayHour {
    private String day;
    private Vector<KeyValue> hours;
    DayHour(){
        day="";
        hours=new Vector<>();
    }
    public void addDay(String d){
        this.day=d;
    }
    public void addHours(Vector<KeyValue> h){
       
        hours=h;
    }
    public void addHour(KeyValue h){
        
        hours.add(h);
    }
    public String getDay(){
        return day;
    }
     public Vector getHours(){
        return hours;
    }
}
