package id.smartech.moviedatabase.model

import com.google.gson.annotations.SerializedName

data class CastModel (
    val id: Int,
    @SerializedName("known_for_department") val knownForDepartment: String,
    val name: String,
    @SerializedName("profile_path") val profilePath: String?,
    val character: String
)