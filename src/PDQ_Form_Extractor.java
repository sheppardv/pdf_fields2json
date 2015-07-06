import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.*;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.*;

public class PDQ_Form_Extractor {

    public static HashMap<String, Object> listFields(PDDocument doc) throws Exception {
        PDDocumentCatalog catalog = doc.getDocumentCatalog();
        PDAcroForm form = catalog.getAcroForm();
        List<PDField> fields = form.getFields();

        HashMap<String, Object> fieldsAsString = new HashMap<>();

        for (PDField field : fields) {
            String value = field.getValueAsString();
            String name = field.getFullyQualifiedName();

            fieldsAsString.put(name, value);
        }

        return fieldsAsString;
    }

    public static String jsonEncode(HashMap<String, Object> fields) {
        JSONObject obj = new JSONObject();
        for (Map.Entry<String, Object> entry : fields.entrySet())
        {
            obj.put(entry.getKey(), entry.getValue());
        }
        return obj.toString();
    }

    public static void main(String[] args) throws Exception {
        File file = new File(args[0]);
        PDDocument doc = PDDocument.load(file);
        String json = jsonEncode(listFields(doc));
        System.out.print(json);
    }

}