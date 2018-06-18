package models;

import io.reactivex.Observable;
import responses.AsignaturaAlumnoResponse;
import responses.LoginResponse;
import responses.TemaAlumnoResponse;
import retrofit2.http.*;

import java.util.List;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login/")
    Observable<LoginResponse> login(@Field("usuario") String usuario,@Field("password") String password);

    @GET("asignaturas/{alumno}/")
    Observable<List<ItemListAsignatura>> obtenerAsignaturasAlumno(@Path("alumno") String alumno);

    @GET("ultimasAsignaciones/{alumno}/")
    Observable<List<AsignacionLista>> obtenerUltimasAsignacionesAlumno(@Path("alumno") String alumno);

    @GET("asignaturaAlumno/{asignatura}/{alumno}/")
    Observable<AsignaturaAlumnoResponse> obtenerAsignaturaAlumno(@Path("asignatura") String asignatura, @Path("alumno") String alumno);

    @GET("temaAlumno/{asignatura}/{tema}/{alumno}/")
    Observable<TemaAlumnoResponse> obtenerTemaAlumno(@Path("asignatura") String asignatura, @Path("tema") String tema, @Path("alumno") String alumno);

}
