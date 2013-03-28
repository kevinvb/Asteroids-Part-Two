package asteroids.model;

public class Ship {
	private double x, y, xVelocity,  yVelocity, radius, angle, direction ;

	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double angle){
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.radius = radius;
		this.angle = angle;
	}

	  public double getX(Ship ship){
		  return ship.x;
	  }

	  /**
	   * Return the y-coordinate of <code>ship</code>.
	   */
	  public double getY(Ship ship){
		  return ship.y;
	  }

	  /**
	   * Return the velocity of <code>ship</code> along the X-axis.
	   */
	  public double getXVelocity(Ship ship){
		  return ship.xVelocity;
	  };

	  /**
	   * Return the velocity of <code>ship</code> along the Y-axis.
	   */
	  public double getYVelocity(Ship ship){
		  return ship.yVelocity;
	  };

	  /**
	   * Return the radius of <code>ship</code>.
	   */
	  public double getRadius(Ship ship){
	  assert ship.radius >=10;
	  return ship.radius;
	  }
	  

	  /**
	   * Return the direction of <code>ship</code> (in radians).
	   */
	  public double getDirection(Ship ship){
		  return ship.angle;
	  }
	  /**
	   * Update <code>ship</code>'s position, assuming it moves <code>dt</code>
	   * seconds at its current velocity.
	   */
	  
	  public double getVelocity(Ship ship){
		  double velocity = Math.sqrt((ship.xVelocity*ship.xVelocity)+(ship.yVelocity*ship.yVelocity));
		  assert velocity <= 300000;
		  return velocity;
	  }
	  
	  public void move(Ship ship, double dt){
	  assert dt>=0;
	  ship.x = ship.x+ (Math.cos(ship.angle)*(ship.getVelocity(ship)*dt));
	  ship.y = ship.y+ (Math.sin(ship.angle)*(ship.getVelocity(ship)*dt));
	  }
	  
	  /**
	   * Update <code>ship</code>'s velocity based on its current velocity, its
	   * direction and the given <code>amount</code>.
	   */
	  public void thrust(Ship ship, double amount){
		  ship.xVelocity = ship.xVelocity + (amount*Math.cos(ship.angle));
		  ship.yVelocity = ship.yVelocity + (amount*Math.sin(ship.angle));
	  }

	  /**
	   * Update the direction of <code>ship</code> by adding <code>angle</code> (in
	   * radians) to its current direction. <code>angle</code> may be negative.
	   */
	  public void turn(Ship ship, double angle){
		  ship.angle+= angle;
	  };

	  /**
	   * Return the distance between <code>ship1</code> and <code>ship2</code>.
	   * 
	   * The absolute value of the result of this method is the minimum distance
	   * either ship should move such that both ships are adjacent. Note that the
	   * result must be negative if the ships overlap. The distance between a ship
	   * and itself is 0.
	   */
	  public double getDistanceBetween(Ship ship1, Ship ship2){
		  double distance = Math.sqrt(((ship2.x-ship1.x)*(ship2.x-ship1.x))+((ship2.y-ship1.y)*(ship2.y-ship1.y)));
		  if (distance < (ship1.radius+ship2.radius))
				  return -distance;
		  else
			  return distance;
	  }
	  
	  /**
	   * Check whether <code>ship1</code> and <code>ship2</code> overlap. A ship
	   * always overlaps with itself.
	   */
	  public boolean overlap(Ship ship1, Ship ship2){
		  double distance = getDistanceBetween(ship1, ship2);
		  if (distance <0)
			  return true;
		  else
			  return false;
	  }
	  /**
	   * Return the number of seconds until the first collision between
	   * <code>ship1</code> and <code>ship2</code>, or Double.POSITIVE_INFINITY if
	   * they never collide. A ship never collides with itself.
	   */
	  public double getTimeToCollision(Ship ship1, Ship ship2){
		  double sigma = ship1.radius+ship2.radius;
		  double DRx = ship2.x-ship1.x;
		  double DRy = ship2.y-ship1.y;
		  double DVx = ship2.xVelocity-ship1.xVelocity;
		  double DVy = ship2.yVelocity-ship1.yVelocity;
		  double DR2 = (DRx*DRx)+(DRy*DRy);
		  double DV2 = (DVx*DVx)+(DVy*DVy);
		  double DVR = (DVx*DRx)+(DVy*DRy);
		  double d = (DVR*DVR)-((DV2)*(DR2-(sigma*sigma)));
		  double DT = -((DVR+Math.sqrt(d))/DV2);
		  if (DVR >=0)
			  return Double.POSITIVE_INFINITY;
		  else if (d<=0)
			  return Double.POSITIVE_INFINITY;
		  else
			  return DT;  
	  }

	  /**
	   * Return the first position where <code>ship1</code> and <code>ship2</code>
	   * collide, or <code>null</code> if they never collide. A ship never collides
	   * with itself.
	   * 
	   * The result of this method is either null or an array of length 2, where the
	   * element at index 0 represents the x-coordinate and the element at index 1
	   * represents the y-coordinate.
	   */
	  public double[] getCollisionPosition(Ship ship1, Ship ship2)
	 {
		  double collisionDistance  = ship1.radius  + ship2.radius;
		  double[] result = new double[2];
		  double DT = getTimeToCollision(ship1,ship2);
		  double x1,x2,y1,y2,x3,y3,dx, angle;
		if ( DT != Double.POSITIVE_INFINITY)
		{
			x1 = ship1.x+ (Math.cos(ship1.angle)*(ship1.getVelocity(ship1)*DT));
			y1 = ship1.y+ (Math.sin(ship1.angle)*(ship1.getVelocity(ship1)*DT));
			x2 = ship2.x+ (Math.cos(ship2.angle)*(ship2.getVelocity(ship2)*DT));
			y2 = ship2.y+ (Math.sin(ship2.angle)*(ship2.getVelocity(ship2)*DT));
			dx = Math.abs(x2-x1);
			angle = Math.acos(dx/collisionDistance);
			x3 =  x1+ (ship1.radius*Math.cos(angle));
			y3 =  y1+ (ship1.radius*Math.sin(angle));
			result[0] = x3;
			result[1] = y3;
			return result;
		}
		
		else
			return null;
	 
	 }
	

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public double getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	
}
