package com.amo.demo.wheelview;

/**
 * The simple Array wheel adapter    ¿ªÔ´  http://blog.csdn.net/zhjp4295216/article/details/6963651
 * @param <T> the element type
 */
public class ArrayWheelAdapter<T> implements WheelAdapter {
	
	/** The default items length */
	public static final int DEFAULT_LENGTH = -1;
	
	// items
	private T items[];
	// length
	private int length;

	/**
	 * Constructor
	 * @param items the items
	 * @param length the max items length
	 */
	public ArrayWheelAdapter(T items[], int length) {
		this.items = items;
		this.length = length;
	}
	
	/**
	 * Contructor
	 * @param items the items
	 */
	public ArrayWheelAdapter(T items[]) {
		this(items, DEFAULT_LENGTH);
	}

	public String getItem(int index) {
		if (index >= 0 && index < items.length) {
			return items[index].toString();
		}
		return null;
	}


	public int getItemsCount() {
		return items.length;
	}


	public int getMaximumLength() {
		return length;
	}

}
