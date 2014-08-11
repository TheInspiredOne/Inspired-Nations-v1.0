package com.github.InspiredOne.InspiredNationsServer.Regions.Implem;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;

import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.Exceptions.NotSimplePolygonException;
import com.github.InspiredOne.InspiredNations.Exceptions.PointsInDifferentWorldException;
import com.github.InspiredOne.InspiredNations.Governments.InspiredGov;
import com.github.InspiredOne.InspiredNations.Hud.Menu;
import com.github.InspiredOne.InspiredNations.Hud.Implem.ClaimAndUnclaimLand.ClaimPolygonPrism;
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
	private static final String description = "A prism volume of any shape.";
	private int ymin;
	private int ymax;
	private WorldID world;
	private Polygon polygon = new Polygon();
	private Polygon polygonOrig = new Polygon();
	
	public PolygonPrism() {
		
	}
	
	public PolygonPrism(PolygonPrism prism) {
		this.ymax = prism.ymax;
		this.ymin = prism.ymin;
		this.world = prism.world;
		this.polygon = prism.polygon;
		this.polygonOrig = new Polygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
	}
	
	public PolygonPrism(Point3D[] points) throws NotSimplePolygonException, PointsInDifferentWorldException{
		polygon.reset();
		ymin = points[0].y;
		ymax = ymin;
		this.world = points[0].world;
		for (int i = 0; i < points.length; i++) {
			if(!this.world.equals(points[i].world)) {
				throw new PointsInDifferentWorldException();
			}
			this.addVertex(points[i]);
		}
		polygonOrig = new Polygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
		if(!this.isSimple()) {
			throw new NotSimplePolygonException();
		}
		this.reconcilePoints();
	}
	
	public int getMaxHieght() {
		return ymax;
	}
	public int getMinHieght() {
		return ymin;
	}
	ArrayList<Arrow> arrows = new ArrayList<Arrow>();
