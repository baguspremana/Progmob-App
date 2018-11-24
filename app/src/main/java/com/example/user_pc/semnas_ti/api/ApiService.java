package com.example.user_pc.semnas_ti.api;

import com.example.user_pc.semnas_ti.model.DataAdmin;
import com.example.user_pc.semnas_ti.model.InfoSeminarResponse;
import com.example.user_pc.semnas_ti.model.Response;
import com.example.user_pc.semnas_ti.model.Ticket;
import com.example.user_pc.semnas_ti.model.TicketPayment;
import com.example.user_pc.semnas_ti.model.TicketPaymentAdmin;
import com.example.user_pc.semnas_ti.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("login")
    Call<User> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<User> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("contact") String contact,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("booking")
    Call<Response> addBookingTicket(
            @Field("booking_name") String bookingName,
            @Field("booking_email") String bookingEmail,
            @Field("booking_contact") String bookingContact,
            @Field("booking_veget") int bookingVeget,
            @Field("booking_institution") String bookingInstitution
    );

    @FormUrlEncoded
    @POST("update/booking/{id}")
    Call<Response> updateBooking(
            @Path("id") int id,
            @Field("booking_name") String bookingName,
            @Field("booking_email") String bookingEmail,
            @Field("booking_contact") String bookingContact,
            @Field("booking_veget") int bookingVeget,
            @Field("booking_institution") String bookingInstitution
    );

    @DELETE("delete/booking/{id}")
    Call<Response> deleteBooking(@Path("id") int id);

    @GET("booking/show")
    Call<List<Ticket>> allTicket();

    @GET("booking/payment")
    Call<List<TicketPayment>> paymentTicket();

    @Multipart
    @POST("booking/{id}")
    Call<Response> addPayment(
            @Path("id") int id,
            @Part MultipartBody.Part photo,
            @Part("etc")RequestBody etc
    );

    @GET("admin/show/seminar")
    Call<InfoSeminarResponse> infoSeminar();

    @GET("admin/booking/payment")
    Call<List<TicketPaymentAdmin>> verifikasi();

    @FormUrlEncoded
    @POST("admin/add/seminar")
    Call<Response> addSeminarInfo(
            @Field("seminar_name") String seminarName,
            @Field("seminar_theme") String seminarTheme,
            @Field("seminar_description") String seminarDesc,
            @Field("seminar_schedule") String seminarSchedule,
            @Field("seminar_location") String seminarLocation,
            @Field("ticket_available") int seminarTicket

    );

    @GET("admin/show/admin")
    Call<List<DataAdmin>> dataAdmin();

    @FormUrlEncoded
    @POST("admin/add/admin")
    Call<Response> addAdmin(
            @Field("name") String name,
            @Field("email") String email,
            @Field("gender") int gender,
            @Field("contact") String contact,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("admin/update/seminar/{id}")
    Call<Response> updateSeminar(
            @Path("id") int id,
            @Field("seminar_name") String seminarName,
            @Field("seminar_theme") String seminarTheme,
            @Field("seminar_description") String seminarDesc,
            @Field("seminar_schedule") String seminarSchedule,
            @Field("seminar_location") String seminarLocation,
            @Field("ticket_available") int seminarTicket
    );

}
