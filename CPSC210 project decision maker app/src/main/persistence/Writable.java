package persistence;

import org.json.JSONObject;

/**
 * A Writable Interface forces its sub classes to have a method return it's data to JSONObject
 */
public interface Writable {

    JSONObject toJson();
}
