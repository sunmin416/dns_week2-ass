package gdg.DNS.service;

import gdg.DNS.dto.CloudflareApiRes;
import gdg.DNS.dto.DnsRecordReq;
import gdg.DNS.dto.DnsRecordRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudflareDnsService {

    private final WebClient cloudflareWebClient;

    @Value("${cloudflare.api.zone-id}")
    private String zoneId;

    // POST /records - DNS 레코드 생성
    public Mono<DnsRecordRes> createDnsRecord(DnsRecordReq request) {
        return cloudflareWebClient
                .post()
                .uri("/zones/{zoneId}/dns_records", zoneId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CloudflareApiRes<DnsRecordRes>>() {})
                .map(CloudflareApiRes::getResult);
    }

    // GET /records - 전체 DNS 레코드 목록 조회
    public Mono<List<DnsRecordRes>> listDnsRecords() {
        return cloudflareWebClient
                .get()
                .uri("/zones/{zoneId}/dns_records", zoneId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CloudflareApiRes<List<DnsRecordRes>>>() {})
                .map(CloudflareApiRes::getResult);
    }

    // GET /records/{id} - 특정 DNS 레코드 조회
    public Mono<DnsRecordRes> getDnsRecord(String recordId) {
        return cloudflareWebClient
                .get()
                .uri("/zones/{zoneId}/dns_records/{recordId}", zoneId, recordId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CloudflareApiRes<DnsRecordRes>>() {})
                .map(CloudflareApiRes::getResult);
    }

    // PUT /records/{id} - DNS 레코드 수정
    public Mono<DnsRecordRes> updateDnsRecord(String recordId, DnsRecordReq request) {
        return cloudflareWebClient
                .put()
                .uri("/zones/{zoneId}/dns_records/{recordId}", zoneId, recordId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CloudflareApiRes<DnsRecordRes>>() {})
                .map(CloudflareApiRes::getResult);
    }

    // DELETE /records/{id} - DNS 레코드 삭제
    public Mono<Void> deleteDnsRecord(String recordId) {
        return cloudflareWebClient
                .delete()
                .uri("/zones/{zoneId}/dns_records/{recordId}", zoneId, recordId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CloudflareApiRes<Object>>() {})
                .then();
    }

    // GET /records/check - API 연결 상태 확인
    public Mono<String> checkDnsApi() {
        return listDnsRecords()
                .map(list -> "Cloudflare DNS API 연결 정상, 레코드 수: " + list.size())
                .onErrorResume(e -> Mono.just("Cloudflare DNS API 연결 실패: " + e.getMessage()));
    }

}