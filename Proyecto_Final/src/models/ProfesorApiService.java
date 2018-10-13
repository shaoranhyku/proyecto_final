package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import io.reactivex.Completable;
import io.reactivex.Observable;
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
        return getInstance().retrofit.obtenerAsignaturasProfesor(profesor);
    }

    public static Observable<List<ItemListAlumno>> obtenerAlumnos() {
        return getInstance().retrofit.obtenerAlumnos();
    }

    public static Observable<List<ItemListAlumno>> obtenerAlumnosPorAsignatura(String asignatura) {
        return getInstance().retrofit.obtenerAlumnosPorAsignatura(asignatura);
    }

    public static Completable agregarAlumnosAsignatura(String asignatura, String alumno) {
        return getInstance().retrofit.agregarAlumnoAsignatura(asignatura, alumno);
    }

    public static Completable eliminarAlumnosAsignatura(String asignatura, String alumno) {
        return getInstance().retrofit.eliminarAlumnoAsignatura(asignatura, alumno);
    }

    public static Observable<List<ItemListTema>> obtenerTemasAsignatura(String asignatura) {
        return getInstance().retrofit.obtenerTemasAsignatura(asignatura);
    }

    public static Completable crearTema(String asignatura,
                                        String codTemario,
                                        String nombre,
                                        String descripcion,
                                        String fechaComienzo) {
        return getInstance().retrofit.crearTema(asignatura, codTemario, nombre, descripcion, fechaComienzo);
    }

    public static Completable crearEnlaceTema(String asignatura,
                                              String tema,
                                              String url,
                                              String descripcion) {
        return getInstance().retrofit.crearEnlaceTema(asignatura, tema, url, descripcion);
    }

    public static Completable eliminarTema(String asignatura, String tema) {
        return getInstance().retrofit.eliminarTema(asignatura, tema);
    }

    public static Completable eliminarEnlace(String asignatura, String tema, String enlace) {
        return getInstance().retrofit.eliminarEnlace(asignatura, tema, enlace);
    }

    public static Observable<ItemListTema> obtenerTema(String asignatura, String tema) {
        return getInstance().retrofit.obtenerTema(asignatura, tema);
    }

    public static Completable editarTema(String asignatura,
                                         String tema,
                                         String nuevoCodTemario,
                                         String nombre,
                                         String descripcion,
                                         String fechaComienzo) {
        return getInstance().retrofit.editarTema(asignatura, tema, nuevoCodTemario, nombre, descripcion, fechaComienzo);
    }

    public static Observable<Integer> crearAsignacion(String nombre,
                                              String nombreGit,
                                              String descripcion,
                                              String fechaEntrega,
                                              String fechaCreacion) {
        return getInstance().retrofit.crearAsignacion(nombre, nombreGit, descripcion, fechaEntrega, fechaCreacion);
    }

    public static Completable asignarTemaAsignacion(String asignacion,
                                                    String asignatura,
                                                    String tema) {
        return getInstance().retrofit.asignarTemaAsignacion(asignacion, asignatura, tema);
    }

    public static Completable crearCriterio(String asignacion,
                                            String nombreCriterio,
                                            String porcentaje) {
        return getInstance().retrofit.crearCriterio(asignacion, nombreCriterio, porcentaje);
    }

    Observable<List<AsignacionAlumnoLista>> obtenerAsignacionesAsignatura(String asignatura){
        return getInstance().retrofit.obtenerAsignacionesAsignatura(asignatura);
    }

    Observable<List<AsignacionAlumnoLista>> obtenerAsignacionesAsignaturaTema(String asignatura, String tema){
        return getInstance().retrofit.obtenerAsignacionesAsignaturaTema(asignatura,tema);
    }

    Observable<AsignacionProfesorResponse> obtenerAsignacion(String asignacion){
        return getInstance().retrofit.obtenerAsignacion(asignacion);
    }

    Completable borrarAsignacion(String asignacion){
        return getInstance().retrofit.borrarAsignacion(asignacion);
    }

    Completable borrarCriterioAsignacion(String asignacion, String criterio){
        return getInstance().retrofit.borrarCriterioAsignacion(asignacion, criterio);
    }

    Completable desasignarTemaAsignacion(String asignacion, String tema){
        return  getInstance().retrofit.desasignarTemaAsignacion(asignacion, tema);
    }

    Completable editarAsignacion(String asignacion,
                                 String nombre,
                                 String nombreGit,
                                 String descripcion,
                                 String fechaEntrega){
        return getInstance().retrofit.editarAsignacion(asignacion, nombre, nombreGit, descripcion, fechaEntrega);
    }
}
