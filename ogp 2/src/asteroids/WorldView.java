package asteroids;

import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class WorldView<World, Ship, Asteroid, Bullet> extends JPanel implements KeyListener, ActionListener, CollisionListener {

  private static final int LEFT_P1 = KeyEvent.VK_LEFT;
  private static final int RIGHT_P1 = KeyEvent.VK_RIGHT;
  private static final int FIRE_P1 = KeyEvent.VK_SPACE;
  private static final int THRUSTER_P1 = KeyEvent.VK_UP;
  private static final int LEFT_P2 = KeyEvent.VK_A; // change to Q on Azerty
  private static final int RIGHT_P2 = KeyEvent.VK_D;
  private static final int FIRE_P2 = KeyEvent.VK_CONTROL;
  private static final int THRUSTER_P2 = KeyEvent.VK_W; // change to Z on Azerty

  private static final int TIMER_DELAY = 1000 / 30;

  private Asteroids<World, Ship, Asteroid, Bullet> game;
  private IFacade<World, Ship, Asteroid, Bullet> facade;
  private World world;
  private Ship player1, player2;
  private double player1_angle, player2_angle;
  private boolean player1_fire, player2_fire;
  private Timer timer;
  private long timeAfterLastEvolve;
  private Image background;
  private String msg = null;
  private Map<Object, Visualization<?>> visualizations = new HashMap<Object, Visualization<?>>();
  private Set<Explosion> explosions = new HashSet<Explosion>();

  public WorldView(Asteroids<World, Ship, Asteroid, Bullet> game, World world, Ship player1, Ship player2) {
    this.game = game;
    this.facade = game.getFacade();
    this.world = world;
    this.player1 = player1;
    this.player2 = player2;
    this.timer = new Timer(TIMER_DELAY, this);
    setBackground(Color.BLACK);
    ClassLoader loader = WorldView.class.getClassLoader();
    background = Toolkit.getDefaultToolkit().getImage(loader.getResource("asteroids/resources/game-background.jpg"));
    background = background.getScaledInstance(game.getWidth(), game.getHeight(), Image.SCALE_DEFAULT);
    if (player1 != null) {
      int size = (int) (2 * facade.getShipRadius(player1));
      Image image = Toolkit.getDefaultToolkit().getImage(loader.getResource("asteroids/resources/deathstar.png")).getScaledInstance(size, size, Image.SCALE_DEFAULT);
      visualizations.put(player1, new ShipVisualization(Color.RED, player1, image));
    }
    if (player2 != null) {
      int size = (int) (2 * facade.getShipRadius(player2));
      Image image = Toolkit.getDefaultToolkit().getImage(loader.getResource("asteroids/resources/sphere.png")).getScaledInstance(size, size, Image.SCALE_DEFAULT);
      visualizations.put(player2, new ShipVisualization(Color.GREEN, player2, image));
    }
    addKeyListener(this);
  }

  @Override
  public boolean isFocusable() {
    return true;
  }

  private void drawCenteredString(Graphics2D g2d, String txt, int y) {
    int width = getWidth();
    Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(txt, g2d);
    g2d.drawString(txt, width / 2 - (int) bounds.getCenterX(), y);
  }

  private void drawCenteredString(Graphics2D g2d, String txt) {
    int width = getWidth();
    int height = getHeight();
    Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(txt, g2d);
    g2d.drawString(txt, width / 2 - (int) bounds.getCenterX(), height / 2 - (int) bounds.getCenterY());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.drawImage(background, 0, 0, null);
    g2d.setColor(Color.WHITE);
    for (Ship ship : facade.getShips(world)) {
      if (!visualizations.containsKey(ship)) {
        visualizations.put(ship, new ShipVisualization(Color.BLUE, ship, null));
      }
      visualizations.get(ship).draw(g2d);
    }
    for (Asteroid asteroid : facade.getAsteroids(world)) {
      if (!visualizations.containsKey(asteroid)) {
        visualizations.put(asteroid, new AsteroidVisualization(asteroid));
      }
      visualizations.get(asteroid).draw(g2d);
    }
    for (Bullet bullet : facade.getBullets(world)) {
      if (!visualizations.containsKey(bullet)) {
        Ship ship = facade.getBulletSource(bullet);
        visualizations.put(bullet, new BulletVisualization(visualizations.get(ship).getColor(), bullet));
      }
      visualizations.get(bullet).draw(g2d);
    }
    for (Explosion explosion : explosions) {
      explosion.draw(g2d);
    }
    if (msg != null) {
      g2d.setColor(Color.WHITE);
      g2d.setFont(g2d.getFont().deriveFont(40f));
      drawCenteredString(g2d, msg);
      g2d.setFont(g2d.getFont().deriveFont(20f));
      drawCenteredString(g2d, "Press ESC to continue ...", getHeight() / 3 * 2);
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
    case KeyEvent.VK_ESCAPE:
      timer.stop();
      game.showMenu();
    case THRUSTER_P1:
      facade.setThrusterActive(player1, true);
      break;
    case LEFT_P1:
      player1_angle = Math.PI / 20;
      break;
    case RIGHT_P1:
      player1_angle = -Math.PI / 20;
      break;
    case FIRE_P1:
      player1_fire = true;
      break;
    case THRUSTER_P2:
      if (player2 != null)
        facade.setThrusterActive(player2, true);
      break;
    case LEFT_P2:
      if (player2 != null)
        player2_angle = Math.PI / 20;
      break;
    case RIGHT_P2:
      if (player2 != null)
        player2_angle = -Math.PI / 20;
      break;
    case FIRE_P2:
      if (player2 != null)
        player2_fire = true;
      break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
    case THRUSTER_P1:
      facade.setThrusterActive(player1, false);
      break;
    case LEFT_P1:
      player1_angle = 0;
      break;
    case RIGHT_P1:
      player1_angle = 0;
      break;
    case THRUSTER_P2:
      if (player2 != null)
        facade.setThrusterActive(player2, false);
      break;
    case LEFT_P2:
      if (player2 != null)
        player2_angle = 0;
      break;
    case RIGHT_P2:
      if (player2 != null)
        player2_angle = 0;
      break;
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    long now = System.currentTimeMillis();
    long millisSinceLastEvolve = now - timeAfterLastEvolve;
    timeAfterLastEvolve = now;
    if (player1_angle != 0) {
      facade.turn(player1, player1_angle);
    }
    if (player2 != null && player2_angle != 0) {
      facade.turn(player2, player2_angle);
    }
    if (player1_fire && facade.getShips(world).contains(player1)) {
      player1_fire = false;
      facade.fireBullet(player1);
      game.getSound().play("torpedo");
    }
    if (player2 != null && player2_fire && facade.getShips(world).contains(player2)) {
      player2_fire = false;
      facade.fireBullet(player2);
      game.getSound().play("torpedo");
    }
    facade.evolve(world, millisSinceLastEvolve / 1000., this);
    Iterator<Explosion> iter = explosions.iterator();
    while (iter.hasNext()) {
      boolean done = iter.next().evolve(millisSinceLastEvolve / 1000.);
      if (done)
        iter.remove();
    }
    boolean player1Alive = facade.getShips(world).contains(player1);
    if (player1Alive) {
      if (player2 != null && !facade.getShips(world).contains(player2)) {
        timer.stop();
        msg = "Player 1 wins!";
      } else if (facade.getAsteroids(world).isEmpty() && player2 == null) {
        timer.stop();
        msg = "You win!";
      }
    } else {
      timer.stop();
      if (player2 == null || !facade.getShips(world).contains(player2)) {
        msg = "Asteroids win!";
      } else {
        msg = "Player 2 wins!";
      }
    }
    repaint();
  }

  public void startGame() {
    game.getSound().loop("game-theme");
    timeAfterLastEvolve = System.currentTimeMillis();
    timer.start();
  }

  @Override
  public void boundaryCollision(Object entity, double x, double y) {

  }

  @Override
  public void objectCollision(Object entity1, Object entity2, double x, double y) {
    if ((facade.isBullets(entity1) && !facade.isBullets(entity2)) || (facade.isBullets(entity2) && !facade.isBullets(entity1))) {
      game.getSound().play("explosion");
      explosions.add(new Explosion(x, facade.getWorldHeight(world) - y));
    }
  }
  
  public abstract class Visualization<T> implements Drawable {
    private final Color color;
    private final T object;
    private final Image image;

    public Visualization(Color color, T object, Image image) {
      if (color == null)
        throw new IllegalArgumentException("color null");
      if (object == null)
        throw new IllegalArgumentException("object null");
      this.color = color;
      this.object = object;
      this.image = image;
    }

    public Color getColor() {
      return color;
    }

    public T getObject() {
      return object;
    }

    public Image getImage() {
      return image;
    }
  }
  
  private static Image[] getImages() {
    Image[] images = new Image[1];
    ClassLoader loader = WorldView.class.getClassLoader();
    images[0] = Toolkit.getDefaultToolkit().createImage(loader.getResource("asteroids/resources/asteroid1.png"));
    return images;
  }
  
  private static Image[] asteroidImages = getImages();
  
  public class ShipVisualization extends Visualization<Ship> {

    public ShipVisualization(Color color, Ship ship, Image image) {
      super(color, ship, image);
    }

    @Override
    public void draw(Graphics2D g2d) {
      double radius = facade.getShipRadius(getObject());
      double angle = -facade.getShipDirection(getObject());
      double x = facade.getShipX(getObject());
      double y = facade.getWorldHeight(world) - facade.getShipY(getObject());
      g2d.setColor(getColor());
      if (getImage() == null) {
        g2d.drawOval((int) (x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
      } else {
        AffineTransform T = AffineTransform.getTranslateInstance(radius, radius);
        T.rotate(angle);
        T.translate(-radius, -radius);
        T.preConcatenate(AffineTransform.getTranslateInstance(x - radius, y - radius));
        g2d.drawImage(getImage(), T, null);
      }
      g2d.drawLine((int) x, (int) y, (int) (x + Math.cos(angle) * radius), (int) (y + sin(angle) * radius));
    }
  }
  
  public class AsteroidVisualization extends Visualization<Asteroid> {
   
    public AsteroidVisualization(IFacade<World, Ship, Asteroid, Bullet> facade,  Asteroid asteroid, Image image) {
      super(Color.WHITE, asteroid, image);
    }
    
    public AsteroidVisualization(Asteroid asteroid) {
      this(facade, asteroid, asteroidImages[0]);
    }

    @Override
    public void draw(Graphics2D g2d) {
      World world = facade.getAsteroidWorld(getObject());
      if(world != null) {
        double radius = facade.getAsteroidRadius(getObject());
        double x = facade.getAsteroidX(getObject());
        double y = facade.getWorldHeight(world) - facade.getAsteroidY(getObject());
        if(getImage() == null) {
          g2d.setColor(getColor());
          g2d.drawOval((int) (x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
        } else {
          AffineTransform T = AffineTransform.getScaleInstance(2*radius/getImage().getWidth(null), 2*radius / getImage().getHeight(null));
          T.preConcatenate(AffineTransform.getTranslateInstance(x - radius, y - radius));
          g2d.drawImage(this.getImage(), T, null);
        }
      }
    }
  }
  
  public class BulletVisualization extends Visualization<Bullet> {
    
    public BulletVisualization(Color color,  Bullet bullet) {
      super(color, bullet, null);
    }

    @Override
    public void draw(Graphics2D g2d) {
      double radius = facade.getBulletRadius(getObject());
      double x = facade.getBulletX(getObject());
      double y = facade.getWorldHeight(world) - facade.getBulletY(getObject());
      g2d.setColor(getColor());
      g2d.drawOval((int) (x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
    }
  }
}
