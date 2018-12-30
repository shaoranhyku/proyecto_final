package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import io.reactivex.Completable;
import io.reactivex.Observable;
import responses.AsignacionEvaluarProfesorResponse;
import responses.AsignacionProfesorResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProfesorApiService {

    private static ProfesorApiService ourInstance = new ProfesorApiService();
    private ProfesorApiInterface retrofit;

    private static ProfesorApiService getInstance() {
        return ourInstance;
    }

    private ProfesorApiService() {

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
                .build().create(ProfesorApiInterface.class);
    }

    public static Observable<List<ItemListAsignatura>> obtenerAsignaturasProfesor(String profesor) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.obtenerAsignaturasProfesor(bearerToken, profesor);
    }

    public static Observable<List<ItemListAlumno>> obtenerAlumnos() {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.obtenerAlumnos(bearerToken);
    }

    public static Observable<List<ItemListAlumno>> obtenerAlumnosPorAsignatura(String asignatura) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.obtenerAlumnosPorAsignatura(bearerToken, asignatura);
    }

    public static Completable agregarAlumnosAsignatura(String asignatura, String alumno) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.agregarAlumnoAsignatura(bearerToken, asignatura, alumno);
    }

    public static Completable eliminarAlumnosAsignatura(String asignatura, String alumno) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.eliminarAlumnoAsignatura(bearerToken, asignatura, alumno);
    }

    public static Observable<List<ItemListTema>> obtenerTemasAsignatura(String asignatura) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.obtenerTemasAsignatura(bearerToken, asignatura);
    }

    public static Completable crearTema(String asignatura,
                                        String codTemario,
                                        String nombre,
                                        String descripcion,
                                        String fechaComienzo) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.crearTema(bearerToken, asignatura, codTemario, nombre, descripcion, fechaComienzo);
    }

    public static Completable crearEnlaceTema(String asignatura,
                                              String tema,
                                              String url,
                                              String descripcion) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.crearEnlaceTema(bearerToken, asignatura, tema, url, descripcion);
    }

    public static Completable eliminarTema(String asignatura, String tema) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.eliminarTema(bearerToken, asignatura, tema);
    }

    public static Completable eliminarEnlace(String asignatura, String tema, String enlace) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.eliminarEnlace(bearerToken, asignatura, tema, enlace);
    }

    public static Observable<ItemListTema> obtenerTema(String asignatura, String tema) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.obtenerTema(bearerToken, asignatura, tema);
    }

    public static Completable editarTema(String asignatura,
                                         String tema,
                                         String nuevoCodTemario,
                                         String nombre,
                                         String descripcion,
                                         String fechaComienzo) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.editarTema(bearerToken, asignatura, tema, nuevoCodTemario, nombre, descripcion, fechaComienzo);
    }

    public static Observable<Integer> crearAsignacion(String nombre,
                                              String nombreGit,
                                              String descripcion,
                                              String fechaEntrega,
                                              String fechaCreacion) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.crearAsignacion(bearerToken, nombre, nombreGit, descripcion, fechaEntrega, fechaCreacion);
    }

    public static Completable asignarTemaAsignacion(String asignacion,
                                                    String asignatura,
                                                    String tema) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.asignarTemaAsignacion(bearerToken, asignacion, asignatura, tema);
    }

    public static Completable crearCriterio(String asignacion,
                                            String nombreCriterio,
                                            String porcentaje) {
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.crearCriterio(bearerToken, asignacion, nombreCriterio, porcentaje);
    }

    public static Observable<List<AsignacionAlumnoLista>> obtenerAsignacionesAsignatura(String asignatura){
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.obtenerAsignacionesAsignatura(bearerToken, asignatura);
    }

    public static Observable<List<AsignacionAlumnoLista>> obtenerAsignacionesAsignaturaTema(String asignatura, String tema){
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.obtenerAsignacionesAsignaturaTema(bearerToken, asignatura,tema);
    }

    public static Observable<AsignacionProfesorResponse> obtenerAsignacion(String asignacion){
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.obtenerAsignacion(bearerToken, asignacion);
    }

    public static Completable borrarAsignacion(String asignacion){
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.borrarAsignacion(bearerToken, asignacion);
    }

    public static Completable borrarCriterioAsignacion(String asignacion, String criterio){
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.borrarCriterioAsignacion(bearerToken, asignacion, criterio);
    }

    public static Completable desasignarTemaAsignacion(String asignacion, String tema){
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return  getInstance().retrofit.desasignarTemaAsignacion(bearerToken, asignacion, tema);
    }

    public static Completable editarAsignacion(String asignacion,
                                 String nombre,
                                 String nombreGit,
                                 String descripcion,
                                 String fechaEntrega){
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.editarAsignacion(bearerToken, asignacion, nombre, nombreGit, descripcion, fechaEntrega);
    }

    public static Observable<List<AsignacionAlumnoLista>> obtenerAsignacionesAsignaturaTemaAlumno(String asignatura, String tema, String alumno){
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.obtenerAsignacionesAsignaturaTemaAlumno(bearerToken, asignatura, tema, alumno);
    }

    public static Observable<AsignacionEvaluarProfesorResponse> obtenerAsignacionEvaluar(String asignacion, String alumno){
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.obtenerAsignacionEvaluar(bearerToken, asignacion, alumno);
    }

    public static Completable evaluarAsignacion(String asignacion,
                                                String alumno,
                                                String comentario){
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return getInstance().retrofit.evaluarAsignacion(bearerToken, asignacion, alumno, comentario);
    }

    public static Completable evaluarCriterio(String asignacion,
                                              String alumno,
                                              String criterio,
                                              int notaEval){
        String bearerToken = "Bearer "+Sesion.getInstance().getToken();
        return  getInstance().retrofit. evaluarCriterio(bearerToken, asignacion, alumno, criterio, notaEval);
    }
}
