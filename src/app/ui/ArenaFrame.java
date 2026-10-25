package app.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ArenaFrame extends JFrame {

    private final CardLayout cards = new CardLayout();
    private final JPanel root = new JPanel(cards);

    // Bouton "Quitter le jeu" (visible seulement quand un jeu est lancé).
    private JButton quitGameButton;


    /**
     * Fenêtre d’accueil + menu.
     * Lancement d'un jeu :
     * Dans le menu principal quand vous cliquez sur un jeu, il se lance dans une autre fenêtre, si un autre jeu était ouvert il sera fermé.
     * Bouton quitter :
     * Quitte le jeu en cours.
     */

    public ArenaFrame() {
        setTitle("Arena of Games");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        root.add(buildWelcome(), "WELCOME");
        root.add(buildMenu(), "MENU");
        setContentPane(root);
        cards.show(root, "WELCOME");
    }

    // Jcomponent : Constructeur html/css/js pour java

    // page d'accueil
    private JComponent buildWelcome() {
        JPanel p = new UiKit.GradientPanel(new Color(25, 24, 38), new Color(35, 90, 160));
        p.setLayout(new GridBagLayout());
        p.setBorder(new EmptyBorder(40, 60, 40, 60));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(12, 12, 12, 12);

        // Titre accueil
        JLabel title = new JLabel("Welcome, Challenger!");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 42f));

        // Message d'accueil
        JLabel sub = new JLabel("<html><div style='text-align:center;'>"
                + "Ready for some simulations?<br>Enter the arena.</div></html>");
        sub.setForeground(new Color(230, 235, 245));
        sub.setFont(sub.getFont().deriveFont(18f));

        // Bouton accueil
        JButton enter = UiKit.primary("Enter to the arena of games");
        enter.addActionListener(e -> cards.show(root, "MENU"));

        c.gridy = 0;
        p.add(title, c);
        c.gridy = 1;
        p.add(sub, c);
        c.gridy = 2;
        p.add(enter, c);
        return p;
    }

    // menu principal
    private JComponent buildMenu() {
        JPanel outer = new UiKit.GradientPanel(new Color(22, 27, 34), new Color(28, 38, 58));
        outer.setLayout(new BorderLayout());
        outer.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel header = new JLabel("Choose your simulator");
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 28f));
        header.setBorder(new EmptyBorder(0, 0, 16, 0));

        JPanel grid = new JPanel(new GridLayout(2, 3, 16, 16));
        grid.setOpaque(false);

        JButton conway = UiKit.primary("Conway");
        JButton balls = UiKit.primary("Balls");
        JButton boids = UiKit.primary("Boids");
        JButton immig = UiKit.primary("Immigration");
        JButton schel = UiKit.primary("Schelling");

        // Nouveau bouton "Quitter le jeu" (texte lisible  mais grisé lorsqu'il est désactivé)
        quitGameButton = UiKit.stylePrimary(new JButton("Quitter le jeu") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!isEnabled()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    FontMetrics fm = g2.getFontMetrics(getFont());
                    int textWidth = fm.stringWidth(getText());
                    int x = (getWidth() - textWidth) / 2;
                    int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                    g2.setColor(DISABLED_TEXT);
                    g2.drawString(getText(), x, y);
                    g2.dispose();
                }
            }
        });

        quitGameButton.addActionListener(e -> {
            // Ferme les fenêtres de simulation, et masque le bouton "Quitter"
            GuiWindows.closeAllSimulators();
            updateQuitGameButton(false);
        });
        updateQuitGameButton(false); // aucun jeu lancé au démarrage

        // Une seule fenêtre de simulation à la fois (voir fonction startSimulation)
        /* 
        * Pour les simulateurs à grille :
        * Largeur, hauteur, nb lignes, nb colonnes, couverture (%), couleur fond
        */

        conway.addActionListener(e -> startSimulation("Sim-Conway",
                () -> new systems.ConwaySimulator(900, 600, 50, 50, 20, Color.WHITE).play()));

        immig.addActionListener(e -> startSimulation("Sim-Immig",
                () -> new systems.ImmigrationSimulator(900, 600, 50, 50, 90, Color.WHITE, 3).play()));

        schel.addActionListener(e -> startSimulation("Sim-Schelling",
                () -> new systems.SchellingSimulator(900, 600, 50, 50, Color.WHITE, 3, 2, 99).play()));

        balls.addActionListener(
                e -> startSimulation("Sim-Balls", () -> new systems.BallsSimulator(900, 600, 15, 20).play()));

        boids.addActionListener(e -> startSimulation("Sim-Boids", () -> new systems.BoidsSimulator(
                900, 600, 400, 150,
                8, 3,
                2, 1,
                1.6, 0.12, 0.02).play()));
        /*
         * Détails des paramètres des boids (commentaire original) :
         * Largeur/hauteur de la fenêtre, nombre de boids, taille des cellules,
         * rayon de perception, distance de séparation, vitesse max, pas max,
         * poids d’alignement, de cohésion et de séparation.
         */

        grid.add(conway);
        grid.add(balls);
        grid.add(boids);
        grid.add(immig);
        grid.add(schel);
        grid.add(quitGameButton);

        outer.add(header, BorderLayout.NORTH);
        outer.add(grid, BorderLayout.CENTER);
        return outer;
    }

    /**
     * Lance une simulation dans un thread séparé :
     * 1. Ferme les anciennes fenêtres de simulation
     * 2. Lance la simulation sélectionnée
     * 3. Active le bouton "Quitter le jeu"
     */
    private void startSimulation(String threadName, Runnable simulation) {
        // 1) fermer toute fenêtre de simulation existante
        GuiWindows.closeAllSimulators();

        // 2) afficher le bouton "Quitter le jeu"
        updateQuitGameButton(true);

        // 3) lancer la simulation dans un thread séparé
        new Thread(() -> {
            try {
                simulation.run();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }, threadName).start();
    }

    // Met à jour le bouton "Quitter le jeu".
    private static final Color PRIMARY_BLUE = new Color(66, 133, 244);
    private static final Color DISABLED_TEXT = Color.WHITE;
    private static final Color INACTIVE_BLUE = new Color(90, 120, 190);

    private void updateQuitGameButton(boolean gameRunning) {
        if (quitGameButton == null)
            return;

        Runnable r = () -> {
            quitGameButton.setVisible(true); // toujours visible après le 1er jeu

            if (gameRunning) {
                quitGameButton.setEnabled(true);
                quitGameButton.setBackground(PRIMARY_BLUE);
                quitGameButton.setForeground(Color.WHITE); // texte blanc bien lisible
            } else {
                quitGameButton.setEnabled(false);
                quitGameButton.setBackground(INACTIVE_BLUE); // look proche du design initial
                quitGameButton.setForeground(DISABLED_TEXT); // garde le libellé visible
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }
}
