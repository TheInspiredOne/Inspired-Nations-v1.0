package com.github.InspiredOne.InspiredNationsServer.ToolBox.Messaging;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


import com.github.InspiredOne.InspiredNationsClient.InspiredNationsClient;
import com.github.InspiredOne.InspiredNationsServer.Remotes.AlertPortal;
import com.github.InspiredOne.InspiredNationsServer.Remotes.PlayerDataPortal;
import com.github.InspiredOne.InspiredNationsServer.SerializableIDs.PlayerID;
import com.github.InspiredOne.InspiredNationsServer.ToolBox.SortTool;

public abstract class Alert implements AlertPortal {

	/**
	 * 
	 */
	public static final long serialVersionUID = 8148726689920578981L;
	protected Calendar calendar;
	protected boolean menuVisible = true;
	private boolean expired = false; // turned to true when expires.
	private int stacksize = 1;
    public transient static final ScheduledExecutorService scheduler = 
		      Executors.newScheduledThreadPool(1);
    public transient ScheduledFuture<?> timerHandle;

	transient public static SortTool<AlertPortal> ageSort = new SortTool<AlertPortal>() {

		@Override
		public String getName() {
			return "Age";
		}


		@Override
		public Comparator<AlertPortal> getComparator() {

			return new Comparator<AlertPortal>() {

				@Override
				public int compare(AlertPortal o1, AlertPortal o2) {
					try {
						return o1.getCalendar().compareTo(o2.getCalendar());
					} catch (RemoteException e) {
						// TODO kill the plugin
						e.printStackTrace();
					}
					return 0;
				}

			};
		};
	};

	public Alert() {
		calendar = Calendar.getInstance();
	}

	@Override
	public Alert getSelf() throws RemoteException{
		return this;
	}
	
	@Override
	public Calendar getCalendar() {
		return this.calendar;
	}

	@Override
	public boolean menuVisible() {
		return menuVisible;
	}

	@Override
	public void setExpired(boolean ex) {
		this.expired = ex;
	}

	public boolean expired() {

		   
			   final Runnable timer = new Runnable() {
				   public void run() { expired = true; }
			   };
			   
			   timerHandle = scheduler.schedule(timer, 5, TimeUnit.SECONDS);
			  
		return expired;
	}

	public abstract String getMessage(PlayerDataPortal receiver)
			throws RemoteException;

	/**
	 * Returns true if the alert should remain after the player switches to a
	 * new menu.
	 * 
	 * @return
	 */
	public abstract boolean menuPersistent();

	@Override
	public String getName() {
		return "Received: " + calendar.getDisplayName(0, 0, Locale.US);
	}

	@Override
	public void setName(String name) {

	}

	@Override
	public String getDisplayName(PlayerID viewer) throws RemoteException {
		String msg = this.getMessage(InspiredNationsClient.server
				.getPlayer(viewer));
		if (this.stacksize == 1) {
			return msg;
		} else {
			return msg.concat(" [" + this.stacksize + "]");
		}

		/*
		 * if(msg.length() <= end) { end = msg.length() - 1; return getName() +
		 * " " + msg.substring(0, end); } else { return getName() + " " +
		 * msg.substring(0, end) + "..."; }
		 */
	}

	public static List<SortTool<AlertPortal>> getComparators() {
		List<SortTool<AlertPortal>> output = new ArrayList<SortTool<AlertPortal>>();
		output.add(Alert.ageSort);
		return output;
	}

	public void incrementStack() {
		this.stacksize++;
		
		this.timerHandle.cancel(true);
		this.expired = false;
		this.calendar = Calendar.getInstance();
	}
}