//	ArrayList<EnderSignal> eyes = new ArrayList<EnderSignal>();
	
	/**
	 * Adjusts the points so that the original selected blocks are actually within the polygon.
	 */
	public void reconcilePoints() {
		for(Arrow arrow:arrows) {
			arrow.remove();
		}
//		for(EnderSignal eye:eyes) {
//			eye.remove();
//		}
		Polygon finalized = new Polygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
		for(int iter = 0; iter < polygon.npoints; iter++) {
			Point3D point = new Point3D(polygonOrig.xpoints[iter], ymax, polygonOrig.ypoints[iter], world);
			Point3D pointtemp = point.clone();
			boolean notInside = !this.contains(point);
			int rotate = 1;
			int Area = 0; 
			while(rotate <= 4) {
				pointtemp = rotateBlock(point, rotate);
				polygon.xpoints[iter] = pointtemp.x;
				polygon.ypoints[iter] = pointtemp.z;
				polygon.invalidate();
				notInside = !this.contains(point);
				if(!notInside) {
					int areatemp = this.area();
					if(areatemp >= Area) {
						Area = areatemp;
						finalized.xpoints[iter] = pointtemp.x;
						finalized.ypoints[iter] = pointtemp.z;
						finalized.invalidate();
					}
				}
				rotate++;
			}
		}
		//TODO ask eddie if these arrows should appear when selecting the region.
		for(int point = 0; point < finalized.npoints; point++) {
			Point3D arrowspawn = new Point3D(finalized.xpoints[point], ymin, finalized.ypoints[point], this.world);
			Point3D eyespawn = arrowspawn.clone();
			eyespawn.y = this.ymax;
			Arrow arrow = this.world.getWorld().spawn(arrowspawn.getLocation(), Arrow.class);
//			EnderSignal eye = this.world.getWorld().spawn(eyespawn.getLocation(), EnderSignal.class);
//			eye.setVelocity(new Vector());
//			eye.setFireTicks(400);
			arrow.setFireTicks(400);
			arrows.add(arrow);
//			eyes.add(eye);
		}
		this.polygon = finalized;
	}
	public Point3D rotateBlock(Point3D pointInit, int rotates) {
		Point3D point = pointInit.clone();
		rotates = (rotates % 4);
		int counter = 0;
		for(int x = 0; x <= 1; x++) {
			for(int z = 0; z <= 1; z++) {
				if(rotates == counter) {
					point.x = point.x + x;
					point.z = point.z + z;
				}
				counter++;
				
			}
		}
		return point;
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
	
	public void addVertex(Point3D point) throws PointsInDifferentWorldException {
		if(!this.instantiated()) {
			this.world = point.world;
			this.ymax = point.y;
			this.ymin = point.y;
		}
		else if(!point.world.equals(this.world)) {
			throw new PointsInDifferentWorldException();
		}
		if(point.y > ymax ) {
			ymax = point.y;
		}
		if(point.y < ymin) {
			ymin =point.y;
		}
		this.polygon.addPoint(point.x, point.z);
		this.polygonOrig.addPoint(point.x, point.z);
		this.reconcilePoints();
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
		return new ClaimPolygonPrism(PDI, previous, gov);
	}

	/**
	 * A method to determine if a polygon is simple. It checks to make sure that
	 * the sides do not cross.
	 * @return	<code>true</code> if the polygon is simple.
	 */
	public boolean isSimple() {
		Polygon poly = this.polygon;
		java.util.Vector<Line2D> lines = new java.util.Vector<Line2D>();
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
	//
	@Override
	public boolean contains(Point3D tile) {
		if (tile.world.equals(this.world)) {
			if (polygon.contains(tile.x + .49, tile.z + .49) 
					&& polygon.contains(tile.x + .51, tile.z + .51)
					&& polygon.contains(tile.x + .51, tile.z + .49)
					&& polygon.contains(tile.x + .49, tile.z + .51)) {// || polygon.contains(tile.getBlockX() + .5, tile.getBlockZ() + .5) || polygon.contains(tile.getBlockX() - .5, tile.getBlockZ() + .5) || polygon.contains(tile.getBlockX() + .5, tile.getBlockZ() - .5)) {
				if (tile.y <= ymax && tile.y >= ymin) {
					//tile.getLocation().getBlock().setType(Material.DIAMOND_BLOCK);
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

		Point3D one = new Point3D(rect.x + rect.width, this.ymin, rect.y + rect.height, this.world);
		Point3D two = new Point3D(rect.x, this.ymax, rect.y, this.world);
		try {
			return new Cuboid(one, two);
		} catch (PointsInDifferentWorldException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public WorldID getWorld() {
		return world;
	}

	@Override
	public boolean IsIn(Region region) {
		Rectangle rect = this.polygon.getBounds();
		for(int x = rect.x; x <= rect.x + rect.width; x++) {
			for(int z = rect.y; z <= rect.y + rect.height; z++) {
				for(int y = this.ymax; y >= this.ymin; y--) {
					Point3D test = new Point3D(x,y,z,this.world);
					//test.getLocation().getBlock().setType(Material.GOLD_BLOCK);
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
		return IsIn((Region) region);
	}
	
	@Override
	public boolean IsIn(CummulativeRegion<?> region) {
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
	public boolean Intersects(CummulativeRegion<?> region) {
		return Intersects((Region) region);
	}

	@Override
	protected boolean instantiated() {
		return !(this.world == null);
	}
	
	@Override
	public Object clone() {
		return new PolygonPrism(this);
	}

	@Override
	public Location getCharacteristicPoint() {
		double yavg = (this.getMaxHieght()+this.getMinHieght())/2.0;
		double zsum = 0;
		double xsum = 0;
		for(int i = 0; i<this.polygon.npoints; i++) {
			zsum = zsum + this.polygon.ypoints[i];
			xsum = xsum + this.polygon.xpoints[i];
		}
		double xavg = xsum/this.polygon.npoints;
		double zavg = zsum/this.polygon.npoints;
		return new Location(this.world.getWorld(), xavg, yavg, zavg);
	}
}
