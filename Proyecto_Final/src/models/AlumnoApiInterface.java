package models;

import io.reactivex.Completable;
import io.reactivex.Observable;
import responses.AsignacionAlumnoResponse;
import responses.AsignaturaAlumnoResponse;
import responses.LoginResponse;
import responses.TemaAlumnoResponse;
import retrofit2.http.*;

import java.time.LocalDate;
import java.util.List;

public interface AlumnoApiInterface {

    @FormUrlEncoded
    @POST("login/")
    Observable<LoginResponse> login(@Field("usuario") String usuario,@Field("password") String password);

    @GET("asignaturas/{alumno}/")
    Observable<List<ItemListAsignatura>> obtenerAsignaturasAlumno(@Path("alumno") String alumno);

    @GET("ultimasAsignaciones/{alumno}/")
    Observable<List<AsignacionAlumnoLista>> obtenerUltimasAsignacionesAlumno(@Path("alumno") String alumno);

    @GET("asignaturaAlumno/{asignatura}/{alumno}/")
    Observable<AsignaturaAlumnoResponse> obtenerAsignaturaAlumno(@Path("asignatura") String asignatura, @Path("alumno") String alumno);

    @GET("temaAlumno/{asignatura}/{tema}/{alumno}/")
    Observable<TemaAlumnoResponse> obtenerTemaAlumno(@Path("asignatura") String asignatura, @Path("tema") String tema, @Path("alumno") String alumno);

    @GET("asignacionAlumno/{asignacion}/{alumno}/")
    Observable<AsignacionAlumnoResponse> obtenerAsignacionAlumno(@Path("asignacion") int asignacion, @Path("alumno") String alumno);

    @FormUrlEncoded
    @POST("entregarAsignacion/{asignacion}/")
    Completable entregarAsignacion(@Path("asignacion") int asignacion, @Field("alumno") String alumno , @Field("clave") String clave, @Field("rutaArchivo") String ruta, @Field("comentario") String comentario);

    @FormUrlEncoded
    @POST("entregarCriterio/{asignacion}/{criterio}/")
    Completable entregarCriterio(@Path("asignacion") int asignacion, @Path("criterio") int criterio, @Field("alumno") String alumno , @Field("clave") String clave, @Field("notaAuto") int notaAuto);

    @GET("asignacionesDia/{fecha}/{alumno}/")
    Observable<List<AsignacionAlumnoLista>> obtenerAsignacionesDia(@Path("fecha") LocalDate fecha, @Path("alumno") String alumno);

    @GET("numAsignacionesDia/{fecha}/{alumno}/")
    Observable<Integer> obtenerNumAsignacionesDia(@Path("fecha") LocalDate fecha, @Path("alumno") String alumno);

}
