package core.events;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Gestionnaire d'événements : maintient une file prioritaire d'événements ordonnés par date.
 * À chaque appel de next(), la date courante est incrémentée et les événements dont la date est atteinte sont exécutés.
 */
public class EventManager {
    private long currentDate = 0;

    // File prioritaire ordonnée par date croissante (événements les plus proches d'abord).
    private final PriorityQueue<Event> queue = new PriorityQueue<>(Comparator.comparingLong(Event::getDate));

    // Ajoute un évènement (il sera exécuté à sa date).
    public void addEvent(Event e) {
        if (e != null) queue.add(e);
    }

    // Incrémente la date courante, puis exécute (et retire de la file) tous les événements dont la date est <= date courante.
    public void next() {
        currentDate++;
        while (!queue.isEmpty() && queue.peek().getDate() <= currentDate) {
            Event e = queue.poll();
            e.execute();
        }
    }

    // Vrai si aucun événement n'est en attente d'exécution.
    public boolean isFinished() {
        return queue.isEmpty();
    }

    // Remise à zéro du gestionnaire.
    public void restart() {
        currentDate = 0;
        queue.clear();
    }

    // Accès à la date courante.
    public long getCurrentDate() {
        return currentDate;
    }
}
