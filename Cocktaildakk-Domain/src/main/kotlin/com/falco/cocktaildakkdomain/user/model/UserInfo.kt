package com.falco.cocktaildakkdomain.user.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id


@Entity(name = "user_info")
data class UserInfo(
    @Id
    @Column(name = "id")
    val id: String,
    @Column(name = "alcohol_level")
    var alcoholLevel: Int = DEFAULT_LEVEL,
    /** ex) 간단한,복잡한 */
    var keyword: String = "",
    /** ex) 리큐어,보드카 */
    var base: String = "",
    @Column(name = "weight_level")
    val weightLevel: Int = 2,
    @Column(name = "weight_base")
    val weightBase: Int = 2,
    @Column(name = "weight_keyword")
    val weightKeyword: Int = 2,
) {

    val baseList: List<String>
        get() = base.split(".")
    val keywordList: List<String>
        get() = keyword.split(".")

    companion object {

        const val DEFAULT_LEVEL = 10
    }
}