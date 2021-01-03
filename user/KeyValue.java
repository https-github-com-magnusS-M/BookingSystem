/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieticketbooking.user;

public class KeyValue {
    private int Key;
    private String Value;
    public KeyValue(int k, String v)
        {
            this.Key = k;
            this.Value = v;
        }
    public int getKey(){
        return Key;
    }
    public String getValue(){
        return Value;
    }
   @Override
    public String toString() {
    return Value;
    }
}
