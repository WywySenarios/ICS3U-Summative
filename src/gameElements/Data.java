package gameElements;

/*
 * Data
 * 12/20/2023 7:33PM
 * Eric Zhu
 */

// import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
// import org.json.simple.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author pc
 */
public class Data {

    /**
     *
     */
    public final String TYPE;

    /**
     *
     */
    public final String PATH;

    /**
     *
     */
    protected JSONObject file;
	private FileWriter fileWrite;
	private PrintWriter fileOut;
	private String output;
	private boolean isEmpty;
	
    /**
     *
     * @param file_
     */
    public Data(JSONObject file_) {
		this.file = file_;
		this.TYPE = null;
		this.PATH = null;
	}

    /**
     *
     * @param type_
     * @param path_
     */
    public Data(String type_, String path_) {
		this.TYPE = type_;
		this.PATH = path_;
		isEmpty = false;
		initializeFile(); // this will throw ParseException when the END OF FILE token is at position 0
	}

	private boolean initializeFile() {
		try {
			Object o;
			o = new JSONParser().parse(new FileReader(PATH));

			file = (JSONObject) o;

		} catch (FileNotFoundException e) { // in the case the file does not exist,
			File a = new File(PATH);

			try { // try to create it :))
				a.createNewFile();
				fileWrite = new FileWriter(PATH);
				fileOut = new PrintWriter(fileWrite);

				fileOut.print("{}");
				isEmpty = true;

				fileOut.close();
				fileWrite.close();

				return initializeFile();
			} catch (IOException e1) {
			}
		} catch (IOException e) {
			System.out.println("ERROR: Something weird happened with files.\n");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("ERROR: Something weird happened with the JSON package");
			e.printStackTrace();
		}

		return true;
	}

    /**
     *
     * @return
     */
    public String getData() {
		String output = "";

		for (Object o : file.entrySet()) {
			output += file.get(o.toString().split("=")[0]) + "\n";
		}

		return output.substring(0, output.length() - 1);
	}

    /**
     *
     * @return
     */
    public Object[] getDatapoints() {
		int size = file.entrySet().size();
		int index = 0;
		Object[] output = new Object[size];

		for (Object o : file.entrySet()) {
			output[index] = o;
			index++;
		}

		return output;
	}

