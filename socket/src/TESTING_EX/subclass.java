package TESTING_EX;


import java.util.Scanner;

public class subclass {
    Scanner scanner= new Scanner(System.in);
    public void printName(){
        System.out.println("enter votre nom: ");
        String name = scanner.nextLine();
        System.out.println(name);
    }
}
