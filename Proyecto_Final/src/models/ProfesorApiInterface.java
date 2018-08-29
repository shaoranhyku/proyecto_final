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

public interface ProfesorApiInterface {

    @GET("asignaturasProfesor/{profesor}/")
    Observable<List<ItemListAsignatura>> obtenerAsignaturasProfesor(@Path("profesor") String profesor);
}
