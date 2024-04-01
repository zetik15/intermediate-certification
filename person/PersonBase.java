package person;

import behavior.ActionInterface;
import behavior.CoordXY;

import java.util.ArrayList;
import java.util.Random;

/**
 * База для персонажей.
 */
public abstract class PersonBase implements ActionInterface {
    protected static Random rnd;
    static {
        rnd = new Random();
    }

    protected String name;
    public int priority;                 // приоритет хода
    protected int health;                   // здоровье (0 - убит, -1 - ждет восстановления)
    protected final int maxHealth;
    protected final int power;              //
    protected final int agility;            // ловкость
    protected final int defence;            //
    protected int distance;                 // дистанция воздействия на другой объект

    protected CoordXY position;             // позиционирование и перемещение

    protected String history;

    /**
     * Конструктор базы
     * @param name Имя
     * @param priority Приоритет хода
     * @param health Текущее здоровье
     * @param power Сила
     * @param agility Ловкость (%). 3 ловкости = 1% к увороту, и 10 ловкости = 1% к критическому удару
     * @param defence Защита (% к сопротивлению урону)
     * @param distance Дистанция воздействия на другой объект (10 у мага, 1 у крестьянина и тд)
     * @param pos Положение в прогстранстве
     */
    protected PersonBase(String name, int priority, int health, int power, int agility, int defence, int distance, CoordXY pos)
    {
        this.name = name;
        this.priority = priority;
        this.health = getRound(health, 10);
        this.maxHealth = this.health;
        this.power = getRound(power, 10);
        this.agility = getRound(agility, 10);
        this.defence = defence;
        this.distance = distance;
        this.position = pos;
        this.history = "";
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getPriority() {
        return priority;
    }

    /**
     * Возвращает значение со случайной погрешностью в +-percent%
     * @param origin Начальное значение
     * @param percent Погрешность
     * @return Значение с внесённой погрешностью
     */
    protected int getRound(int origin, int percent)
    {
        if (percent > origin)
            return origin;
        int n = (origin * percent) / 100;
        return origin + (rnd.nextInt(0, n * 2+1)- n);
    }

    /**
     * Задаёт местоположение персонажа
     * @param x По оси X
     * @param y По оси Y
     */
    public void setPosition(int x, int y)
    {
        position.setXY(x, y);
    }

    /**
     * Возвращает текущее местоположение персонажа
     * @return
     */
    public CoordXY getPosition()
    {
        return position;
    }


    /**
     * Лечение персонажа
     * @param health Количество добавляемого здоровья
     */
    public void healed(int health)
    {
        this.health = Math.min(this.health +health, this.maxHealth);
    }

    /**
     * Персонаж принимает урон
     * @param damage Величина урона (конечная будет зависеть от @defence и ловкости)
     * @return Количество действительно нанесенного урона
     */
    public int getDamage(int damage)
    {
        boolean probability = (this.agility/2) >= rnd.nextInt(100);
        if (probability)
        {
//            System.out.print(" но " + name + " увернулся!");
            return 0;           // увернулись
        }

        int loss = damage - (this.defence * damage) / 100;
        loss = Math.min(loss, this.health);
        this.health -= loss;
//        if (this.health <= 0)
//        {
//            System.out.println(name + ": вышел из чата!");
//        }
        return loss;
    }

    /**
     * Поиск ближайшего персонажа из доступных
     * @param persons Массив персон (врагов или своих)
     * @return        Ближайший к текущему персонаж
     */
    public PersonBase findNearestPerson(ArrayList<PersonBase> persons)
    {
        PersonBase target = null;
        float minDistance = Float.MAX_VALUE;

        for (PersonBase p : persons)
        {
            if (p.health > 0)
            {
                float dist = position.distanceTo(p.position);
                if (dist < minDistance)
                {
                    minDistance = dist;
                    target = p;
                }
            }
        }
        return target;
    }

    @Override
    public String getInfo() {
        return this.toString() + history;
    }

//    @Override
//    public String toString()
//    {
//        return String.format("[%s] %s", this.getClass().getSimpleName(), this.name);
//    }

}