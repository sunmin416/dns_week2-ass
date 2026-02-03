package gdg.DNS.dto;

import lombok.Data;

@Data
public class DnsRecordRes {
    private String id;
    private String type;
    private String name;
    private String content;
    private Integer ttl;
    private Boolean proxied;
    private Integer priority;
}