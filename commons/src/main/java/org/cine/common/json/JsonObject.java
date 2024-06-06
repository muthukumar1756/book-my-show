package org.cine.common.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * <p>
 * Handles the methods to work with json object.
 * </p>
 *
 * @author Muthu kumar V
 * @version 1.0
 */
public final class JsonObject {

    private static final JsonFactory JSON_FACTORY = JsonFactory.getInstance();
    private ObjectNode objectNode;

    public JsonObject(final ObjectNode objectNode) {
        this.objectNode = objectNode;
    }

    /**
     * <p>
     * Builds the json object with the given object.
     * </p>
     *
     * @param object the object to be converted into json object
     * @return The json object
     */
    public JsonObject build(final Object object) {
        return add(JSON_FACTORY.toJson(object));
    }

    /**
     * <p>
     * Adds the object node to the json object.
     * </p>
     *
     * @param jsonNode The json node object
     * @return The json object
     */
    public JsonObject add(final JsonNode jsonNode) {
        this.objectNode = (ObjectNode) jsonNode;
             
        return this;
    }

    /**
     * <p>
     * converts the object node into byte array.
     * </p>
     *
     * @return The byte array of array node
     */
    public byte[] asBytes() {
        return JSON_FACTORY.asBytes(objectNode);
    }

    /**
     * <p>
     * Gets the object node as text.
     * </p>
     *
     * @param type the value to get from the object node
     */
    public String get(final String type) {
        return objectNode.get(type).asText();
    }

    /**
     * <p>
     *  Puts the key and value in the object node.
     * </p>
     *
     * @param key The key string
     * @param value The Value string
     * @return The Json object
     */
    public JsonObject put(final String key, final String value) {
        objectNode.put(key, value);
             
        return this;
    }

    /**
     * <p>
     * checks the object node is empty or not
     * </p>
     *
     * @return true if the object node is empty, false otherwise
     */
    public boolean isEmpty() {
        return objectNode.isEmpty();
    }

    /**
     * <p>
     * Gets the object node.
     * </p>
     *
     * @return The object node
     */
    public ObjectNode getObjectNode() {
        return objectNode;
    }
}