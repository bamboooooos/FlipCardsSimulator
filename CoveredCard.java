package others;

import java.util.ArrayList;
import java.util.Scanner;

public class CoveredCard {
    public static int[][] cards=new int[6][5];
    public static int[] num=new int[3];
    public static int[] totalNum=new int[3];
    public static int steps=0;
    public static ArrayList<X_Y> know=new ArrayList<>();
    public static ArrayList<X_Y> unKnown=new ArrayList<>();
    public static int totalSteps=0;
    public static void main(String[] args){
        Scanner in=new Scanner(System.in);
        System.out.println("请输入演算的次数:");
        int counts=in.nextInt();
        for(int i=0;i<counts;i++){
            solves();
        }
        System.out.println("演算后平均步数为:"+(double)totalSteps/(double) counts);
    }
    private static void solves(){
        randomTheCards();
        unKnown=new ArrayList<>();
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                unKnown.add(new X_Y(i,j));
            }
        }
        solveTheCards2();
        totalSteps+=steps;
        steps=0;
    }
    private static void solveTheCards1(){//第一步永远未知算法
        int now;
        int cursor;
        X_Y local,second;
        boolean flag=false;
        while (unKnown.size()!=0){
            local=unKnown.get(0);
            steps++;
            now=cards[local.x][local.y];
            for(cursor=0;cursor<know.size();cursor++){
                if(know.get(cursor).value==now){
                    steps++;
                    know.remove(cursor);
                    unKnown.remove(0);
                    flag=true;
                    cursor=100;
                }
            }
            if (!flag){
                second=unKnown.get(1);
                steps++;
                if (cards[second.x][second.y]!=now){
                    know.add(new X_Y(local.x,local.y,now));
                    know.add(new X_Y(second.x,second.y,cards[second.x][second.y]));
                }else{
                }
                unKnown.remove(0);
                unKnown.remove(0);
            }
            flag=false;
        }
    }
    private static void solveTheCards2(){//优先消除算法
        int now;
        X_Y local,second;
        boolean[] known=new boolean[3];
        while (unKnown.size()!=0){
            local=unKnown.get(0);
            steps++;
            now=cards[local.x][local.y];
            if(known[now-1]){
                known[now-1]=false;
                unKnown.remove(0);
                steps++;
            }else{
                second=unKnown.get(1);
                steps++;
                unKnown.remove(0);
                unKnown.remove(0);
                if(cards[second.x][second.y]!=now){
                    known[now-1]=true;
                    if(known[cards[second.x][second.y]-1]){
                        known[cards[second.x][second.y]-1]=false;
                        steps+=2;
                    }else{
                        known[cards[second.x][second.y]-1]=true;
                    }
                }
            }
        }
    }
    private static void getCards(){
        randomTheCards();
        totalNum[0]+=num[0];
        totalNum[1]+=num[1];
        totalNum[2]+=num[2];
        num[0]=0;
        num[1]=0;
        num[2]=0;
        /*
        for(int[] a:cards){
            for (int i:a){
                System.out.print(i+" ");
            }
            System.out.println("");
        }
        System.out.println(num[0]+" "+num[1]+" "+num[2]);*/
    }
    private static void randomTheCards(){
        for(int i=0;i<6;i++){
            if(i==5){
                for(int j=0;j<5;j++){
                    if(num[0]%2!=0){
                        cards[i][j]=1;
                        num[0]++;
                    }else{
                        if(num[1]%2!=0){
                            cards[i][j]=2;
                            num[1]++;
                        }else{
                            if(num[2]%2!=0){
                                cards[i][j]=3;
                                num[2]++;
                            }else{
                                int result = (int) (Math.random() * 30)/10;
                                num[result]++;
                                cards[i][j] = result+1;
                            }
                        }
                    }
                }
            }else {
                for (int j = 0; j < 5; j++) {
                    int result = (int) (Math.random() * 3);
                    num[result]++;
                    cards[i][j] = result+1;
                }
            }
        }
    }
}
