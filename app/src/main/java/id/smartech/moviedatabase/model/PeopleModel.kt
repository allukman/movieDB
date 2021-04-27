package id.smartech.moviedatabase.model

import com.google.gson.annotations.SerializedName

data class PeopleModel (
    val id: Int?,
    val name: String?,
    @SerializedName("profile_path") val profilePath: String?
)