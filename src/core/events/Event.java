package core.events;

/**
 * Représente un événement planifié à une date spécifique.
 * Les sous-classes doivent implémenter la méthode execute() pour définir l'action de l'événement.
 */
public abstract class Event {
    private final long date;

    // Construit un événement à la date donnée.
    public Event(long date) {
        this.date = date;
    }

    // Accès à la date de l'événement.
    public long getDate() {
        return date;
    }

// Action exécutée lors du déclenchement de l'événement.
    public abstract void execute();
}
