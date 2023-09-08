package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MachineCommand machine = new MachineCommand(400, 540, 120 , 9, 550);
        while(machine.Online()){
            machine.menu(sc.nextLine());
        }
    }
}
