package foodwasting.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.swing.text.Document;
import java.util.List;

@Data
public class LocationData {

    private List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public static class Document {
        private String x;
        private String y;

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }
    }
}