package cientopolis.cientopolis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Map;

import cientopolis.cientopolis.interfaces.RequestControllerListener;
import cientopolis.cientopolis.models.ResponseDTO;

/**
 * Created by nicolas.valentini on 4/7/17.
 */

public class RequestController {

    private final String URL = "http://10.0.2.2:8000/webpage/";
    private VolleyManager volleyManager;
    private Context context;
    private RequestControllerListener listener;
    private String volleyTag = "tag";

    public RequestController(Context context, RequestControllerListener listener){
        this.context = context;
        volleyManager = VolleyManager.getInstance(context);
        this.listener = listener;
    }

    public void doRequest(final Type type, String url, final Integer id, final Map<String, String> params, int method) {
        if (!hasInternet() && listener!=null) {
            listener.responseError(id, ResponseDTO.NO_INTERNET_ERROR);
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        for (String param : params.keySet()) {
            builder.appendQueryParameter(param, params.get(param));
        }
        String urll = builder.build().toString();
        Log.d("URL", "**********" + urll + "**********");
        if (listener!=null) {
            Log.d("LISTENER", "**********" + listener.toString() + "**********");
        }
        StringRequest request = new StringRequest(method, urll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String fragmentReponse = "";
                        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();//new Gson();
                        ResponseDTO responseObject = gson.fromJson(response, type);
                        if (listener!=null) {
                            if (responseObject.getStatusCode().equals("200")) {
                                listener.responseOk(id, responseObject);
                            } else {
                                if (responseObject.getErrorCode() != null && responseObject.getErrorCode() == 24){
                                    //problema de autenticacion
                                }else{
                                    listener.responseError(id, responseObject);
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Integer errorCode = 1;
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            errorCode = 23;
                        } else if (error instanceof AuthFailureError) {
                            errorCode = 24;
                        } else if (error instanceof ServerError) {
                            errorCode = 25;
                        } else if (error instanceof NetworkError) {
                            errorCode = 26;
                        } else if (error instanceof ParseError) {
                            errorCode = 27;
                        }
                        if (listener!=null) {
                            listener.responseError(id, new ResponseDTO("-1", error.toString(), null, errorCode));
                        }
                    }
                }
        );

        volleyManager.addToRequestQueue(request, volleyTag);

    }

    public void cancelRequests() {
        volleyManager.cancelPendingRequests(volleyTag);
    }

    public void post(Type type, String uri, final Integer id, final Map<String, String> params) {
        this.doRequest(type, URL+uri, id, params, Request.Method.POST);
    }

    public void delete(Type type, String uri, final Integer id, final Map<String, String> params) {
        this.doRequest(type, URL+uri, id, params, Request.Method.DELETE);
    }

    public void get(Type type, String uri, final Integer id, final Map<String, String> params) {
        this.doRequest(type, URL+uri, id, params, Request.Method.GET);
    }

    public void getFull(Type type, String url, final Integer id, final Map<String, String> params) {
        this.doRequest(type, url, id, params, Request.Method.GET);
    }

    public void patch(Type type, String uri, final Integer id, final Map<String, String> params) {
        this.doRequest(type, URL+uri, id, params, Request.Method.PATCH);
    }

    private Boolean hasInternet() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
