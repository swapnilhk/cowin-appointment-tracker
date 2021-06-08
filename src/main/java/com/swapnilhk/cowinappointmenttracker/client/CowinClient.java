package com.swapnilhk.cowinappointmenttracker.client;

import com.swapnilhk.cowinappointmenttracker.model.CowinResponse;
import com.swapnilhk.cowinappointmenttracker.model.CowinResponseNext7Days;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CowinClient {
	@Headers({
		"Accept-Language: hi_IN",
		"User-Agent: PostmanRuntime/7.26.10"
	})
	@GET("v2/appointment/sessions/public/findByDistrict")
	Call<CowinResponse> findByDistrict(@Query("district_id") int districtId, @Query("date") String date);

	@Headers({
                "Accept-Language: hi_IN",
                "User-Agent: PostmanRuntime/7.26.10"
        })
        @GET("v2/appointment/sessions/public/findByPin")
        Call<CowinResponse> findByPin(@Query("pincode") int pincode, @Query("date") String date);

	@Headers({
                "Accept-Language: hi_IN",
                "User-Agent: PostmanRuntime/7.26.10"
        })
        @GET("v2/appointment/sessions/public/calendarByDistrict")
        Call<CowinResponseNext7Days> calendarByDistrict(@Query("district_id") int districtId, @Query("date") String date);

	@Headers({
                "Accept-Language: hi_IN",
                "User-Agent: PostmanRuntime/7.26.10"
        })
        @GET("v2/appointment/sessions/public/calendarByPin")
        Call<CowinResponseNext7Days> calendarByPin(@Query("pincode") int pincode, @Query("date") String date);
}

