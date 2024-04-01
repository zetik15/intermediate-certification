package person;

import person.PersonBase;

import java.util.Comparator;

/**
 * Класс сортировки персонажей по их приоритету (скорости/очерёдности хода)
 */
public class PrioritySort  implements Comparator<PersonBase> {

    @Override
    public int compare(PersonBase o1, PersonBase o2) {
        return Integer.compare(o2.priority, o1.priority);
    }
}