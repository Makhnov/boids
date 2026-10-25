# TO DO LIST

## 1. Erreurs

### Mains
- L'idée de GridSimu est bonne, autant y mettre getNeighbors() du coup ? 
    - GridSimu is a good idea, you should place getNeightbors in it since it's a non renderer function
- Simulator n'a plus/pas de board ! Ca va poser un pbm plus tard.
    - We need a board in Simu
- Déplacer le dessins des entités dans le simulator avec des polymorphismes (afficheEntity(), afficheBoard(), etc.) situés dans les branches (pour Balls, Conway & Immig)
    - Move entity draws in the simulator using polymorphism (afficheEntity(), afficheBoard(), etc.) in the branches (cell, particle, grid, space).
- Grid ne doit plus gérer les règles du jeu
    - Grid had to be stateless
- List entity au lieu de [] ? Est-ce dans l'optique des boids ? Mais surtout on a deux truc, d'un côté "
    ```java
    protected List<Entity> entities;
    ```
    et de l'autre 
    ```java
    private final Particle[] balls;
    ```
    Je pense qu'il vaut mieux rester sur l'idée de départ avec un Entity[] dans simu, hérité dans gridSimu/spaceSimu et les simus de game.
    I think it's better to stick with the original idea and using Entity[], inherited in gridSimu/spaceSimu and the systems's ones.

    - List entity instead of []? Is this choice in related to the inc. boids? 

### Just in case
- y'a un reset() dans le restart() (simu) donc un next() au lancement (dans les SubSimu), strange. 
    - Should'nt we gather all GUIs call, I do feel it'll be soon unsustainable otherwise
- remettre row et col dans grid
- Dans Particle.java, si "iswrapped" est vrai il y a deux fois screenHeight, normal ?
    - In Particle.java, in the "iswrapped" true section there is two calls for ScreenHeight ?
- Déjà vu avec qqn (Marcelo ?) mais quand on dessine la grid dans Conway, il y a une inversion des axes x et y.
    - Already noted with someone (Marcelo?), but when drawing the grid in Conway, there is an inversion of the x's and y's.


## 2. A implanter