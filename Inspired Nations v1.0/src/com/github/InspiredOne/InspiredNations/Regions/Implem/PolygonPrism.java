package com.github.InspiredOne.InspiredNations.Regions.Implem;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.Vector;

import com.github.InspiredOne.InspiredNations.Debug;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.NotSimplePolygonException;
import com.github.InspiredOne.InspiredNations.Exceptions.PointsInDifferentWorldException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.CummulativeRegion;
import com.github.InspiredOne.InspiredNations.Regions.NonCummulativeRegion;
import com.github.InspiredOne.InspiredNations.Regions.Region;
import com.github.InspiredOne.InspiredNations.ToolBox.Point3D;
import com.github.InspiredOne.InspiredNations.ToolBox.WorldID;

public class PolygonPrism extends NonCummulativeRegion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6427556511666509346L;
	private static final String typeName = "Polygon Prism";
	private static final String description = "";
	private int ymin;
	private int ymax;
	private WorldID world;
	private Polygon polygon = new Polygon();
	
	public PolygonPrism(Point3D[] points) throws NotSimplePolygonException, PointsInDifferentWorldException{
		polygon.reset();
		ymin = points[0].y;
		ymax = ymin;
		this.world = points[0].world;
		for (int i = 0; i < points.length; i++) {
			if(!this.world.equals(points[i].world)) {
				throw new PointsInDifferentWorldException();
			}
			polygon.addPoint(points[i].x, points[i].z);
			if(points[i].y > ymax) {
				ymax = points[i].y;
			}
			else if(points[i].y < ymin) {
				ymin = points[i].y;
			}
		}
		if(!this.isSimple()) {
			throw new NotSimplePolygonException();
		}
	}
	
	@Override
	public int volume() {
		return this.area()*(ymax - ymin + 1);
	}
	
	public int area() {
    	double sum = 0.0;
    	for (int i = 0; i < polygon.npoints; i++) {
    		if (!((i + 1) < polygon.npoints)) {
    			sum = sum + ((polygon.xpoints[i] * polygon.ypoints[0]) - (polygon.ypoints[i] * polygon.xpoints[0]));
    		}
    		else {
    			sum = sum + ((polygon.xpoints[i] * polygon.ypoints[i + 1]) - (polygon.ypoints[i] * polygon.xpoints[i + 1]));
    		}
    	}
    	return (int) Math.abs(.5 * sum);
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Menu getClaimMenu(PlayerData PDI, Menu previous, InspiredGov gov) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * A method to determine if a polygon is simple. It checks to make sure that
	 * the sides do not cross.
	 * @return	<code>true</code> if the polygon is simple.
	 */
	public boolean isSimple() {
		Polygon poly = this.polygon;
		Vector<Line2D> lines = new Vector<Line2D>();
		Line2D line;
		Line2D line2;
		for (int i = 0; i < poly.npoints; i++) {
			if (!((i+1)<poly.npoints)) {
				line = new Line2D.Double(new Point(poly.xpoints[i], poly.ypoints[i]), new Point(poly.xpoints[0], poly.ypoints[0]));
			}
			else {
				line = new Line2D.Double(new Point(poly.xpoints[i], poly.ypoints[i]), new Point(poly.xpoints[i+1], poly.ypoints[i+1]));
			}
			lines.add(line);
		}
		for (int i = 0; i < lines.size(); i++) {
			line = (Line2D) lines.get(i).clone();
			for (int j = 0; j < lines.size(); j++) {
				line2 = (Line2D) lines.get(j).clone();
				if (i != j && i != (j+1) && i != (j-1) && !(i == 0 && j == lines.size() - 1) && !(i == lines.size() - 1 && j == 0) && line.intersectsLine(line2)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Determines if a location is inside of the polygon
	 */
	public boolean contains(Point3D tile) {
		if (tile.world.equals(this.world)) {
			if (polygon.contains(tile.x, tile.z)) {// || polygon.contains(tile.getBlockX() + .5, tile.getBlockZ() + .5) || polygon.contains(tile.getBlockX() - .5, tile.getBlockZ() + .5) || polygon.contains(tile.getBlockX() + .5, tile.getBlockZ() - .5)) {
				if (tile.y <= ymax && tile.y >= ymin) {
					return true;
				}
				else return false;
			}
			else return false;
		}
		else return false;
	}

	@Override
	public Cuboid getBoundingCuboid() {
		Rectangle rect = this.polygon.getBounds();
		//TODO have to test this to make sure rect.x is actually x
		Point3D one = new Point3D(rect.x, this.ymin, rect.y, this.world);
		Point3D two = new Point3D(rect.x + rect.width, this.ymax, rect.y + rect.height, this.world);
		return new Cuboid(one, two);
	}

	@Override
	public WorldID getWorld() {
		return world;
	}

	@Override
	public boolean IsIn(Region region) {
		Debug.print("Inside polygonPrism(Region)");
		Rectangle rect = this.polygon.getBounds();
		for(int x = rect.x; x >= rect.x - rect.width; x--) {
			for(int z = rect.y; z >= rect.y - rect.height; z--) {
				for(int y = this.ymax; y >= this.ymin; y--) {
					Point3D test = new Point3D(x,y,z,this.world);
					if(this.contains(test) && !region.contains(test)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean isIn(NonCummulativeRegion region) {
		Debug.print("Inside PolygonPrism(NonCummulativeRegion)");
		return IsIn((Region) region);
	}
	
	@Override
	public boolean IsIn(CummulativeRegion region) {
		Debug.print("Inside PolygonPrism(CummulativeRegion)");
		return IsIn((Region) region);
	}

	@Override
	public boolean Intersects(Region region) {
		Rectangle rect = this.polygon.getBounds();
		for(int x = rect.x; x >= rect.x - rect.width; x--) {
			for(int z = rect.y; z >= rect.y - rect.height; z--) {
				for(int y = this.ymax; y >= this.ymin; y--) {
					Point3D test = new Point3D(x,y,z,this.world);
					if(this.contains(test) && region.contains(test)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	protected boolean intersects(NonCummulativeRegion region) {
		return Intersects((Region) region);
	}

	@Override
	public boolean Intersects(CummulativeRegion region) {
		return Intersects((Region) region);
	}

	@Override
	protected boolean instantiated() {
		return !(this.world == null);
	}
}
