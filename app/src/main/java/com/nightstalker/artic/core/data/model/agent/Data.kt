package com.nightstalker.artic.core.data.model.agent


import com.google.gson.annotations.SerializedName
import com.nightstalker.artic.core.data.model.common.SuggestAutocompleteAll

data class Data(
    @SerializedName("alt_titles")
    val altTitles: List<String>,
    @SerializedName("api_link")
    val apiLink: String,
    @SerializedName("api_model")
    val apiModel: String,
    @SerializedName("birth_date")
    val birthDate: Int,
    @SerializedName("death_date")
    val deathDate: Any,
    @SerializedName("description")
    val description: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_artist")
    val isArtist: Boolean,
    @SerializedName("sort_title")
    val sortTitle: String,
    @SerializedName("source_updated_at")
    val sourceUpdatedAt: String,
    @SerializedName("suggest_autocomplete_all")
    val suggestAutocompleteAll: SuggestAutocompleteAll,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("ulan_id")
    val ulanId: Any,
    @SerializedName("updated_at")
    val updatedAt: String
)