
public class Vector
{
	double x, y;
	public Vector (double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	void mult(double multiplyer) {
		this.x *= multiplyer;
		this.y *= multiplyer;
	}
	void div(double divider) {
		this.x /= divider;
		this.y /= divider;
	}
	void addVector(Vector v) {
		this.x += v.x;
		this.y += v.y;
	}
	void subVector(Vector v) {
		this.x -= v.x;
		this.y -= v.y;
	}
	void normalize() {
		this.div(this.length());
	}
	double length() {
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	double tempx, tempy;
	void rotate(int degree) {
		tempx = this.x;
		tempy = this.y;
		this.x = Math.cos(degree) * tempx - Math.sin(degree) * tempy;
		this.y = Math.sin(degree) * tempx + Math.cos(degree) * tempy;
	}
	double degree;
	double getDegree() {
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
}
