package id.smartech.moviedatabase.model

import com.google.gson.annotations.SerializedName

data class DetailPersonResponse (
    val id: Int,
    val name: String,
    val birthday: String?,
    val gender: Int,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    val popularity: Double,
    val biography: String,
    @SerializedName("profile_path") val profilePath: String?
)