package asteroids;

import java.util.Random;
import java.util.Set;

/**
 * Implement this interface to connect your code to the user interface.
 * 
 * <ul>
 * <li>Connect your classes to the user interface by creating a class named
 * <code>Facade</code> that implements <code>IFacade</code>. The header of the
 * class <code>Facade</code> should look as follows:
 * 
 * <p>
 * <code>class Facade implements IFacade&ltWorldImpl, ShipImpl, AsteroidImpl, BulletImpl&gt { ... }</code>
 * </p>
 * 
 * The code snippet shown above assumes that your classes representing worlds,
 * ships, asteroids and bullets are respectively named <code>WorldImpl</code>,
 * <code>ShipImpl</code>, <code>AsteroidImpl</code> and <code>BulletImpl</code>.
 * Consult the <a href=
 * "http://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html">Java
 * tutorial</a> for more information on interfaces.</li>
 * <li>Modify the code between <code>&ltbegin&gt</code> and
 * <code>&ltend&gt</code> in Asteroids.java: instantiate the generic arguments
 * with your own classes and replace <code>new asteroids.model.Facade()</code>
 * with <code>new yourpackage.Facade()</code>.</li>
 * <li>Because IFacade is generic, it is no longer necessary to perform casts in
 * <code>Facade.java</code>.</li>
 * <li>Methods in this interface are allowed to throw only
 * <code>ModelException</code>. No other exception types are allowed. This
 * exception can be thrown only if calling a method of your <code>Ship</code>
 * class with the given parameters would violate a precondition or if the method
 * of your <code>Ship</code> class throws an exception (if so, wrap the
 * exception in a <code>ModelException</code>). ModelException should not be
 * used anywhere outside of your Facade implementation.</li>
 * <li>Your classes for representing worlds, ships, asteroids and bullets should
 * follow the rules described in the assignment. The documentation given below
 * for each method applies only to the class implementing IFacade.</li>
 * <li>Do not modify the signatures of the methods defined in this interface.
 * You can however add additional methods, as long as these additional methods
 * do not overload the existing ones. Each additional method must be implemented
 * in your class <code>Facade</code>.</li>
 * <ul>
 */
public interface IFacade<World, Ship, Asteroid, Bullet> {

  /**
   * Create a new world with the given <code>width</code> and
   * <code>height</code>.
   */
  public World createWorld(double width, double height);

  /**
   * Return the width of <code>world</code>.
   */
  public double getWorldWidth(World world);

  /**
   * Return the height of <code>world</code>.
   */
  public double getWorldHeight(World world);

  /**
   * Return all ships located within <code>world</code>.
   */
  public Set<Ship> getShips(World world);

  /**
   * Return all asteroids located in <code>world</code>.
   */
  public Set<Asteroid> getAsteroids(World world);

  /**
   * Return all bullets located in <code>world</code>.
   */
  public Set<Bullet> getBullets(World world);

  /**
   * Add <code>ship</code> to <code>world</code>.
   */
  public void addShip(World world, Ship ship);

  /**
   * Add <code>asteroid</code> to <code>world</code>.
   */
  public void addAsteroid(World world, Asteroid asteroid);

  /**
   * Remove <code>ship</code> from <code>world</code>.
   */
  public void removeShip(World world, Ship ship);

  /**
   * Remove <code>asteroid</code> from <code>world</code>.
   */
  public void removeAsteroid(World world, Asteroid asteroid);

  /**
   * Advance <code>world</code> by <code>dt<code> seconds. 
   * 
   * To enable explosions within the UI, notify <code>collisionListener</code>
   * whenever an entity collides with a boundary or another entity during this
   * method. <code>collisionListener</code> may be null. If
   * <code>collisionListener</code> is <code>null</code>, do not call its notify
   * methods.
   */
  public void evolve(World world, double dt, CollisionListener collisionListener);

