package Day2;
import java.util.Scanner;
public class SimpleInterestCalculator{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println(Short.MIN_VALUE+" "+Short.MAX_VALUE);
        System.out.println("Enter Principle Amount:");
        int p=sc.nextInt();
        System.out.println("Enter Rate of Interest:");
        float r=sc.nextFloat();
        System.out.println("Enter Years");
        int t=sc.nextInt();
        System.out.println("Enter your name");
        String name=sc.next();
        double interest=(p*r*t)/100;
        System.out.println("Interest: "+interest);
//        if(interest>1000){
//            System.out.println("High Interest Earned");
//        }
//        else{
//            System.out.println("Low Interest Earned");
//        }
        for (t=1990;t<=2025;t++){
            double interest1=(p*r*t)/100;
            System.out.println("At the End of year "+t+" the Bank Interest is:"+interest1);
        }
    }
}