package com.github.InspiredOne.InspiredNations.ToolBox.Messaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.ToolBox.Nameable;
import com.github.InspiredOne.InspiredNations.ToolBox.PlayerID;
import com.github.InspiredOne.InspiredNations.ToolBox.SortTool;

public abstract class Alert implements Nameable, Serializable {

	/**
	 * 
	 */
	public static final long serialVersionUID = 8148726689920578981L;
	public Calendar calendar;
	public boolean menuVisible = true;
	public boolean expired = false; //turned to true when expires.
	private int timerid = -1;
	private int stacksize = 1;
	
	transient public static SortTool<Alert> ageSort = new SortTool<Alert>() {
		
		@Override
		public String getName() {
			return "Age";
		}

		@Override
		public Comparator<Alert> getComparator() {
			
			return new Comparator<Alert>() {

				@Override
				public int compare(Alert o1, Alert o2) {
					return o1.calendar.compareTo(o2.calendar);
				}
				
			};
		};
	};
	
	public Alert() {
		calendar = Calendar.getInstance();
	}

	public boolean expired() {
		BukkitRunnable Timer = new BukkitRunnable() {
			@Override
			public void run() {
				expired = true;
			}
		};
		if(timerid == -1) {
			timerid = Timer.runTaskLater(InspiredNations.plugin, 100).getTaskId();
		}
		return expired;
	}
	
	public abstract String getMessage(PlayerData receiver);
	
	/**
	 * Returns true if the alert should remain after the player switches to a new menu.
	 * @return
	 */
	public abstract boolean  menuPersistent();
	
	@Override
	public String getName() {
		return "Received: " + calendar.getDisplayName(0, 0, Locale.US);
	}

	@Override
	public void setName(String name) {
		
	}

	@Override
	public String getDisplayName(PlayerData viewer) {
		String msg = this.getMessage(viewer);
		if(this.stacksize == 1) {
			return msg;
		}
		else {
			return msg.concat(" [" + this.stacksize + "]");
		}
		
/*		if(msg.length() <= end) {
			end = msg.length() - 1;
			return getName() + " " + msg.substring(0, end);
		}
		else {
			return getName() + " " + msg.substring(0, end) + "...";	
		}*/
	}

	public static List<SortTool<Alert>> getComparators() {
		List<SortTool<Alert>> output = new ArrayList<SortTool<Alert>>();
		output.add(Alert.ageSort);
		return output;
	}
	
	public void incrementStack() {
		this.stacksize++;
		InspiredNations.plugin.getServer().getScheduler().cancelTask(timerid);;
		this.expired = false;
		timerid = -1;
		this.calendar = Calendar.getInstance();
	}
/*	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(this.serialVersionUID).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof PlayerID))
            return false;
        Alert rhs = (Alert) obj;

        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(this.serialVersionUID, rhs.serialVersionUID).
            isEquals();
    }*/
}
