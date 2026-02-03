package gdg.DNS.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudflareApiRes<T> {
    private Boolean success;
    private List<Object> errors;
    private List<Object> messages;
    private T result;
}
