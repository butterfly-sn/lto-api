package com.ltonetwork.client.types;

import com.ltonetwork.client.utils.Encoder;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.OrderedJSONObject;

import java.util.Iterator;

public class JsonObject {
    private static final int OBJECT = 1;
    private static final int ARRAY = 2;

    private final int type;

    private OrderedJSONObject object;
    private JSONArray array;

    public JsonObject(boolean isArray) {
        if (isArray) {
            this.type = ARRAY;
        } else {
            this.type = OBJECT;
        }

        init();
    }

    public JsonObject() {
        this(false);
    }

    public JsonObject(String string, boolean isArray) {
        if (isArray) {
            this.type = ARRAY;
        } else {
            this.type = OBJECT;
        }

        init(string);
    }

    public JsonObject(String string) {
        this(string, false);
    }


    public OrderedJSONObject getObject() {
        if (type == OBJECT) {
            return object;
        }
        return null;
    }

    public void setObject(OrderedJSONObject object) {
        if (type == OBJECT) {
            this.object = object;
        }
    }

    public boolean isArray() {
        return type == ARRAY;
    }

    private void init() {
        if (type == OBJECT) {
            object = new OrderedJSONObject();
        }
        if (type == ARRAY) {
            array = new JSONArray();
        }
    }

    private void init(String string) {
        try {
            if (type == OBJECT) {
                object = new OrderedJSONObject(string);
            }
            if (type == ARRAY) {
                array = new JSONArray(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        try {
            if (type == OBJECT) {
                return object.write(false);
            }
            if (type == ARRAY) {
                return array.write(false);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Object get(String key) {
        try {
            if (type == OBJECT) {
                return object.get(key);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String getString(String key) {
        try {
            if (type == OBJECT) {
                return object.get(key).toString();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public JsonObject getJsonObject(String key) {
        if (type == OBJECT) {
            JsonObject obj = new JsonObject();
            try {
                obj.setObject((OrderedJSONObject) object.getJSONObject(key));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return obj;
        }

        return null;
    }

    public String get(int index) {
        if (type == ARRAY) {
            return array.get(index).toString();
        }
        return null;
    }

    public boolean has(String key) {
        if (type == OBJECT) {
            return object.has(key);
        }
        return false;
    }

    public void put(String key, String value) {
        try {
            if (type == OBJECT) {
                object.put(key, value);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void putByte(String key, byte[] value) {
        try {
            if (type == OBJECT) {
                object.put(key, Encoder.base58Encode(value));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public byte[] getByte(String key) {
        if (type == OBJECT) {
            return has(key) ? Encoder.base58Decode(getString(key)) : null;
        }
        return null;
    }

    public void put(int index, String value) {
        try {
            if (type == ARRAY) {
                array.put(index, value);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Iterator<?> keys() {
        if (type == OBJECT) {
            return object.keys();
        }
        return null;
    }

    public int length() {
        if (type == ARRAY) {
            return array.length();
        }
        return 0;
    }
}
