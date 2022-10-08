package com.nightstalker.artic.core.data.model.artwork.detail

import com.google.gson.annotations.SerializedName
import com.nightstalker.artic.core.data.model.common.SuggestAutocompleteAll

data class ArtworkData(
    @SerializedName("alt_artist_ids")
    val altArtistIds: List<String> = listOf(),
    @SerializedName("alt_classification_ids")
    val altClassificationIds: List<String> = listOf(),
    @SerializedName("alt_image_ids")
    val altImageIds: List<String> = listOf(),
    @SerializedName("alt_material_ids")
    val altMaterialIds: List<String> = listOf(),
    @SerializedName("alt_style_ids")
    val altStyleIds: List<String> = listOf(),
    @SerializedName("alt_subject_ids")
    val altSubjectIds: List<String> = listOf(),
    @SerializedName("alt_technique_ids")
    val altTechniqueIds: List<String> = listOf(),
    @SerializedName("alt_titles")
    val altTitles: String,
    @SerializedName("api_link")
    val apiLink: String,
    @SerializedName("api_model")
    val apiModel: String,
    @SerializedName("artist_display")
    val artistDisplay: String,
    @SerializedName("artist_id")
    val artistId: Int,
    @SerializedName("artist_ids")
    val artistIds: List<Int> = listOf(),
    @SerializedName("artist_title")
    val artistTitle: String,
    @SerializedName("artist_titles")
    val artistTitles: List<String> = listOf(),
    @SerializedName("artwork_type_id")
    val artworkTypeId: Int,
    @SerializedName("artwork_type_title")
    val artworkTypeTitle: String,
    @SerializedName("boost_rank")
    val boostRank: String,
    @SerializedName("catalogue_display")
    val catalogueDisplay: String,
    @SerializedName("category_ids")
    val categoryIds: List<String> = listOf(),
    @SerializedName("category_titles")
    val categoryTitles: List<String> = listOf(),
    @SerializedName("classification_id")
    val classificationId: String,
    @SerializedName("classification_ids")
    val classificationIds: List<String> = listOf(),
    @SerializedName("classification_title")
    val classificationTitle: String,
    @SerializedName("classification_titles")
    val classificationTitles: List<String> = listOf(),
    @SerializedName("color")
    val color: Color,
    @SerializedName("colorfulness")
    val colorfulness: Double,
    @SerializedName("copyright_notice")
    val copyrightNotice: String,
    @SerializedName("credit_line")
    val creditLine: String,
    @SerializedName("date_display")
    val dateDisplay: String,
    @SerializedName("date_end")
    val dateEnd: Int,
    @SerializedName("date_qualifier_id")
    val dateQualifierId: String,
    @SerializedName("date_qualifier_title")
    val dateQualifierTitle: String,
    @SerializedName("date_start")
    val dateStart: Int,
    @SerializedName("department_id")
    val departmentId: String,
    @SerializedName("department_title")
    val departmentTitle: String,
    @SerializedName("dimensions")
    val dimensions: String,
    @SerializedName("document_ids")
    val documentIds: List<String> = listOf(),
    @SerializedName("exhibition_history")
    val exhibitionHistory: String,
    @SerializedName("fiscal_year")
    val fiscalYear: Int,
    @SerializedName("fiscal_year_deaccession")
    val fiscalYearDeaccession: String,
    @SerializedName("gallery_id")
    val galleryId: String,
    @SerializedName("gallery_title")
    val galleryTitle: String,
    @SerializedName("has_educational_resources")
    val hasEducationalResources: Boolean,
    @SerializedName("has_multimedia_resources")
    val hasMultimediaResources: Boolean,
    @SerializedName("has_not_been_viewed_much")
    val hasNotBeenViewedMuch: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_id")
    val imageId: String,
    @SerializedName("inscriptions")
    val inscriptions: String,
    @SerializedName("internal_department_id")
    val internalDepartmentId: Int,
    @SerializedName("is_boosted")
    val isBoosted: Boolean,
    @SerializedName("is_on_view")
    val isOnView: Boolean,
    @SerializedName("is_public_domain")
    val isPublicDomain: Boolean,
    @SerializedName("is_zoomable")
    val isZoomable: Boolean,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("latlon")
    val latlon: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("main_reference_number")
    val mainReferenceNumber: String,
    @SerializedName("material_id")
    val materialId: String,
    @SerializedName("material_ids")
    val materialIds: List<String> = listOf(),
    @SerializedName("material_titles")
    val materialTitles: List<String> = listOf(),
    @SerializedName("max_zoom_window_size")
    val maxZoomWindowSize: Int,
    @SerializedName("medium_display")
    val mediumDisplay: String,
    @SerializedName("on_loan_display")
    val onLoanDisplay: String,
    @SerializedName("place_of_origin")
    val placeOfOrigin: String,
    @SerializedName("provenance_text")
    val provenanceText: String,
    @SerializedName("publication_history")
    val publicationHistory: String,
    @SerializedName("publishing_verification_level")
    val publishingVerificationLevel: String,
    @SerializedName("section_titles")
    val sectionTitles: List<String> = listOf(),
    @SerializedName("site_ids")
    val siteIds: List<String> = listOf(),
    @SerializedName("sound_ids")
    val soundIds: List<String> = listOf(),
    @SerializedName("source_updated_at")
    val sourceUpdatedAt: String,
    @SerializedName("style_id")
    val styleId: String,
    @SerializedName("style_ids")
    val styleIds: List<String> = listOf(),
    @SerializedName("style_title")
    val styleTitle: String,
    @SerializedName("style_titles")
    val styleTitles: List<String> = listOf(),
    @SerializedName("subject_id")
    val subjectId: String,
    @SerializedName("subject_ids")
    val subjectIds: List<String> = listOf(),
    @SerializedName("subject_titles")
    val subjectTitles: List<String> = listOf(),
    @SerializedName("suggest_autocomplete_all")
    val suggestAutocompleteAll: List<SuggestAutocompleteAll> = listOf(),
    @SerializedName("technique_id")
    val techniqueId: String,
    @SerializedName("technique_ids")
    val techniqueIds: List<String> = listOf(),
    @SerializedName("technique_titles")
    val techniqueTitles: List<String> = listOf(),
    @SerializedName("term_titles")
    val termTitles: List<String> = listOf(),
    @SerializedName("text_ids")
    val textIds: List<String> = listOf(),
    @SerializedName("theme_titles")
    val themeTitles: List<String> = listOf(),
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("video_ids")
    val videoIds: List<String> = listOf()
)