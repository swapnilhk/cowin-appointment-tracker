package com.swapnilhk.cowinappointmenttracker.client;

import com.swapnilhk.cowinappointmenttracker.model.CowinCalendarResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CowinClient {
	
	@Headers({ "Accept-Language: hi_IN", "User-Agent: PostmanRuntime/7.26.10" })
	@GET("v2/appointment/sessions/public/calendarByDistrict")
	Call<CowinCalendarResponse> calendarByDistrict(@Query("district_id") int districtId, @Query("date") String date);

}
