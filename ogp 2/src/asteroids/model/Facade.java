package asteroids.model;

import java.util.Random;
import java.util.Set;

import asteroids.CollisionListener;

public class Facade implements asteroids.IFacade {
	
	public Facade()
	{
		
	}

	@Override
	public Object createWorld(double width, double height) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getWorldWidth(Object world) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWorldHeight(Object world) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set getShips(Object world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getAsteroids(Object world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getBullets(Object world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addShip(Object world, Object ship) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAsteroid(Object world, Object asteroid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeShip(Object world, Object ship) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAsteroid(Object world, Object asteroid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void evolve(Object world, double dt,
			CollisionListener collisionListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object createShip(double x, double y, double xVelocity,
			double yVelocity, double radius, double direction, double mass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isShip(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getShipX(Object ship) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getShipY(Object ship) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getShipXVelocity(Object ship) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getShipYVelocity(Object ship) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getShipRadius(Object ship) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getShipDirection(Object ship) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getShipMass(Object ship) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getShipWorld(Object ship) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isShipThrusterActive(Object ship) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setThrusterActive(Object ship, boolean active) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turn(Object ship, double angle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireBullet(Object ship) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object createAsteroid(double x, double y, double xVelocity,
			double yVelocity, double radius) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createAsteroid(double x, double y, double xVelocity,
			double yVelocity, double radius, Random random) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAsteroid(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getAsteroidX(Object asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAsteroidY(Object asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAsteroidXVelocity(Object asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAsteroidYVelocity(Object asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAsteroidRadius(Object asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAsteroidMass(Object asteroid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getAsteroidWorld(Object asteroid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBullets(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getBulletX(Object bullet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBulletY(Object bullet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBulletXVelocity(Object bullet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBulletYVelocity(Object bullet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBulletRadius(Object bullet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBulletMass(Object bullet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getBulletWorld(Object bullet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getBulletSource(Object bullet) {
		// TODO Auto-generated method stub
		return null;
	}

}
