package foodwasting.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.swing.text.Document;
import java.util.List;

@Data
public class LocationData {

    private List<Document> documents;

    @JsonProperty("documents")
    public List<Document> getDocuments() {
        return documents;
    }
//    private String address_name;
//    private String b_code;
//    private String h_code;
//    private String main_address_no;
//    private String mountain_yn;
//    private String region_1depth_name;
//    private String region_2depth_name;
//    private String region_3depth_h_name;
//    private String region_3depth_name;
//    private String sub_address_no;
//    private String x;
//    private String y;
//
//    // Getters and setters for the fields
//
//    @JsonProperty("address_name")
//    public String getAddress_name() {
//        return address_name;
//    }
//
//    @JsonProperty("b_code")
//    public String getB_code() {
//        return b_code;
//    }
//
//    @JsonProperty("h_code")
//    public String getH_code() {
//        return h_code;
//    }
//
//    @JsonProperty("main_address_no")
//    public String getMain_address_no() {
//        return main_address_no;
//    }
//
//    @JsonProperty("mountain_yn")
//    public String getMountain_yn() {
//        return mountain_yn;
//    }
//
//    @JsonProperty("region_1depth_name")
//    public String getRegion_1depth_name() {
//        return region_1depth_name;
//    }
//
//    @JsonProperty("region_2depth_name")
//    public String getRegion_2depth_name() {
//        return region_2depth_name;
//    }
//
//    @JsonProperty("region_3depth_h_name")
//    public String getRegion_3depth_h_name() {
//        return region_3depth_h_name;
//    }
//
//    @JsonProperty("region_3depth_name")
//    public String getRegion_3depth_name() {
//        return region_3depth_name;
//    }
//
//    @JsonProperty("sub_address_no")
//    public String getSub_address_no() {
//        return sub_address_no;
//    }
//
//    @JsonProperty("x")
//    public String getX() {
//        return x;
//    }
//
//    @JsonProperty("y")
//    public String getY() {
//        return y;
//    }
}