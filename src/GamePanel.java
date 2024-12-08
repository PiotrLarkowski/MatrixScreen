import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    boolean escPressed = false;
    boolean timeCheck = false;
    Thread gameThread;
    final int FPS = 60;
    static Random random = new Random();
    static int randomInt;
    static int randomCharacter;
    static ArrayList<ArrayList<SimpleLetter>> listOfActiveChains = new ArrayList<>();
    static ArrayList<SimpleLetter> characters = new ArrayList<>();
    long timeNow = 0;
    long timePass = LocalDateTime.now().getSecond();
    long startTime = System.nanoTime();
    boolean newLetter = true;
    KeyHandler keyHandler = new KeyHandler();
    int activeLevelOfApplication = 0;

    public GamePanel() {

        initializeListOfActiveChains();
        createMainWindow();

        addKeyListener(keyHandler);

        gameThread = new Thread(this);
        gameThread.start();
    }

    private void createMainWindow() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setFocusable(true);
    }

    private static void initializeListOfActiveChains() {
        for (int i = 0; i < WIDTH; i++) {
            SimpleLetter simpleLetter = new SimpleLetter(false, 0, 0, '0');
            listOfActiveChains.add(i, new ArrayList<>(List.of(simpleLetter)));
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (escPressed) {
            if (!timeCheck) {
                timeNow = LocalDateTime.now().getSecond();
                timeCheck = true;
            }
            timePass = LocalDateTime.now().getSecond();
            g2.setColor(Color.GREEN);
            g2.fillRect((WIDTH / 2) - 200, (HEIGHT / 2) - 80, 400, 200);
            g2.setColor(Color.BLACK);
            g2.fillRect((WIDTH / 2) - 198, (HEIGHT / 2) - 78, 396, 196);
            g2.setColor(Color.GREEN);
            g2.setFont(new Font("Arial", Font.BOLD, 48));
            g2.drawString("SYSTEM", (WIDTH / 2) - 100, (HEIGHT / 2) + 10);
            g2.drawString("FAILURE", (WIDTH / 2) - 105, (HEIGHT / 2) + 55);
            if (timePass - timeNow > 2) {
                System.exit(0);
            }
        } else {
            for (int i = 0; i < listOfActiveChains.size(); i++) {
                for (int j = 0; j <listOfActiveChains.get(i).size(); j++) {
                    if (listOfActiveChains.get(i).get(j).active) {
                        SimpleLetter simpleLetter = listOfActiveChains.get(i).get(j);
                        g2.setColor(Color.GREEN);
                        g2.setFont(new Font("Arial", Font.PLAIN, 13));
                        g2.drawString("" + simpleLetter.letter, simpleLetter.xCoordinate, simpleLetter.yCoordinate);
                    }
                }
            }
        }
    }

    private void addNextLetter(Graphics2D g2, int yValue) {

    }

    public void update() {
        if (newLetter) {
            startTime = System.nanoTime();
            newLetter = false;
        }
        long stopTime = System.nanoTime();
        long interval = (stopTime - startTime);
        double second = (double) interval / 1000000000.0;
        if (second > 1) {
            if (activeLevelOfApplication == 0) {
                randomInt = random.nextInt(WIDTH);
                randomCharacter = random.nextInt(255);
                listOfActiveChains.add(randomInt, new ArrayList<>(List.of(new SimpleLetter(true, randomInt, 15, (char) randomCharacter))));
            }
            newLetter = true;
        }
        if (KeyHandler.escPressed) {
            escPressed = true;
        }
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;
        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }
}
