import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class mineSweeper{
    int n=15;
    int[][] field=new int[n][n];
    int[][] checkedField=new int[n][n];
    mineSweeper(){
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
                if(prob>70){
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
    void printState() {
        System.out.print("    ");
        for (int i = 0; i < n; i++) {
            System.out.printf("%3d", i);
        }
        System.out.println("  <- x");

        for (int i = 0; i < n; i++) {
            System.out.printf("%3d", i);
            for (int j = 0; j < n; j++) {
                if (checkedField[i][j] == 0) {
                    System.out.print("  #");
                } else if (checkedField[i][j] == 1) {
                    System.out.printf("%3d", field[i][j]);
                } else {
                    System.out.print(" 🚩");
                }
            }
            System.out.println();
        }
        System.out.println(" ^");
        System.out.println(" y");
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
    void open(int x, int y){
        if(checkedField[x][y]!=0){
            return;
        }
        checkedField[x][y]=1;
        if(field[x][y]!=0){
            return;
        }
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };
        for (int[] dir : directions) {
            int ni = x + dir[0];
            int nj = y + dir[1];

            if (ni >= 0 && ni < n && nj >= 0 && nj < n) {
                open(ni,nj);
            }
        }

    }
    void play(){
        Scanner scan=new Scanner(System.in);

        while (true){
            boolean isCompete=true;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(checkedField[i][j]==0 || (checkedField[i][j]==2 && field[i][j]>=0)){
                        isCompete=false;
                    } else if (checkedField[i][j]==1 && field[i][j]<0) {
                        for (int k = 0; k < n; k++) {
                            for (int f = 0; f < n ; f++) {
                                if(field[k][f]>-1){
                                    System.out.print(" " + field[k][f] +" ");
                                }else{
                                    System.out.print(" "+ "🧨");
                                }
                            }
                            System.out.println();
                        }
                        System.out.println("Game over");
                        return;
                    }
                }
            }
            printState();
            if(isCompete){
                System.out.println("you won!!");
                return;
            }
            List<String> lines=readLine(scan.nextLine());
            int i=Integer.parseInt(lines.get(1));
            int j=Integer.parseInt(lines.get(2));
            if(i<n && j<n && i>-1 && j>-1){
                if(lines.getFirst().equals("fl")){
                    checkedField[i][j]=2;
                }
                else if(lines.getFirst().equals("o")){
                    open(i,j);
                }
                else{
                    System.out.println("wrong input");
                }
            }
            else{
                System.out.println("out of bounds");
            }
        }
    }


}

public class Main {
    public static void main(String[] args) {
        mineSweeper game=new mineSweeper();
        System.out.println("input\nfl x y \nto put flag on (x,y) or \no x y\nto open x y");
        game.play();
    }
}