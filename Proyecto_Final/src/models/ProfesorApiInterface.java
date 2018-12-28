package models;

import io.reactivex.Completable;
import io.reactivex.Observable;
import responses.AsignacionEvaluarProfesorResponse;
import responses.AsignacionProfesorResponse;
import retrofit2.http.*;

import java.util.List;

public interface ProfesorApiInterface {

    @GET("asignaturasProfesor/{profesor}/")
    Observable<List<ItemListAsignatura>> obtenerAsignaturasProfesor(@Header("Authorization") String autorizacion, @Path("profesor") String profesor);

    @GET("alumnos/")
    Observable<List<ItemListAlumno>> obtenerAlumnos(@Header("Authorization") String autorizacion);

    @GET("alumnos/{asignatura}/")
    Observable<List<ItemListAlumno>> obtenerAlumnosPorAsignatura(@Header("Authorization") String autorizacion, @Path("asignatura") String asignatura);

    @POST("alumnos/{asignatura}/{alumno}/")
    Completable agregarAlumnoAsignatura(@Header("Authorization") String autorizacion, @Path("asignatura") String asignatura, @Path("alumno") String alumno);

    @DELETE("alumnos/{asignatura}/{alumno}/")
    Completable eliminarAlumnoAsignatura(@Header("Authorization") String autorizacion, @Path("asignatura") String asignatura, @Path("alumno") String alumno);

    @GET("temas/{asignatura}/")
    Observable<List<ItemListTema>> obtenerTemasAsignatura(@Header("Authorization") String autorizacion, @Path("asignatura") String asignatura);

    @FormUrlEncoded
    @POST("temas/{asignatura}/")
    Completable crearTema(@Header("Authorization") String autorizacion,
                          @Path("asignatura") String asignatura,
                          @Field("codTemario") String codTemario,
                          @Field("nombre") String nombre,
                          @Field("descripcion") String descripcion,
                          @Field("fechaComienzo") String fechaComienzo);

    @FormUrlEncoded
    @POST("temas/{asignatura}/{tema}/")
    Completable crearEnlaceTema(@Header("Authorization") String autorizacion,
                                @Path("asignatura") String asignatura,
                                @Path("tema") String tema,
                                @Field("url") String url,
                                @Field("descripcion") String descripcion);

    @DELETE("temas/{asignatura}/{tema}/")
    Completable eliminarTema(@Header("Authorization") String autorizacion, @Path("asignatura") String asignatura, @Path("tema") String tema);

    @DELETE("temas/{asignatura}/{tema}/{enlace}/")
    Completable eliminarEnlace(@Header("Authorization") String autorizacion, @Path("asignatura") String asignatura, @Path("tema") String tema, @Path("enlace") String enlace);

    @GET("temas/{asignatura}/{tema}/")
    Observable<ItemListTema> obtenerTema(@Header("Authorization") String autorizacion, @Path("asignatura") String asignatura, @Path("tema") String tema);

    @FormUrlEncoded
    @POST("temas/{asignatura}/{tema}/editar/")
    Completable editarTema(@Header("Authorization") String autorizacion,
                           @Path("asignatura") String asignatura,
                           @Path("tema") String tema,
                           @Field("nuevoCodTemario") String nuevoCodTemario,
                           @Field("nombre") String nombre,
                           @Field("descripcion") String descripcion,
                           @Field("fechaComienzo") String fechaComienzo);

    @FormUrlEncoded
    @POST("asignaciones/")
    Observable<Integer> crearAsignacion(@Header("Authorization") String autorizacion,
                                        @Field("nombre") String nombre,
                                        @Field("nombreGit") String nombreGit,
                                        @Field("descripcion") String descripcion,
                                        @Field("fechaEntrega") String fechaEntrega,
                                        @Field("fechaCreacion") String fechaCreacion);

    @FormUrlEncoded
    @POST("asignacion/{asignacion}/tema/")
    Completable asignarTemaAsignacion(@Header("Authorization") String autorizacion,
                                      @Path("asignacion") String asignacion,
                                      @Field("asignatura") String asignatura,
                                      @Field("tema") String tema);

    @FormUrlEncoded
    @POST("asignacion/{asignacion}/criterio/")
    Completable crearCriterio(@Header("Authorization") String autorizacion,
                              @Path("asignacion") String asignacion,
                              @Field("nombre") String nombreCriterio,
                              @Field("porcentaje") String porcentaje);

    @GET("asignaciones/{asignatura}/")
    Observable<List<AsignacionAlumnoLista>> obtenerAsignacionesAsignatura(@Header("Authorization") String autorizacion, @Path("asignatura") String asignatura);

    @GET("asignaciones/{asignatura}/{tema}/")
    Observable<List<AsignacionAlumnoLista>> obtenerAsignacionesAsignaturaTema(@Header("Authorization") String autorizacion, @Path("asignatura") String asignatura, @Path("tema") String tema);

    @GET("asignacion/{asignacion}/")
    Observable<AsignacionProfesorResponse> obtenerAsignacion(@Header("Authorization") String autorizacion, @Path("asignacion") String asignacion);

    @DELETE("asignacion/{asignacion}/")
    Completable borrarAsignacion(@Header("Authorization") String autorizacion, @Path("asignacion") String asignacion);

    @DELETE("asignacion/{asignacion}/criterio/{criterio}/")
    Completable borrarCriterioAsignacion(@Header("Authorization") String autorizacion, @Path("asignacion") String asignacion, @Path("criterio") String criterio);

    @DELETE("asignacion/{asignacion}/tema/{tema}/")
    Completable desasignarTemaAsignacion(@Header("Authorization") String autorizacion, @Path("asignacion") String asignacion, @Path("tema") String tema);

    @FormUrlEncoded
    @POST("asignacion/{asignacion}/")
    Completable editarAsignacion(@Header("Authorization") String autorizacion,
                                 @Path("asignacion") String asignacion,
                                 @Field("nombre") String nombre,
                                 @Field("nombreGit") String nombreGit,
                                 @Field("descripcion") String descripcion,
                                 @Field("fechaEntrega") String fechaEntrega);

    @GET("asignaciones/{asignatura}/{tema}/{alumno}/")
    Observable<List<AsignacionAlumnoLista>> obtenerAsignacionesAsignaturaTemaAlumno(@Header("Authorization") String autorizacion, @Path("asignatura") String asignatura, @Path("tema") String tema, @Path("alumno") String alumno);

    @GET("asignacion/{asignacion}/{alumno}/")
    Observable<AsignacionEvaluarProfesorResponse> obtenerAsignacionEvaluar(@Header("Authorization") String autorizacion, @Path("asignacion") String asignacion, @Path("alumno") String alumno);

    @FormUrlEncoded
    @POST("asignacion/{asignacion}/{alumno}/")
    Completable evaluarAsignacion(@Header("Authorization") String autorizacion,
                                  @Path("asignacion") String asignacion,
                                  @Path("alumno") String alumno,
                                  @Field("comentario") String comentario);

    @FormUrlEncoded
    @POST("asignacion/{asignacion}/{alumno}/{criterio}/")
    Completable evaluarCriterio(@Header("Authorization") String autorizacion,
                                @Path("asignacion") String asignacion,
                                @Path("alumno") String alumno,
                                @Path("criterio") String criterio,
                                @Field("notaEval") int notaEval);
}
