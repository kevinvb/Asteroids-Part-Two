package asteroids;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Asteroids<World, Ship, Asteroid, Bullet> extends JFrame {

  private AsteroidsMenu<World, Ship, Asteroid, Bullet> menu;
  private WorldView<World, Ship, Asteroid, Bullet> view;
  private IFacade<World, Ship, Asteroid, Bullet> facade;
  private int width;
  private int height;
  private Sound sound;

  public Asteroids(IFacade<World, Ship, Asteroid, Bullet> facade, int width, int height, boolean undecorated, Sound sound) {
    super("Asteroids");
    this.sound = sound;
    this.width = width;
    this.height = height;
    menu = new AsteroidsMenu<World, Ship, Asteroid, Bullet>(this);
    this.facade = facade;
    setUndecorated(undecorated);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    getContentPane().add(menu);
    pack();
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Sound getSound() {
    return sound;
  }
  
  public IFacade<World, Ship, Asteroid, Bullet> getFacade() {
    return facade;
  }

  public void start() {
    menu.reset();
    sound.start();
    setVisible(true);
    menu.requestFocusInWindow();
  }

  public void startSinglePlayerGame() {
    World world = facade.createWorld(width, height);
    Ship player = facade.createShip(width / 2., height / 2., 0, 0, 40, 0, 5E15);
    facade.addShip(world, player);
    Asteroid asteroid1 = facade.createAsteroid(100, 100, 25, 50, 60);
    facade.addAsteroid(world, asteroid1);
    Asteroid asteroid2 = facade.createAsteroid(600, 100, -30, -40, 80);
    facade.addAsteroid(world, asteroid2);
    view = new WorldView<World, Ship, Asteroid, Bullet>(this, world, player, null);
    if (!isUndecorated())
      view.setPreferredSize(new Dimension(width, height));
    getContentPane().remove(menu);
    getContentPane().add(view);
    revalidate();
    repaint();
    view.requestFocusInWindow();
    view.startGame();
  }

  public void startMultiPlayerGame() {
    World world = facade.createWorld(width, height);
    Ship player1 = facade.createShip(width / 5 * 4, height / 2., 0, 0, 40, Math.PI, 5E15);
    facade.addShip(world, player1);
    Ship player2 = facade.createShip(width / 5, height / 2., 0, 0, 40, 0, 5E15);
    facade.addShip(world, player2);
    Asteroid asteroid1 = facade.createAsteroid(width / 2, height / 2, 25, 50, 75);
    facade.addAsteroid(world, asteroid1);
    Asteroid asteroid2 = facade.createAsteroid(600, 100, -30, -40, 40);
    facade.addAsteroid(world, asteroid2);
    Asteroid asteroid3 = facade.createAsteroid(990, 550, -20, -3, 25);
    facade.addAsteroid(world, asteroid3);
    Asteroid asteroid4 = facade.createAsteroid(40, height - 100, 10, -8, 15);
    facade.addAsteroid(world, asteroid4);
    view = new WorldView<World, Ship, Asteroid, Bullet>(this, world, player1, player2);
    if (!isUndecorated())
      view.setPreferredSize(new Dimension(width, height));
    getContentPane().remove(menu);
    getContentPane().add(view);
    revalidate();
    repaint();
    view.requestFocusInWindow();
    view.startGame();
  }

  public void showMenu() {
    if (view != null) {
      getContentPane().remove(view);
      view = null;
    }
    menu.reset();
    getContentPane().add(menu);
    revalidate();
    repaint();
    menu.requestFocusInWindow();
    pack();
    menu.repaint();
  }

  public static void main(final String[] args) {
    boolean tryFullscreen = true;
    boolean enableSound = true;
    for(String arg : args) {
      if(arg.equals("-window")) {
        tryFullscreen = false;
      } else if(arg.equals("-nosound")) {
        enableSound = false;
      } else {
        System.out.println("unknown option: " + arg);
        return;
      }
    }
    if (args.length > 0 && args[0].equals("-window")) {
      tryFullscreen = false;
    }
    if (GraphicsEnvironment.isHeadless()) {
      System.out.println("no screen found");
      return;
    }
    // <begin>
    IFacade<asteroids.model.World, asteroids.model.Ship, asteroids.model.Asteroid, asteroids.model.Bullet> facade = new asteroids.model.Facade();
    // <end>
    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice screen = env.getDefaultScreenDevice();
    Asteroids<asteroids.model.World, asteroids.model.Ship, asteroids.model.Asteroid, asteroids.model.Bullet> asteroids;
    Sound sound = enableSound ? new FileSoundManager("asteroids/resources/sounds.txt") : new NullSound();
    if (tryFullscreen && screen.isFullScreenSupported()) {
      Rectangle dimensions = screen.getDefaultConfiguration().getBounds();
      asteroids = new Asteroids<asteroids.model.World, asteroids.model.Ship, asteroids.model.Asteroid, asteroids.model.Bullet>(facade, dimensions.width, dimensions.height, true, sound);
      screen.setFullScreenWindow(asteroids);
    } else {
      asteroids = new Asteroids<asteroids.model.World, asteroids.model.Ship, asteroids.model.Asteroid, asteroids.model.Bullet>(facade, 1024, 768, false, sound);
    }
    asteroids.start();
  }
}
