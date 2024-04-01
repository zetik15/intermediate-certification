package person;

import behavior.CoordXY;

/**
 * Класс Копейщик, сильнее и защищённее Разбойника, но менее ловкий
 */
public class Spearman extends InfantryBase {

    private static final int HEALTH = 1000;
    private static final int POWER = 80;
    private static final int AGILITY = 10;
    private static final int DEFENCE = 12;
    private static final int DISTANCE = 1;

    /**
     * Создание экземпляра Копейщика
     *
     * @param name Имя
     * @param pos  Положение в прогстранстве
     */
    public Spearman(String name, CoordXY pos)
    {
        super(name, 2, HEALTH, POWER, AGILITY, DEFENCE, DISTANCE, pos);
    }

    @Override
    public String toString() {
        return String.format("[Пикенёр] (%s) %s { ❤️=%d }", position.toString(), name, health);
    }

}