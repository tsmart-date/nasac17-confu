package test;

import java.io.Serializable;

public class Entity implements Serializable {
    public Entity a;
    public int b;
//    public String[] strings = new String[10];
    public String[] strings = new String[3];
//    public int[] values = {1,2,3,4,5,6,7,8,9,10};
    public int[] values = new int[3];

    public Entity(int b) {
        this.b = b;
    }

    public Entity add(int b) {
        a = new Entity(10);
        return a;
    }

//    public int[] addInt(int[] vals){
//        System.out.println(vals[0]);
//        return new int[]{1,2,3};
//    }

    public void addString(String str) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = str;
            strings[i].equals("gel");
            values[i] = i;
//            int[] valueArray = addInt(values);
        }
    }
}
