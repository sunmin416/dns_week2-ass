package gdg.DNS.dto;

import lombok.Data;
import java.util.List;

@Data
public class CloudflareApiRes<T> {
    private Boolean success;
    private List<String> errors;
    private List<String> messages;
    private T result;
}
