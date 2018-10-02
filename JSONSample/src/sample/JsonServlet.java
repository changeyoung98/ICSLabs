package sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;


import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JsonServlet
 */
@WebServlet("/JsonServlet")
public class JsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//List<DOMTreeRow> rowList;
    JsonStructure parsed;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String content = request.getParameter("content");
        parseJson(content);
        
        PrintWriter out = response.getWriter();
        out.println(buildJson());        
    }
    
    public void parseJson(String content) {
        /* Parse the data using the document object model approach */
        try (JsonReader reader = Json.createReader(new StringReader(content))) {
            parsed = reader.readObject();
        }

        /* Represent the DOM tree on a list for a JSF table */
        this.printTree(parsed, 0, "");
    }
    
    /* Used to populate rowList to display the DOM tree on a JSF table */
    public void printTree(JsonValue tree, int level, String key) {
        switch (tree.getValueType()) {
            case OBJECT:
                JsonObject object = (JsonObject) tree;
                for (int i = 0; i < level; i++)
                	System.out.print(" ");
                System.out.println( level + " " + tree.getValueType().toString()  + " " +  key + "--");
                for (String name : object.keySet()) {
                   this.printTree(object.get(name), level+1, name);
                }
                break;
            case ARRAY:
                JsonArray array = (JsonArray) tree;
                for (int i = 0; i < level; i++)
                	System.out.print(" ");
                System.out.println( level + " " + tree.getValueType().toString() + " " + key + "--");
                for (JsonValue val : array) {
                    this.printTree(val, level+1, "");
                }
                break;
            case STRING:
                JsonString st = (JsonString) tree;
                for (int i = 0; i < level; i++)
                	System.out.print(" ");
                System.out.println( level + " " + tree.getValueType().toString() + " " + key + " " + st.getString());
                break;
            case NUMBER:
                JsonNumber num = (JsonNumber) tree;
                for (int i = 0; i < level; i++)
                	System.out.print(" ");
                System.out.println( level + " " + tree.getValueType().toString() + " " + key + " " + num.toString());
                break;
            case FALSE:
            case TRUE:
            case NULL:
                String valtype = tree.getValueType().toString();
                for (int i = 0; i < level; i++)
                	System.out.print(" ");
                System.out.println( level + " " + valtype + " " + key + " " + valtype.toLowerCase());
                break;
        }
    }
    

    public String buildJson() {        
        /* Build JSON Object Model */
        JsonObject model = Json.createObjectBuilder()
            .add("firstName", "Tom")
            .add("lastName", "Jerry")
            .add("age", 10)
            .add("streetAddress", "Disney Avenue")
            .add("city", "los angles")
            .add("state", "CA")
            .add("postalCode", "12345")
            .add("phoneNumbers", Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                    .add("number", "911")
                    .add("type", "HOME"))
                .add(Json.createObjectBuilder()
                    .add("number", "110")
                    .add("type", "OFFICE")))
        .build();
        
        /* Write JSON Output */
        StringWriter stWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
            jsonWriter.writeObject(model);
        }
        //return stWriter.toString();
        
        /* Write formatted JSON Output */
        Map<String,String> config = new HashMap<>();
        config.put(JsonGenerator.PRETTY_PRINTING, "");
        JsonWriterFactory factory = Json.createWriterFactory(config);
        
        StringWriter stWriterF = new StringWriter();
        try (JsonWriter jsonWriterF = factory.createWriter(stWriterF)) {
            jsonWriterF.writeObject(model);
        }
        
        return stWriterF.toString();
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
	}

}
