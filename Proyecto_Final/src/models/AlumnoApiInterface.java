package models;

import io.reactivex.Completable;
import io.reactivex.Observable;
import responses.AsignacionAlumnoResponse;
import responses.AsignaturaAlumnoResponse;
import responses.TemaAlumnoResponse;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.*;

import java.time.LocalDate;
import java.util.List;

public interface AlumnoApiInterface {

    @POST("login/")
    Observable<Result<Void>> login(@Header("Authorization") String autorizacion);

    @GET("asignaturas/{alumno}/")
    Observable<List<ItemListAsignatura>> obtenerAsignaturasAlumno(@Header("Authorization") String autorizacion, @Path("alumno") String alumno);

    @GET("ultimasAsignaciones/{alumno}/")
    Observable<List<AsignacionAlumnoLista>> obtenerUltimasAsignacionesAlumno(@Header("Authorization") String autorizacion, @Path("alumno") String alumno);

    @GET("asignaturaAlumno/{asignatura}/{alumno}/")
    Observable<AsignaturaAlumnoResponse> obtenerAsignaturaAlumno(@Header("Authorization") String autorizacion, @Path("asignatura") String asignatura, @Path("alumno") String alumno);

    @GET("temaAlumno/{asignatura}/{tema}/{alumno}/")
    Observable<TemaAlumnoResponse> obtenerTemaAlumno(@Header("Authorization") String autorizacion, @Path("asignatura") String asignatura, @Path("tema") String tema, @Path("alumno") String alumno);

    @GET("asignacionAlumno/{asignacion}/{alumno}/")
    Observable<AsignacionAlumnoResponse> obtenerAsignacionAlumno(@Header("Authorization") String autorizacion, @Path("asignacion") int asignacion, @Path("alumno") String alumno);

    @FormUrlEncoded
    @POST("entregarAsignacion/{asignacion}/")
    Completable entregarAsignacion(@Header("Authorization") String autorizacion, @Path("asignacion") int asignacion, @Field("rutaArchivo") String ruta, @Field("comentario") String comentario);

    @FormUrlEncoded
    @POST("entregarCriterio/{asignacion}/{criterio}/")
    Completable entregarCriterio(@Header("Authorization") String autorizacion, @Path("asignacion") int asignacion, @Path("criterio") int criterio, @Field("notaAuto") int notaAuto);

    @GET("asignacionesDia/{fecha}/{alumno}/")
    Observable<List<AsignacionAlumnoLista>> obtenerAsignacionesDia(@Header("Authorization") String autorizacion, @Path("fecha") LocalDate fecha, @Path("alumno") String alumno);

    @GET("numAsignacionesDia/{fecha}/{alumno}/")
    Observable<Integer> obtenerNumAsignacionesDia(@Header("Authorization") String autorizacion, @Path("fecha") LocalDate fecha, @Path("alumno") String alumno);

}
