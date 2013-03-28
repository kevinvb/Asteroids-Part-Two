package asteroids;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AsteroidsMenu<World, Ship, Asteroid, Bullet> extends JPanel implements KeyListener {
  
  private String[] menu_options = { "Player vs Asteroids", "Player vs Player", "Exit" };
  private int selectedIndex = 0;
  private final Asteroids<World, Ship, Asteroid, Bullet> game;
  private Image background;
  
  public AsteroidsMenu(Asteroids<World, Ship, Asteroid, Bullet> game) {
    this.game = game;
    addKeyListener(this);
    setBackground(Color.BLACK);
    ClassLoader loader = AsteroidsMenu.class.getClassLoader();
    background = Toolkit.getDefaultToolkit().getImage(loader.getResource("asteroids/resources/menu-background.jpg"));
    background = background.getScaledInstance(game.getWidth(), game.getHeight(), Image.SCALE_DEFAULT);
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
  
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.drawImage(background, 0, 0, game.getWidth(), game.getHeight(), null);
    g2d.setFont(g2d.getFont().deriveFont(150f));
    int titleHeight = g2d.getFontMetrics().getHeight();
    g2d.setColor(Color.WHITE);
    int baseHeight = 250;
    drawCenteredString(g2d, "Asteroids", baseHeight);
    g2d.setFont(g2d.getFont().deriveFont(40f));
    int optionHeight = g2d.getFontMetrics().getHeight();
    for(int i = 0; i < menu_options.length; i++) {
      if(i == selectedIndex) {
        g2d.setColor(Color.RED);
      } else {
        g2d.setColor(Color.WHITE);
      }
      drawCenteredString(g2d, menu_options[i], (int) (baseHeight  + titleHeight + optionHeight * 1.5 * i));
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
  
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(game.getWidth(), game.getHeight());
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
    case KeyEvent.VK_ESCAPE:
      System.exit(0);
    case KeyEvent.VK_UP:
      selectedIndex = selectedIndex == 0 ? menu_options.length - 1 : selectedIndex - 1;
      repaint();
      game.getSound().play("blip");
      break;
    case KeyEvent.VK_DOWN:
      selectedIndex = (selectedIndex + 1) % menu_options.length;
      repaint();
      game.getSound().play("blip");
      break;
    case KeyEvent.VK_ENTER:
      game.getSound().stop("menu-theme");
      switch(selectedIndex) {
      case 0:
        game.startSinglePlayerGame();
        break;
      case 1:
        game.startMultiPlayerGame();
        break;
      case 2:
        System.exit(0);
      }
      break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    
  }
  
  public void reset() { 
    game.getSound().stop("game-theme");
    game.getSound().loop("menu-theme");
    repaint();
  }
}
