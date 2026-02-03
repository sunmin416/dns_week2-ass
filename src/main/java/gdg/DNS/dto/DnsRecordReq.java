package gdg.DNS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DnsRecordReq {
    private String type;
    private String name;
    private String content;
    private Integer ttl;
    private Boolean proxied;
    private Integer priority;
}