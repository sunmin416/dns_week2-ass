package gdg.DNS.controller;

import gdg.DNS.dto.DnsRecordReq;
import gdg.DNS.dto.DnsRecordRes;
import gdg.DNS.service.CloudflareDnsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "record-controller", description = "Cloudflare DNS Record API")
@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final CloudflareDnsService dnsService;

    // GET /records/{id}
    @Operation(summary = "특정 DNS 레코드 조회")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<DnsRecordRes>> getRecord(
            @Parameter(description = "DNS Record ID") @PathVariable String id) {
        return dnsService.getDnsRecord(id)
                .map(ResponseEntity::ok);
    }

    // PUT /records/{id}
    @Operation(summary = "DNS 레코드 수정")
    @PutMapping("/{id}")
    public Mono<ResponseEntity<DnsRecordRes>> updateRecord(
            @Parameter(description = "DNS Record ID") @PathVariable String id,
            @RequestBody DnsRecordReq request) {
        return dnsService.updateDnsRecord(id, request)
                .map(ResponseEntity::ok);
    }

    // DELETE /records/{id}
    @Operation(summary = "DNS 레코드 삭제")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteRecord(
            @Parameter(description = "DNS Record ID") @PathVariable String id) {
        return dnsService.deleteDnsRecord(id)
                .map(v -> ResponseEntity.noContent().<Void>build());
    }

    // GET /records
    @Operation(summary = "전체 DNS 레코드 목록 조회")
    @GetMapping
    public Mono<ResponseEntity<List<DnsRecordRes>>> listRecords() {
        return dnsService.listDnsRecords()
                .map(ResponseEntity::ok);
    }

    // POST /records
    @Operation(summary = "DNS 레코드 생성")
    @PostMapping
    public Mono<ResponseEntity<DnsRecordRes>> createRecord(
            @RequestBody DnsRecordReq request) {
        return dnsService.createDnsRecord(request)
                .map(ResponseEntity::ok);
    }

    // GET /records/check
    @Operation(summary = "Cloudflare DNS API 연결 상태 확인")
    @GetMapping("/check")
    public Mono<ResponseEntity<String>> checkDnsApi() {
        return dnsService.checkDnsApi()
                .map(ResponseEntity::ok);
    }
}