import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class mineSweeper{
    int n;
    int[][] field;
    int[][] checkedField;
    mineSweeper(){
        checkedField=new int[n][n];
        field=new int[n][n];
        fill();
    }
    void fill(){
        for (int[] i:checkedField){
            Arrays.fill(i,0);
        }
        for(int[] i:field){
            Arrays.fill(i,0);
        }
        Random rand=new Random();
        int prob;
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };
        for (int i=0;i<n;i++){
            for (int j = 0; j < n; j++) {
                prob=rand.nextInt(0,100);
                if(prob>80){
                    field[i][j]=-10;
                    for (int[] dir : directions) {
                        int ni = i + dir[0];
                        int nj = j + dir[1];

                        if (ni >= 0 && ni < n && nj >= 0 && nj < n) {
                            field[ni][nj]++;
                        }
                    }

                }
            }
        }

    }
    void printState(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n ; j++) {
                if(checkedField[i][j]==0){
                    System.out.print("#");
                } else if (checkedField[i][j]==1) {
                    System.out.print(field[i][j]);
                }else{
                    System.out.print("F");
                }
            }
        }
    }
    List<String> readLine(String str){
        Pattern pat=Pattern.compile("\\b\\w+\\b");
        Matcher mat= pat.matcher(str);
        List<String> lines=new ArrayList<>();
        while(mat.find()){
            lines.add(mat.group());
        }
        lines.add("-1");
        lines.add("-1");
        lines.add("-1");
        lines.add("-1");
        return lines;
    }
    void play(){
        Scanner scan=new Scanner(System.in);
        while (true){
            printState();
            boolean isCompete=true;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(checkedField[i][j]==0 || (checkedField[i][j]==2 && field[i][j]>=0)){
                        isCompete=false;
                    } else if (checkedField[i][j]==1 && field[i][j]<0) {
                        System.out.println("Game over");
                    }
                }
            }
            if(isCompete){
                System.out.println("you won!!");
                return;
            }
            List<String> lines=readLine(scan.nextLine());
            if(lines.getFirst().equals("put") && lines.get(1).equals("flag")){
                checkedField[Integer.parseInt(lines.get(2))][Integer.parseInt(lines.get(3))]=2;
            }
            else if(lines.getFirst().equals("open")){
                checkedField[Integer.parseInt(lines.get(1))][Integer.parseInt(lines.get(2))]=1;
            }
            else{
                System.out.println("wrong input");
            }
        }
    }


}

public class Main {
    public static void main(String[] args) {
        mineSweeper game=new mineSweeper();
        System.out.println("input\n put flag x y \nto put flag on (x,y) or \nopen x y\nto open x y");
        game.play();
    }
}