    /**
     *
     */
    public void pushData() {
		try {
			fileWrite = new FileWriter(PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: Something weird happened with files.\n");
			e.printStackTrace();
		}
		fileOut = new PrintWriter(fileWrite);
		output = file.toString();
		fileOut.print(output);
		try {
			fileWrite.close();
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: Something went wrong while closing the fileWriter.\n");
			e.printStackTrace();
		}
	}

    /**
     *
     * @param path_
     * @return
     */
    public Object isolateObject(String path_) {
		// the String keys uses similar notation to file paths
		// find and break up the different
		int length = 0;

		for (char i : path_.toCharArray()) {
			if (i == '\\') {
				length++;
			}
		}

		// if the path length is 0 we can skip straight to the return value
		if (length == 0) {
			return file.get(path_);
		}

		// break up the path into different keys
		String temp = "";
		String[] keys = new String[length + 1];
		// length will now be used as a counter variable
		length = 0;

		for (char i : path_.toCharArray()) {
			if (i == '\\') {
				keys[length++] = temp;
				temp = "";
			} else {
				temp += i;
			}
		}

		// the loop doesn't account for the last key. rip
		keys[length] = temp;

		try {
			// try to follow the path the user gave
			JSONObject o = (JSONObject) file.get(keys[0]);
			for (int i = 1; i < keys.length - 1; i++) {
				o = (JSONObject) o.get(keys[i]);
			}

			return o.get(keys[keys.length - 1]);
		} catch (Exception e) {
			// e.printStackTrace();
			// if one of the keys screwed up and gave null (thus making the next
			// JSONObject.get() return an error),
			return null; // mark that there was nothing valid at the given location
		}
	}
	
    /**
     *
     * @param path_
     * @return
     */
    public JSONObject isolateJSONObject(String path_) {
		return (JSONObject) isolateObject(path_);
	}

    /**
     *
     * @param path_
     * @return
     */
    public String isolateString(String path_) {
		return (String) isolateObject(path_);
	}

    /**
     *
     * @param path_
     * @return
     */
    public int isolateInt(String path_) {
		return (int) (long) isolateObject(path_);
	}

    /**
     *
     * @param path_
     * @return
     */
    public boolean isolateBoolean(String path_) {
		return (boolean) isolateObject(path_);
	}

    /**
     *
     * @param path_
     * @return
     */
    public long isolateLong(String path_) {
		return (long) isolateObject(path_);
	}

    /**
     *
     * @param path_
     * @return
     */
    public double isolateDouble(String path_) {
		Object output = isolateObject(path_);

		try {
			return (double) output;
		} catch (ClassCastException e) {
			return (double) (long) 0;
		}
	}

    /**
     *
     * @param path_
     * @return
     */
    public Object[] isolateArray(String path_) {
		JSONArray JSONContents = isolateJSONArray(path_);

		Object[] output = new Object[JSONContents.size()];
		for (int i = 0; i < JSONContents.size(); i++) {
			output[i] = JSONContents.get(i);
		}

		return output;
	}

    /**
     *
     * @param path_
     * @return
     */
    public JSONArray isolateJSONArray(String path_) {
		return (JSONArray) isolateObject(path_);
	}

    /**
     *
     * @param path_
     * @return
     */
    public String[] isolateStringArray(String path_) {
		JSONArray JSONContents = isolateJSONArray(path_);

		String[] output = new String[JSONContents.size()];
		for (int i = 0; i < JSONContents.size(); i++) {
			output[i] = (String) JSONContents.get(i);
		}

		return output;
	}

    /**
     *
     * @param path_
     * @return
     */
    public int[] isolateIntArray(String path_) {
		JSONArray JSONContents = isolateJSONArray(path_);

		int[] output = new int[JSONContents.size()];
		for (int i = 0; i < JSONContents.size(); i++) {
			output[i] = (int) JSONContents.get(i);
		}

		return output;
	}

    /**
     *
     * @param path_
     * @return
     */
    public boolean[] isolateBooleanArray(String path_) {
		JSONArray JSONContents = isolateJSONArray(path_);

		boolean[] output = new boolean[JSONContents.size()];
		for (int i = 0; i < JSONContents.size(); i++) {
			output[i] = (boolean) JSONContents.get(i);
		}

		return output;
	}

    /**
     *
     * @param path_
     * @return
     */
    public long[] isolateLongArray(String path_) {
		JSONArray JSONContents = isolateJSONArray(path_);

		long[] output = new long[JSONContents.size()];
		for (int i = 0; i < JSONContents.size(); i++) {
			output[i] = (long) JSONContents.get(i);
		}

		return output;
	}

    /**
     *
     * @param path_
     * @return
     */
    public double[] isolateDoubleArray(String path_) {
		JSONArray JSONContents = isolateJSONArray(path_);

		double[] output = new double[JSONContents.size()];
		for (int i = 0; i < JSONContents.size(); i++) {
			output[i] = (double) JSONContents.get(i);
		}

		return output;
	}

    /**
     *
     * @param key
     * @return
     */
    public boolean containsKey(Object key) {
		if (file.get(key) == null) {
			return false;
		} else {
			return true;
		}
	}

    /**
     *
     * @return
     */
    public boolean isEmpty() { // accessor for isEmpty
		return isEmpty;
	}

    /**
     *
     * @param key
     * @param o
     */
    @SuppressWarnings("unchecked")
	protected void writeDatapoint(String key, Object o) {
		file.putIfAbsent(key, o);
		file.replace(key, o);
	}
	
    /**
     *
     * @return
     * @throws ClassCastException
     */
    public String[] getKeys() throws ClassCastException {
		String[] output = new String[file.keySet().size()];
		
		int currentIndex = 0;
		for (Object o : file.keySet()) {
			output[currentIndex++] = (String) o;
		}
		
		return output;
	}
}
