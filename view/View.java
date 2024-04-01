package view;

import behavior.CoordXY;
import person.PersonBase;
import controller.Main;

import java.util.Collections;

public class View {
    private static int step = 1;
    private static int maxLengthMsg = 0;
    private static final String top10 = formatDiv("a") + String.join("", Collections.nCopies(9, formatDiv("-b"))) + formatDiv("-c");
    private static final String midl10 = formatDiv("d") + String.join("", Collections.nCopies(9, formatDiv("-e"))) + formatDiv("-f");
    private static final String bottom10 = formatDiv("g") + String.join("", Collections.nCopies(9, formatDiv("-h"))) + formatDiv("-i");

    private static void tabSetter(int cnt, int max)
    {
        int dif = max - cnt + 2;
//      old:
//        if (dif > 0) System.out.printf("%" + dif + "s", ":\t"); else System.out.print(":\t");

        if (dif > 0)
            System.out.printf("%" + dif + "s", " ");
        System.out.print(": ");
    }

    private static String formatDiv(String str) {
        return str.replace('a', '┌')
                .replace('b', '┬')
                .replace('c', '┐')
                .replace('d', '├')
                .replace('e', '┼')
                .replace('f', '┤')
                .replace('g', '└')
                .replace('h', '┴')
                .replace('i', '┘')
                .replace('-', '─');
    }

    private static String getChar(int x, int y)
    {
        String out = "│ ";
        for (PersonBase human: Main.allPersons)
        {
            CoordXY pos = human.getPosition();
            if (pos.getX() == x && pos.getY() == y)
            {
                if (human.getHealth() <= 0)
                {
                    out = "│" + (AnsyView.ANSI_RED + human.getInfo().charAt(1) + AnsyView.ANSI_RESET);
                    break;
                }
                if (Main.greenPersons.contains(human)) out = "│" + (AnsyView.ANSI_GREEN + human.getInfo().charAt(1) + AnsyView.ANSI_RESET);
                if (Main.bluePersons.contains(human)) out = "│" + (AnsyView.ANSI_BLUE + human.getInfo().charAt(1) + AnsyView.ANSI_RESET);
                break;
            }
        }
        return out;
    }

    public static void view() {
        if (step == 1 ){
            System.out.print(AnsyView.ANSI_RED + "First step" + AnsyView.ANSI_RESET);
        } else {
            System.out.print(AnsyView.ANSI_RED + "Step:" + step + AnsyView.ANSI_RESET);
        }
        step++;
        Main.allPersons.forEach((v) -> maxLengthMsg = Math.max(maxLengthMsg, v.toString().length()));
        System.out.print("_".repeat(maxLengthMsg *2));
        System.out.println();
        System.out.print(top10 + "    ");
        System.out.print(AnsyView.ANSI_BLUE + "Blue side" + AnsyView.ANSI_RESET);
        tabSetter(9, maxLengthMsg);                 // old: System.out.print(" ".repeat(maxLengthMsg -9));
        System.out.println(AnsyView.ANSI_GREEN +
                           "Green side" +
                           AnsyView.ANSI_RESET);    // old: System.out.println(":\tGreen side");
        for (int x = 0; x < 10; x++) {
            System.out.print(getChar(x, 0));        // old: System.out.print(getChar(0, x));
        }
        System.out.print("|    ");
        System.out.print(Main.bluePersons.get(0));
        tabSetter(Main.bluePersons.get(0).toString().length(), maxLengthMsg);
        System.out.println(Main.greenPersons.get(0));
        System.out.println(midl10);

        for (int y = 1; y < 9; y++)
        {
            for (int x = 0; x < 10; x++) {
                System.out.print(getChar(x, y));    // old: System.out.print(getChar(y, x));
            }
            System.out.print("|    ");
            System.out.print(Main.bluePersons.get(y));
            tabSetter(Main.bluePersons.get(y).toString().length(), maxLengthMsg);
            System.out.println(Main.greenPersons.get(y));
            System.out.println(midl10);
        }
        for (int x = 0; x < 10; x++) {
            System.out.print(getChar(x, 9));        // old: System.out.print(getChar(9, x));
        }
        System.out.print("|    ");
        System.out.print(Main.bluePersons.get(9));
        tabSetter(Main.bluePersons.get(9).toString().length(), maxLengthMsg);
        System.out.println(Main.greenPersons.get(9));
        System.out.println(bottom10);
    }
}