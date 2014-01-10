/**
 * Creates a json array and provides methods to add various
 * values as defined under json rules {@link http://www.json.org}
 * @author Yuvraj Singh Babrah
 * @version 1.0
 * @released 28 November 2013 
 */

package easyJSON;

import java.util.*;

public class easyJSONArray{
	private ArrayList array = null;

	/* instantiate */
	public easyJSONArray(){
		array = new ArrayList();
	}

	/* insert elements in the array */
	/* string as a value */
	public void add(String value){
		value = value.replace("=", "*$equals$*");
		value = "\"" + value + "\"";
		array.add(value);
	}

	/* boolean as a value */
	public void add(boolean value){
		array.add(value);
	}

	/* number as a value */
	public void add(Number value){
		array.add(value);
	}

	/* object as a value */
	public void add(easyJSON value){
		array.add(value);
	}

	/* array as a value */
	public void add(easyJSONArray value){
		array.add(value);
	}
	/* insert elements in array ends here */

	/* return value for an index */
	public Object get(int index){
		if(index < array.size()){
			Object value = array.get(index);
			if(value instanceof String){
				String value_str = (String)value;
				return value_str.substring(1, value_str.length() - 1);
			}
			return value;
		}
		return null;
	}

	/* remove an element mentioned by index */
	public void remove(int index){
		if(index < array.size())
			array.remove(index);
	}

	/* remove an element mentioned by value */
	public void remove(Object value){
		if(array.contains(value))
			array.remove(value);
	}


	/* return array */
	public String toString(){
		return array.toString().replace("*$equals$*", "=");
	}
}