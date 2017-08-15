package GameUtility;

public class Vector
{
	double x, y;
	public Vector (double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public void mult(double multiplyer) {
		this.x *= multiplyer;
		this.y *= multiplyer;
	}
	public void div(double divider) {
		this.x /= divider;
		this.y /= divider;
	}
	public void addVector(Vector v) {
		this.x += v.x;
		this.y += v.y;
	}
	public void subVector(Vector v) {
		this.x -= v.x;
		this.y -= v.y;
	}
	public void normalize() {
		this.div(this.length());
	}
	public double length() {
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	double tempx, tempy;
	public void rotate(int degree) {
		tempx = this.x;
		tempy = this.y;
		this.x = Math.cos(Math.toRadians(degree)) * tempx - Math.sin(Math.toRadians(degree)) * tempy;
		this.y = Math.sin(Math.toRadians(degree)) * tempx + Math.cos(Math.toRadians(degree)) * tempy;
	}
	private double degree = 2;
	public double getDegree() {
		degree = Math.toDegrees(Math.atan2(this.x, this.y));
		if(degree < 0) {
			degree += 360;
		}
		return degree;
	}
	public void limit(double limiter) {
		
		if(length() >= limiter){
			this.normalize();
			this.mult(limiter);			
		}
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setX(double i) {
		this.x = i;
	}
	
	public void setY(double i) {
		this.y = i;
	}
	
}
