package com.falco.cocktaildakkapi.map.controller

import com.falco.cocktaildakkapi.map.dto.MapSearchInfo
import com.falco.cocktaildakkapi.map.service.MapService
import com.falco.cocktaildakkcommon.model.PageResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "MapController")
@RequestMapping("map")
class MapController(
    private val mapService: MapService,
) {

    @GetMapping("/location")
    fun getStoreByLocation(
        @RequestParam("x") x: Double,
        @RequestParam("y") y: Double,
        @RequestParam("radius") radius: Double,
    ): PageResponse<MapSearchInfo> {
        return mapService.getStoreByLocation(x, y, radius)
    }

}