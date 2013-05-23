package com.mod12.cabal.server.core.manager;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.util.RandomUtil;
import com.mod12.cabal.server.core.item.Item;
import com.mod12.cabal.server.core.item.UniqueItem;

/**
 * Knows of all items in game. Is the main interface to interact with for Item information.
 * @author kowalski
 *
 */
public class ItemManager {

	private static ItemManager manager;
	
	private List<Item> items;
	private List<UniqueItem> uniqueItems;
	
	public static ItemManager getItemManager() {
		if (manager == null) manager = new ItemManager();
		return manager;
	}
	
	public void initializeItems(List<Item> items, List<UniqueItem> uniqueItems) {
		if (this.items != null) return; 
		
		this.items = items;
		this.uniqueItems = uniqueItems;
	}
	
	/**
	 * @return new allocated list
	 */
	public List<Item> getItems() {
		return new LinkedList<Item>(items);
	}
	
	/**
	 * @return new allocated list
	 */
	public List<UniqueItem> getUniqueItems() {
		return new LinkedList<UniqueItem>(uniqueItems);
	}

//	public Item getItem(String itemName) {
//		for (Item item : items) {
//			if (item.equals(itemName)) return new Item(item);
//		}
//		return null;
//	}
	
	public String getUniqueItemName(int id) {
		for (UniqueItem item : uniqueItems) {
			if (item.getID() == id) return item.getName();
		}
		return null;
	}
	
//	public UniqueItem getUniqueItem(int id) {
//		for (UniqueItem item : uniqueItems) {
//			if (item.getID() == id) return new UniqueItem(item);
//		}
//		return null;
//	}
	
	public List<Item> getRandomItems(int number) {
		List<Item> list = new LinkedList<Item>();
		
		while (number > 0) {
			Item randomItem = items.get(RandomUtil.nextInt(items.size()));
			list.add(randomItem);
			
			number--;
		}
		
		return list;
	}
}
