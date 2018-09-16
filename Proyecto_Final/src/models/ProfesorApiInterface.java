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
}
