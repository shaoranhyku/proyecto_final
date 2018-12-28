package models;

import com.google.gson.*;
import io.reactivex.Completable;
import io.reactivex.Observable;
import responses.AsignacionAlumnoResponse;
import responses.AsignaturaAlumnoResponse;
import responses.TemaAlumnoResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AlumnoApiService {

    private static AlumnoApiService ourInstance = new AlumnoApiService();
    private AlumnoApiInterface retrofit;

    private static AlumnoApiService getInstance() {
        return ourInstance;
    }

    private AlumnoApiService() {

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), formatter);
        }).registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), formatter);
        }).create();

        retrofit = new Retrofit.Builder()
//                .baseUrl("https://iessaladillo.000webhostapp.com/api/")
                .baseUrl("http://localhost:8080/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(AlumnoApiInterface.class);
    }

    public static Observable<Result<Void>> login(String autorizacion){
        return getInstance().retrofit.login(autorizacion);
    }

    public static Observable<List<ItemListAsignatura>> obtenerAsignaturasAlumno(String alumno){
        return getInstance().retrofit.obtenerAsignaturasAlumno(Sesion.getInstance().getToken(), alumno);
    }

    public static Observable<List<AsignacionAlumnoLista>> obtenerUltimasAsignacionesAlumno(String alumno){
        return getInstance().retrofit.obtenerUltimasAsignacionesAlumno(Sesion.getInstance().getToken(), alumno);
    }

    public static Observable<AsignaturaAlumnoResponse> obtenerAsignaturaAlumno(String asignatura, String alumno){
        return getInstance().retrofit.obtenerAsignaturaAlumno(Sesion.getInstance().getToken(), asignatura, alumno);
    }

    public static Observable<TemaAlumnoResponse> obtenerTemaAlumno(String asignatura, String tema, String alumno){
        return getInstance().retrofit.obtenerTemaAlumno(Sesion.getInstance().getToken(), asignatura, tema, alumno);
    }

    public static Observable<AsignacionAlumnoResponse> obtenerAsignacionAlumno(int asignacion, String alumno){
        return getInstance().retrofit.obtenerAsignacionAlumno(Sesion.getInstance().getToken(), asignacion, alumno);
    }

    public static Completable entregarAsignacion(int codigoAsignacion, String ruta, String comentario) {
        return getInstance().retrofit.entregarAsignacion(Sesion.getInstance().getToken(), codigoAsignacion, ruta, comentario);
    }

    public static Completable entregarCriterio(int codigoAsignacion, int codigoCriterio, int notaAuto){
        return getInstance().retrofit.entregarCriterio(Sesion.getInstance().getToken(), codigoAsignacion, codigoCriterio, notaAuto);
    }

    public static Observable<List<AsignacionAlumnoLista>> obtenerAsignacionesDia(LocalDate fecha, String alumno){
        return getInstance().retrofit.obtenerAsignacionesDia(Sesion.getInstance().getToken(), fecha, alumno);
    }

    public static Observable<Integer> obtenerNumAsignacionesDia(LocalDate fecha, String alumno){
        return getInstance().retrofit.obtenerNumAsignacionesDia(Sesion.getInstance().getToken(), fecha, alumno);
    }
}
