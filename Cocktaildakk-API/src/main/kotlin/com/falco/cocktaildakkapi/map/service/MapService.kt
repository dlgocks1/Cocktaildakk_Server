package com.falco.cocktaildakkapi.map.service

import com.falco.cocktaildakkapi.map.dto.KakaoSearchResDto
import com.falco.cocktaildakkapi.map.dto.MapSearchResDto
import com.falco.cocktaildakkcommon.config.properties.KakaoProperty
import com.falco.cocktaildakkcommon.exceptions.CommonErrorCode
import com.falco.cocktaildakkcommon.exceptions.model.BaseException
import com.falco.cocktaildakkcommon.model.PageResponse
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Service
class MapService(
    private val kakaoProperty: KakaoProperty
) {
    private val webClient: WebClient = WebClient.builder().build()
    fun getStoreByLocation(x: Double, y: Double, radius: Double): PageResponse<MapSearchResDto> {
        return try {
            val response: Flux<KakaoSearchResDto> = webClient.get()
                .uri("https://dapi.kakao.com/v2/local/search/keyword.json?query=칵테일&x=${x}&y=${y}&radius=${radius}")
                .header("Authorization", "KakaoAK ${kakaoProperty.restApiKey}")
                .retrieve()
                .bodyToFlux(KakaoSearchResDto::class.java)
            response.blockFirst()?.toPagingResponse()
                ?: throw BaseException(CommonErrorCode._INTERNAL_SERVER_ERROR)
        } catch (e: Exception) {
            throw BaseException(CommonErrorCode._INTERNAL_SERVER_ERROR)
        }
    }
}