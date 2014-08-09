package com.github.InspiredOne.InspiredNationsServer.ToolBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.InspiredOne.InspiredNationsServer.Governments.GovFactory;
import com.github.InspiredOne.InspiredNationsServer.Governments.InspiredGov;

/**
 * A multimap implementation that is specific to my government classes. Uses 
 * InspiredGov Class objects as keys and InspiredGovs as values. 
 * @author Jedidiah Phillips
 *
 */
public class MultiGovMap extends MultiMap<Class<? extends InspiredGov>, InspiredGov> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3307693156378400946L;

	public MultiGovMap() {
	}
	
	@Override
	public List<InspiredGov> get(Object key) {
		@SuppressWarnings("unchecked")
		InspiredGov gov = GovFactory.getGovInstance((Class<? extends InspiredGov>) key);
		List<InspiredGov> output = new ArrayList<InspiredGov>();

		for(Class<? extends InspiredGov> govtype:gov.getSelfGovs()) {
			output.addAll(set.get(govtype));
		}
		return output;
	}
	
}