  /**
   * Create a new non-null ship with the given position, velocity, radius,
   * direction and mass.
   * 
   * The thruster of the new ship is initially inactive. The ship is not located
   * in a world.
   */
  public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double direction, double mass);

  /**
   * Check whether <code>o</code> is a ship.
   * 
   * You can use the <code>instanceof</code> operator to implement this method.
   */
  public boolean isShip(Object o);

  /**
   * Return the x-coordinate of <code>ship</code>.
   */
  public double getShipX(Ship ship);

  /**
   * Return the y-coordinate of <code>ship</code>.
   */
  public double getShipY(Ship ship);

  /**
   * Return the velocity of <code>ship</code> along the X-axis.
   */
  public double getShipXVelocity(Ship ship);

  /**
   * Return the velocity of <code>ship</code> along the Y-axis.
   */
  public double getShipYVelocity(Ship ship);

  /**
   * Return the radius of <code>ship</code>.
   */
  public double getShipRadius(Ship ship);

  /**
   * Return the direction of <code>ship</code> (in radians).
   */
  public double getShipDirection(Ship ship);

  /**
   * Return the mass of <code>ship</code>.
   */
  public double getShipMass(Ship ship);

  /**
   * Return the world of <code>ship</code>.
   */
  public World getShipWorld(Ship ship);

  /**
   * Return whether <code>ship</code>'s thruster is active.
   */
  public boolean isShipThrusterActive(Ship ship);

  /**
   * Enables or disables <code>ship</code>'s thruster depending on the value of
   * the parameter <code>active</code>.
   */
  public void setThrusterActive(Ship ship, boolean active);

  /**
   * Update the direction of <code>ship</code> by adding <code>angle</code> (in
   * radians) to its current direction. <code>angle</code> may be negative.
   */
  public void turn(Ship ship, double angle);

  /**
   * <code>ship</code> fires a bullet.
   */
  public void fireBullet(Ship ship);

  /**
   * Create a new non-null asteroid with the given position, velocity and
   * radius.
   * 
   * The asteroid is not located in a world.
   */
  public Asteroid createAsteroid(double x, double y, double xVelocity, double yVelocity, double radius);

  /**
   * Create a new non-null asteroid with the given position, velocity and
   * radius.
   * 
   * The asteroid is not located in a world.
   * 
   * Use numbers generated by <code>random</code> to determine the direction of
   * the children (if any) when this asteroid dies.
   */
  public Asteroid createAsteroid(double x, double y, double xVelocity, double yVelocity, double radius, Random random);

  /**
   * Check whether <code>o</code> is an asteroid.
   * 
   * You can use the <code>instanceof</code> operator to implement this method.
   */
  public boolean isAsteroid(Object o);

  /**
   * Return the x-coordinate of <code>asteroid</code>.
   */
  public double getAsteroidX(Asteroid asteroid);

  /**
   * Return the y-coordinate of <code>asteroid</code>.
   */
  public double getAsteroidY(Asteroid asteroid);

  /**
   * Return the velocity of <code>asteroid</code> along the X-axis.
   */
  public double getAsteroidXVelocity(Asteroid asteroid);

  /**
   * Return the velocity of <code>asteroid</code> along the Y-axis.
   */
  public double getAsteroidYVelocity(Asteroid asteroid);

  /**
   * Return the radius of <code>asteroid</code>.
   */
  public double getAsteroidRadius(Asteroid asteroid);

  /**
   * Return the mass of <code>asteroid</code>.
   */
  public double getAsteroidMass(Asteroid asteroid);

  /**
   * Return the world of <code>asteroid</code>.
   */
  public World getAsteroidWorld(Asteroid asteroid);

  /**
   * Check whether <code>o</code> is a bullet.
   * 
   * You can use the <code>instanceof</code> operator to implement this method.
   */
  public boolean isBullets(Object o);

  /**
   * Return the x-coordinate of <code>bullet</code>.
   */
  public double getBulletX(Bullet bullet);

  /**
   * Return the y-coordinate of <code>bullet</code>.
   */
  public double getBulletY(Bullet bullet);

  /**
   * Return the velocity of <code>bullet</code> along the X-axis.
   */
  public double getBulletXVelocity(Bullet bullet);

  /**
   * Return the velocity of <code>bullet</code> along the Y-axis.
   */
  public double getBulletYVelocity(Bullet bullet);

  /**
   * Return the radius of <code>bullet</code>.
   */
  public double getBulletRadius(Bullet bullet);

  /**
   * Return the mass of <code>bullet</code>.
   */
  public double getBulletMass(Bullet bullet);

  /**
   * Return the world of <code>bullet</code>.
   */
  public World getBulletWorld(Bullet bullet);

  /**
   * Return the source of <code>bullet</code>.
   */
  public Ship getBulletSource(Bullet bullet);
}
