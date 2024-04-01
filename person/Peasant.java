package person;

import behavior.CoordXY;

import java.util.ArrayList;

public class Peasant extends PersonBase{

    private static final int HEALTH = 500;
    private static final int POWER = 30;
    private static final int AGILITY = 30;
    private static final int DEFENCE = 0;
    private static final int DISTANCE = 1;
    private static final int FULL_BAG = 240;

    private int bag;                        // сколько стрел имеем с собой

    /**
     * Создание экземпляра Крестьянина
     * 
     * @param name Имя
     * @param pos  Положение в прогстранстве
     *
     */
    public Peasant(String name, CoordXY pos) {
        super(name, 0, HEALTH, POWER, AGILITY, DEFENCE, DISTANCE, pos);
        bag = FULL_BAG;
    }

    /**
     * Ход персонажа
     *
     * @param enemies Список его врагов
     */
    @Override
    public void step(ArrayList<PersonBase> enemies, ArrayList<PersonBase> friends)
    {
        history = "";

        if (health <= 0 || bag <= 0)
            return;
        ShooterBase p = (ShooterBase) getShooter(friends);
        if (p != null)
        {
            if (p.getAmmo() < p.getMaxAmmo())
            {
                p.setAmmo(p.getAmmo()+1);
                bag--;
                history = String.format(" дал стрелу %s", p);
            }
        }
    }

    /**
     * Ищет подходящего стрелка, с наименьшим запасом стрел
     *
     * @param friends   Своя команда
     * @return          Стрелок или null
     */
    private PersonBase getShooter(ArrayList<PersonBase> friends)
    {
        PersonBase p = null;
        int min = Integer.MAX_VALUE;

        for (PersonBase friend : friends)
        {
            if (friend.getHealth() > 0 && friend instanceof ShooterBase)
            {
                if (min > ((ShooterBase) friend).getAmmo())
                {
                    min = ((ShooterBase) friend).getAmmo();
                    p = friend;
                }
            }
        }
        return p;
    }

    @Override
    public String toString() {
        return String.format("[Крестьянин] (%s) %s { ❤️=%d, \uD83C\uDFF9=%d }", position.toString(), name, health, bag);
    }
}