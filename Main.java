import java.security.SecureRandom;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        Scanner scanner = new Scanner(System.in);

        Player p1 = new Player();
        System.out.print("Nome Player 1: ");
        p1.nome = scanner.nextLine();
        System.out.print("Classe (guerriero/mago/ladro): ");
        p1.classe = scanner.nextLine();

        if (p1.classe.equals("guerriero")) {
            p1.hp = 120; p1.agilita = 5;
        } else if (p1.classe.equals("mago")) {
            p1.hp = 80; p1.agilita = 10;
        } else {
            p1.hp = 100; p1.agilita = 15;
        }

        Player p2 = new Player();
        System.out.print("Nome Player 2: ");
        p2.nome = scanner.nextLine();
        System.out.print("Classe (guerriero/mago/ladro): ");
        p2.classe = scanner.nextLine();

        if (p2.classe.equals("guerriero")) {
            p2.hp = 120; p2.agilita = 5;
        } else if (p2.classe.equals("mago")) {
            p2.hp = 80; p2.agilita = 10;
        } else {
            p2.hp = 100; p2.agilita = 15;
        }

        int init1 = (random.nextInt(20) + 1) + p1.agilita;
        int init2 = (random.nextInt(20) + 1) + p2.agilita;

        Player primo, secondo;
        if (init1 >= init2) {
            primo = p1; secondo = p2;
        } else {
            primo = p2; secondo = p1;
        }

        System.out.println("Inizia: " + primo.nome);

        while (p1.hp > 0 && p2.hp > 0) {
            int dado1 = random.nextInt(20) + 1;
            System.out.println(primo.nome + " lancia: " + dado1);

            if (dado1 == 1) {
                System.out.println("miss");
            } else if (dado1 == 20) {
                System.out.println("critical hit");
                secondo.hp = secondo.hp - 40;
            } else {
                secondo.hp = secondo.hp - dado1;
            }

            if (secondo.hp > 0) {
                int dado2 = random.nextInt(20) + 1;
                System.out.println(secondo.nome + " lancia: " + dado2);
                if (dado2 == 1) {
                    System.out.println("miss");
                } else if (dado2 == 20) {
                    System.out.println("critical hit");
                    primo.hp = primo.hp - 40;
                } else {
                    primo.hp = primo.hp - dado2;
                }
            }
            System.out.println(p1.nome + ":" + p1.hp + " HP | " + p2.nome + ":" + p2.hp + " HP");
        }

        if (p1.hp <= 0) {
            System.out.println("Vincitore: " + p2.nome);
        } else {
            System.out.println("Vincitore: " + p1.nome);
        }
    }
}