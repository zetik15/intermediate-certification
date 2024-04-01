package person;

import behavior.CoordXY;

import java.util.ArrayList;

/**
 * Базовый класс для стрелков, в данном случае для Снайперов и Арбалетчиков,
 * но можно добавить Мушкетёров или Катапульту :)
 */
public abstract class ShooterBase extends PersonBase {

    protected int ammo;                         // боеприпасов в наличии
    protected int maxAmmo;
    protected int level;                        // уровень (зависит от опыта и даёт увеличение урона)
    protected int effectiveDistance;            // макс. дальность эффективной стрельбы

    /**
     * Конструктор базы Стрелков
     *
     * @param name     Имя
     * @param priority Приоритет хода
     * @param health   Текущее здоровье
     * @param power    Сила
     * @param agility  Ловкость (%). 3 ловкости = 1% к увороту, и 10 ловкости = 1% к критическому удару
     * @param defence  Защита (% к сопротивлению урону)
     * @param distance Дистанция воздействия на другой объект (10 у мага, 1 у крестьянина и тд)
     * @param ammo Количество боезапаса (стрел)
     * @param effectiveDistance Эффективная дальность стрельбы
     * @param pos Положение в прогстранстве
     */
    protected ShooterBase(String name, int priority, int health, int power, int agility, int defence, int distance, int ammo, int effectiveDistance, CoordXY pos)
    {
        super(name, priority, health, power, agility, defence, distance, pos);
        this.ammo = ammo;
        this.maxAmmo = ammo;
        this.effectiveDistance = effectiveDistance;
        this.level = 1;
    }


    public int getAmmo() {
        return ammo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    protected void setAmmo(int ammo)
    {
        this.ammo = Math.min(ammo, maxAmmo);
    }
    /**
     * Атака противника
     * 
     * @param target Противник
     */
    protected void shot(PersonBase target)
    {
        ammo--;
        float dist = position.distanceTo(target.position);
        int damage = getRound(power, 10) + (power / 10) * level;
        if (dist > effectiveDistance)
            damage *= 0.5f;
        else if (dist < effectiveDistance)
            damage *= 1.2f;

        boolean critical = (this.agility/3) >= rnd.nextInt(100);
        if (critical)
        {
            damage *= 2.0f;
        }
        int res = target.getDamage(damage);

        history = String.format(" атаковал %s ", target);
        if (res == 0)
        {
            history += "но он увернулся!";
        } else {
            history += "и нанёс ";
            if (critical)
            {
                history += "критический ";
            }
            history += "урон в " + res;
        }
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

        if (health <= 0)
            return;

        if (ammo > 0)
        {
            PersonBase target = this.findNearestPerson(enemies);
            if (target != null)
            {
                shot(target);
            }
        } else {
            history = String.format(" ждёт подвоза стрел.");
        }
    }

}