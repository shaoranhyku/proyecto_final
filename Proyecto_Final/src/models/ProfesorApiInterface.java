package models;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.*;

import java.util.List;

public interface ProfesorApiInterface {

    @GET("asignaturasProfesor/{profesor}/")
    Observable<List<ItemListAsignatura>> obtenerAsignaturasProfesor(@Path("profesor") String profesor);

    @GET("alumnos/")
    Observable<List<ItemListAlumno>> obtenerAlumnos();

    @GET("alumnos/{asignatura}/")
    Observable<List<ItemListAlumno>> obtenerAlumnosPorAsignatura(@Path("asignatura") String asignatura);

    @POST("alumnos/{asignatura}/{alumno}/")
    Completable agregarAlumnoAsignatura(@Path("asignatura") String asignatura, @Path("alumno") String alumno);

    @DELETE("alumnos/{asignatura}/{alumno}/")
    Completable eliminarAlumnoAsignatura(@Path("asignatura") String asignatura, @Path("alumno") String alumno);

    @GET("temas/{asignatura}/")
    Observable<List<ItemListTema>> obtenerTemasAsignatura(@Path("asignatura") String asignatura);

    @FormUrlEncoded
    @POST("temas/{asignatura}/")
    Completable crearTema(@Path("asignatura") String asignatura,
                          @Field("codTemario") String codTemario,
                          @Field("nombre") String nombre,
                          @Field("descripcion") String descripcion,
                          @Field("fechaComienzo") String fechaComienzo);

    @FormUrlEncoded
    @POST("temas/{asignatura}/{tema}/")
    Completable crearEnlaceTema(@Path("asignatura") String asignatura,
                                @Path("tema") String tema,
                                @Field("url") String url,
                                @Field("descripcion") String descripcion);

    @DELETE("temas/{asignatura}/{tema}/")
    Completable eliminarTema(@Path("asignatura") String asignatura, @Path("tema") String tema);

    @DELETE("temas/{asignatura}/{tema}/{enlace}/")
    Completable eliminarEnlace(@Path("asignatura") String asignatura, @Path("tema") String tema, @Path("enlace") String enlace);

    @GET("temas/{asignatura}/{tema}/")
    Observable<ItemListTema> obtenerTema(@Path("asignatura") String asignatura, @Path("tema") String tema);

    @FormUrlEncoded
    @POST("temas/{asignatura}/{tema}/editar/")
    Completable editarTema(@Path("asignatura") String asignatura,
                          @Path("tema") String tema,
                          @Field("nuevoCodTemario") String nuevoCodTemario,
                          @Field("nombre") String nombre,
                          @Field("descripcion") String descripcion,
                          @Field("fechaComienzo") String fechaComienzo);

    @FormUrlEncoded
    @POST("asignaciones/")
    Observable<Integer> crearAsignacion(@Field("nombre") String nombre,
                                @Field("nombreGit") String nombreGit,
                                @Field("descripcion") String descripcion,
                                @Field("fechaEntrega") String fechaEntrega,
                                @Field("fechaCreacion") String fechaCreacion);

    @FormUrlEncoded
    @POST("asignacion/{asignacion}/tema/")
    Completable asignarTemaAsignacion(@Path("asignacion") String asignacion,
                                      @Field("asignatura") String asignatura,
                                      @Field("tema") String tema);

    @FormUrlEncoded
    @POST("asignacion/{asignacion}/criterio/")
    Completable crearCriterio(@Path("asignacion") String asignacion,
                              @Field("nombre") String nombreCriterio,
                              @Field("porcentaje") String porcentaje);
}
