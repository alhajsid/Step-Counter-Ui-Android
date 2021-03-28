import com.google.gson.annotations.SerializedName



data class StepModel (

	@SerializedName("success") val success : Boolean,
	@SerializedName("data") val data : List<Data>,
	@SerializedName("message") val message : String
){
	data class Data (
		@SerializedName("distance") val distance : Int,
		@SerializedName("time") val time : Int,
		@SerializedName("activity_id") val activity_id : Int,
		@SerializedName("steps") val steps : Int,
		@SerializedName("user_id") val user_id : Int,
		@SerializedName("date") val date : String
	)
}