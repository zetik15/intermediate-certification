package controller;/*
Проанализировать персонажей "Крестьянин, Разбойник, Снайпер, Колдун, Копейщик, Арбалетчик, Монах".
Для каждого определит 8 полей данных(здоровье, сила итд) 3-4 поля поведения(методов атаковать, вылечить итд).
Создать абстрактный класс и иерархию наследников.
Расположить классы в пакет так, чтобы в основной программе небыло видно их полей.
В не абстрактных классах переопределить метод toString() так чтобы он возвращал название класса или имя.
Создать в основной программе по одному обьекту каждого не абстрактного класса и вывести в консоль его имя.

Добавить в абстрактный класс int поле инициатива. В классах наследников инициализировать это поле.
Крестьянин = 0, маги=1, пехота=2, лучники=3.
В мэйне сделать так, чтобы сначала делали ход персонажи с наивысшей инициативой
из обеих комманд а с наименьшей в конце.
*/

import behavior.CoordXY;
import behavior.HeroesNames;
import person.*;
import view.View;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static ArrayList<PersonBase> greenPersons = new ArrayList<>();
    public static ArrayList<PersonBase> bluePersons = new ArrayList<>();
    public static ArrayList<PersonBase> allPersons = new ArrayList<>();

    public static void main(String[] args) {
        createTeam(greenPersons, 10, 0);
        createTeam(bluePersons, 10, 3);
//        setDied(greenPersons,5);
//        setManas(greenPersons,30);
        allPersons.addAll(bluePersons);
        allPersons.addAll(greenPersons);
//        all.sort(new PrioritySort());
        allPersons.sort((o1, o2) -> Integer.compare(o2.priority, o1.priority));

        Scanner in = new Scanner(System.in);
        while (true)
        {
            View.view();

            for (PersonBase p : allPersons) {
                if (greenPersons.contains(p)) {
                    p.step(bluePersons, greenPersons);

                } else {
                    p.step(greenPersons, bluePersons);
                }
                System.out.println(p.getInfo());
            }
            in.nextLine();
            if (!isLiving(greenPersons))
            {
                System.out.println("Blue team wins!");
                break;
            }
            if (!isLiving(bluePersons))
            {
                System.out.println("Green wins!");
                break;
            }

        }

    }

    private static boolean isLiving(ArrayList<PersonBase> team)
    {
        for (PersonBase personBase : team) {
            if (personBase.getHealth() > 0)
                return true;
        }
        return false;
    }

    public static void setDied(ArrayList<PersonBase> team, int num)
    {
        for (PersonBase p : team) {
            if (p instanceof Wizard || p instanceof Monk)
                continue;
            p.healed(-p.getHealth());
            num--;
            if (num <= 0)
                break;
        }
    }
    public static void setManas(ArrayList<PersonBase> team, int mana)
    {
        for (PersonBase p : team) {
            if (p instanceof Wizard || p instanceof Monk)
            {
                ((MagicianBase) p).setMana(mana);
            }
        }
    }

    public static void createTeam(ArrayList<PersonBase> team, int num, int start)
    {
        Random rnd = new Random();
        int cy = 0;
        while (num-- > 0)
        {
            int n = start + rnd.nextInt(4);
            switch (n)
            {
                case 0:
                    team.add(new Crossbowman(HeroesNames.getRandomName(), new CoordXY(9, cy)));
                    break;
                case 1:
                    team.add(new Spearman(HeroesNames.getRandomName(), new CoordXY(9, cy)));
                    break;
                case 2:
                    team.add(new Wizard(HeroesNames.getRandomName(), new CoordXY(9, cy)));
                    break;
                case 3:
                    team.add(new Peasant(HeroesNames.getRandomName(), new CoordXY((3-start)*3, cy)));
                    break;
                case 4:
                    team.add(new Sniper(HeroesNames.getRandomName(), new CoordXY(0, cy)));
                    break;
                case 5:
                    team.add(new Monk(HeroesNames.getRandomName(), new CoordXY(0, cy)));
                    break;
                case 6:
                    team.add(new Robber(HeroesNames.getRandomName(), new CoordXY(0, cy)));
                    break;
                default:
                    System.out.println("ERROR!");
            }
            cy++;
        }
    }

}