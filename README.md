# Systèmes multiagents Orientés‑Objet

### Programmation Orientée Objet — ENSIMAG 2A — 2025

OUYAHIA Iles, MONTAURIOL Nicolas, JOUINI Youssef, DANG Duong Dang Khoa, BASSO Marcelo

---

## 📁 Structure du projet

Le code source de l’application est organisé en plusieurs *packages* situés dans `src/`.

```
src/
 ├─ app/
 ├─ core/
 ├─ entities/
 ├─ systems/
 ├─ world/
 └─ ...
```

### `app/`

Contient le fichier `Main` et la définition de l’interface graphique (UI).

* **app.ui** — Définit l'UI principal. La fenêtre est créée lors de l’exécution de `make`. Utilise `java.swing` et `java.awt` (JDK 25.0.1).
* **Main.java** — Initialise l’Arena (UI principale permettant de choisir un jeu).

### `core/`

Définit l’architecture de simulation.

* **Simulator** — Classe abstraite principale du projet. Contient une liste de `Entity` et un `Board`. Gère `update/draw/reset`.
* **Event** - Classe abstract qui implémente un Event utilisé sur EventManager.
* EventManager - Classe qui gére les évents.

### `entities/`

* **Entity** — Classe abstraite commune aux entités des cinq jeux (`Cell`, `Particle`). Possède un `Point`, une `Color` et des méthodes utilitaires.
* **Cell** — Utilisée dans les automates (Conway, Immigration, Schelling). Possède un `state`, `getState()`, `isAlive()`, `draw()`, etc.
* **Particle** — Entité avec position continue, utilisée dans `BallsSimulator` et `BoidsSimulator`.

* **Boid** - (Un type spécific de Particle) Base commune pour tous le types de boids (ici Fish et Shark). 
* boid/Fish - Boid du type Fish.
* boid/Shark - Boid du type Shark (mange Fish).
* boid/Octopus - Boid du type Octopus (mange Fish et Shark).

### `systems/`

C'est ici que nous avons crée les classes finaux des simulateurs.
* Tous les simulateurs héritent de `Simulator`.

* **BallsSimulator** — Affiche des balles en mouvement (`Simulator<Particle, Space>`).
* **BoidsSimulator** — Simulation multi‑agents de Boids (poissons = proies, requins = prédateurs).

* **GridSimulator** — Classe abstraite étendant `Simulator`. Base commune pour les automates cellulaires (Conway, Schelling, Immigration).
* **ConwaySimulator** — Implémente le jeu de Conway (`update()` définit les règles).
* **ImmigrationSimulator** — Variante de Conway avec `nbState` états.
* **SchellingSimulator** — Implémente le modèle de Schelling (`K`, `colorGroup`).
* **EventSimulator** — Adaptateur piloté par événements. L’UI appelle `next()` sur cet objet.

### `world/`

Définit les environnements de simulation.

* **Board** — Classe abstraite commune à `Grid` et `Space`.
* **Grid** — Environnement discret qui affiche un grille (Conway, Immigration, Schelling) + `draw()`.
* **Space** — Environnement continu fini (Balls, Boids) + `draw()`.

### Autres dossiers

* `debug/`, `images/`, `seed/` — Fichiers de débogage, images et fonctions d’initialisation aléatoire pour tous les jeux (seed).
* `doc/`, `doc-gui/` — Documentation fournie (`doc-gui`) et generé (`doc` - javadoc, `make doc`).
* `test/` — Tests unitaires (pour chacun des simulateurs et pour les entités Boid et Cell). Exécution via `make test`, et aussi pour chaque simulateur (voir si-dessous).

---

## ⚙️ Compilation & Tests

> **JDK : 25.0.1**

Compiler le projet :

```
make
```

Lancer tous les tests :

```
make tests
```

Tests spécifiques :

```
make test-conway
make test-schelling
make test-boids
make test-immigration
```

---

## 📚 Bibliothèques utilisées

Les dépendances se trouvent dans `/lib`.

* Java AWT — UI
* Java Swing — UI
* javadoc — documentation
* apiguardian-api‑1.1.2 — tests
* junit‑jupiter‑api‑5.14.0 — tests
* junit‑platform‑console‑standalone‑1.12.2 — tests
* java.util.concurrent.ThreadLocalRandom - balls
* junit‑4.13.1 — tests
* gui (fourni) — affichage des jeux

---

## 📄 Documentation

Génération de la javadoc :

```
make doc
```

Sortie dans `doc/`.

---

## 👥 Auteurs

* OUYAHIA Iles
* MONTAURIOL Nicolas
* JOUINI Youssef
* DANG Duong Dang Khoa
* BASSO Marcelo

---

