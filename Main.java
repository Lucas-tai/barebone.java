import java.io.*;
import java.util.*;
public class Main {
    HashMap<String, Integer> variable = new HashMap<String, Integer>();
    ArrayList<String> varName = new ArrayList<String>();
    List<List> while_loop = new ArrayList<List>();
    Stack<Integer> point = new Stack<Integer>();
    int endCheck = 0;
    int ac = 0;

    public void trans(String a, int line) {
        String[] as = a.split(" ");
        String[] acc = {"-1", "-1", "-1", "-1", "-1"};
        for (int i = 0; i < as.length; i++) {
            acc[i] = as[i];
        }

        // interpreting different syntax and operations
        switch (acc[0]) {
            case "clear":
                variable.put(acc[1], 0);
                break;
            case "incr":
                variable.put(acc[1], variable.get(acc[1]) + 1);
                break;
            case "decr":
                variable.put(acc[1], variable.get(acc[1]) - 1);
                break;
            case "while":
                if (acc[1]!= acc[3]) {
                    varName.add(acc[1]);
                    List list1 = new ArrayList<Integer>();
                    list1.add(variable.get(acc[1]));
                    list1.add(Integer.parseInt(acc[3]));
                    while_loop.add(list1);
                    point.add(line + 1);
                }

                break;
        }
        // update the value of variables in while_loop
        if (varName.size() > 0) {
            for (int y = 0; y < varName.size(); y++) {
                int k = variable.get(varName.get(y));
                while_loop.get(y).set(0, k);
            }
        }

    }
    // finding the "end"
    public void checkEnd(String dataa, String[] data, int b) {
        if (dataa.contains("end")) {
            int m = 10;
            while ( m != 0 && while_loop.get(while_loop.size() - 1).get(0) != while_loop.get(while_loop.size() - 1).get(1)) {
                m-=1;
                for (int seek = point.peek(); seek < b; seek++) {
                    trans(data[seek], seek);
                    if (data[seek].contains("end")) {
                        checkEnd(dataa,data,seek);
                    }
                }
            }
            point.pop();
            while_loop.remove(while_loop.size() - 1);
            varName.remove(varName.size() - 1);
        }
    }
// function running the program
    public void execution(){
        Readfile rf = new Readfile();
        rf.reading();
        String data_1 = rf.Getdata();
        String[] data = data_1.split(";");
        for( int i =0; i < data.length ;i++) {
            while(data[i].startsWith(" ")){
                data[i] = data[i].substring(1);
            }
        }
        int b = 0;
        for (int c= 0; c< data.length; c++) {
            trans(data[c], c);
            checkEnd(data[c],data, c);
        }
        // search for result
        for (String ii : variable.keySet()){
            System.out.println("Value of " + ii + " is: " + variable.get(ii));
        }
    }
    // main program
    public static void main(String[] args) {
        Main exe = new Main();
        exe.execution();
    }
}
