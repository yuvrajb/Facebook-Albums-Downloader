/**
 * Creates a json object and provides methods to add various key-value
 * pairs as defined under json rules {@link http://www.json.org}
 * @author Yuvraj Singh Babrah
 * @version 1.0
 * @released 28 November 2013 
 */

package easyJSON;

import java.util.*;

public class easyJSON{
	private Map json = null;

	/* instantiate */
	public easyJSON(){
		json = new TreeMap();
	}

	/* insert key value pair */
	/* string as a value */
	public void add(String key, String value){
		key = "\"" + key + "\"";
 		value = value.replace("=", "*$equals$*");
 		value = "\"" + value + "\"";
 		json.put(key, value);
	}

	/* boolean as a value */
	public void add(String key, boolean value){
		key = "\"" + key + "\"";
		json.put(key, value);
	}	

	/* numeber as a value */
	public void add(String key, Number value){
		key = "\"" + key + "\"";
		json.put(key, value);
	}

	/* object as a value*/
	public void add(String key, easyJSON value){
		key = "\"" + key + "\"";
		json.put(key, value);
	}

	/* array as a value */
	public void add(String key, easyJSONArray value){
		key = "\"" + key + "\"";
		json.put(key, value);
	}
	/* insert key value pair ends here */

	/* remove a key value pair */
	public void remove(String key){
		key = "\"" + key + "\"";
		if(json.containsKey(key))
			json.remove(key);
	}

	/* return value for a valid key */
	public Object get(String key){
		key = "\"" + key + "\"";
		if(json.containsKey(key)){
			Object value = json.get(key);
			if(value instanceof String){
				String value_str = (String)value;
				return value_str.substring(1, value_str.length() - 1);
			}
			return value;
		}
		else
			return null;
	}

	/* return json representation */
	public String toString(){
		return json.toString().replace("=", ":").replace("*$equals$*", "=");
	}
}