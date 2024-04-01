package person;

import behavior.CoordXY;

/**
 * Класс Разбойник, более ловкий чем Копейщик, но имеет меньшую силу и защиту
 */
public class Robber extends InfantryBase {

    private static final int HEALTH = 1000;
    private static final int POWER = 70;
    private static final int AGILITY = 60;
    private static final int DEFENCE = 10;
    private static final int DISTANCE = 1;

    /**
     * Создание экземпляра Разбойника
     *
     * @param name Имя
     * @param pos  Положение в прогстранстве
     */
    public Robber(String name, CoordXY pos)
    {
        super(name, 2, HEALTH, POWER, AGILITY, DEFENCE, DISTANCE, pos);
    }


    @Override
    public String toString() {
        return String.format("[Разбойник] (%s) %s { ❤️=%d }", position.toString(), name, health);
    }

}