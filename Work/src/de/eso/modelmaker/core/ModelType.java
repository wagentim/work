package de.eso.modelmaker.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all model types
 * 
 * @author bihu8398
 *
 */
public class ModelType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1258048788406282401L;
	
	private List<String> types;

	public ModelType(){
		types = new ArrayList<String>();
	}
	
	public void setTypes(List<String> types) {
		this.types = types;
	}

	public List<String> getTypes() {
		return types;
	}
	
	public void addType(String type){
		if(types != null && types.contains(type)){
			types.add(type);
		}
	}
}
