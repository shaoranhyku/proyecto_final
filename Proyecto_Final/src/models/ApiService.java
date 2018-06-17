package models;

import com.google.gson.*;
import io.reactivex.Observable;
import responses.LoginResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ApiService {

    private static ApiService ourInstance = new ApiService();
    private ApiInterface retrofit;

    private static ApiService getInstance() {
        return ourInstance;
    }

    private ApiService() {

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), formatter);
        }).create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ApiInterface.class);
    }

    public static Observable<LoginResponse> login(String usuario, String clave){
        return getInstance().retrofit.login(usuario, clave);
    }

    public static Observable<List<ItemListAsignatura>> obtenerAsignaturasAlumno(String alumno){
        return getInstance().retrofit.obtenerAsignaturasAlumno(alumno);
    }

    public static Observable<List<AsignacionLista>> obtenerUltimasAsignacionesAlumno(String alumno){
        return getInstance().retrofit.obtenerUltimasAsignacionesAlumno(alumno);
    }

}
