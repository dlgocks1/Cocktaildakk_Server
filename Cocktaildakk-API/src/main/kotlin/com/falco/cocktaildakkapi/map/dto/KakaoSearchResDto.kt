package com.falco.cocktaildakkapi.map.dto

import com.falco.cocktaildakkcommon.model.PageResponse
import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoSearchResDto(
    @JsonProperty("documents")
    val mapSearchInfos: List<MapSearchInfo> = emptyList(),
    @JsonProperty("meta")
    val mapSearchMetadata: MapSearchMetadata
) {
    fun toPagingResponse(): PageResponse<MapSearchResDto> {
        return PageResponse(
            contents = mapSearchInfos.map { it.toResDto() },
            totalCnt = mapSearchMetadata.totalCount.toLong(),
            isLast = mapSearchMetadata.isEnd
        )
    }
}

data class MapSearchInfo(
    @JsonProperty("address_name")
    val addressName: String,
    @JsonProperty("category_group_code")
    val categoryGroupCode: String,
    @JsonProperty("category_group_name")
    val categoryGroupName: String,
    @JsonProperty("category_name")
    val categoryName: String,
    val distance: Long,
    val id: String,
    val phone: String,
    @JsonProperty("place_name")
    val placeName: String,
    @JsonProperty("place_url")
    val placeUrl: String,
    @JsonProperty("road_address_name")
    val roadAddressName: String,
    val x: Double,
    val y: Double
) {
    fun toResDto(): MapSearchResDto {
        return MapSearchResDto(
            addressName = addressName,
            categoryGroupCode = categoryGroupCode,
            categoryGroupName = categoryGroupName,
            categoryName = categoryName,
            distance = distance,
            id = id,
            phone = phone,
            placeName = placeName,
            placeUrl = placeUrl,
            roadAddressName = roadAddressName,
            x = x,
            y = y
        )
    }
}

data class MapSearchResDto(
    val addressName: String,
    val categoryGroupCode: String,
    val categoryGroupName: String,
    val categoryName: String,
    val distance: Long,
    val id: String,
    val phone: String,
    val placeName: String,
    val placeUrl: String,
    val roadAddressName: String,
    val x: Double,
    val y: Double
)

data class MapSearchMetadata(
    @JsonProperty("is_end")
    val isEnd: Boolean = true,
    @JsonProperty("pageable_count")
    val pageableCount: Int = 0,
    @JsonProperty("total_count")
    val totalCount: Int = 0
)
