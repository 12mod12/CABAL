package com.mod12.cabal.server.core.person.agent;

import com.mod12.cabal.server.core.item.Item;

/**
 * A class containing the items and attributes representing a Person's equipment. The equipment is the collection of all items currently
 * affecting the person's skills and capacity.
 * @author kowalski / hamilton
 *
 */
public class Equipment {

	private Item[] slots = new Item[7];
	
	public Equipment() {				
		for (int i = 0; i < slots.length; i ++) {
			slots[i] = null;
		}
	}
	
	public Equipment(Item[] items) {
		for (int i = 0; i < slots.length; i ++) {
			if (items[i] != null) {
				slots[i] = items[i];
			}
		}
	}
	
	public void equipItem(ItemArea area, Item item) {
		slots[area.getIndex()] = item;
	}
	
	public Item getItem(ItemArea area) {
		return slots[area.getIndex()];
	}

	public Item findItem(String itemName) {
		for (int i = 0; i < slots.length; i ++) {
			if (slots[i] != null && slots[i].getName().equals(itemName)) {
				return slots[i];
			}
		}
		return null;
	}

	public Item removeItem(String itemName) {
		for (int i = 0; i < slots.length; i ++) {
			if (slots[i] != null && slots[i].getName().equals(itemName)) {
				Item item = slots[i];
				slots[i] = null;
				return item;
			}
		}
		return null;
	}

	public boolean hasItem(String name) {
		return findItem(name) != null;
	}
	
}
