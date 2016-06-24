package com.neu.wifilocalization.model;

import java.util.ArrayList;

public class MapObjectEntityContainer 
{
	private ArrayList<MapObjectEntity> container;
	
	public MapObjectEntityContainer()
	{
		container = new ArrayList<MapObjectEntity>();
	}

	
	public void addObject(MapObjectEntity object) 
	{
		container.add(object);
	}
	
	
	public void removeObject(MapObjectEntity object)
	{
		container.remove(object);
	}
	
	
	public MapObjectEntity getObject(int index)
	{
		return container.get(index);
	}
	
	
	public MapObjectEntity getObjectById(int id)
	{
		for (MapObjectEntity model:container) {
			if (model.getId() == id) {
				return model;
			}
		}
		return null;
	}
	
	
	public int size()
	{
		return container.size();
	}

}
