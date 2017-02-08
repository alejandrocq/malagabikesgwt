package com.alejandro_castilla.malagabikesgwt.client;

import static jsinterop.annotations.JsPackage.GLOBAL;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.intendia.gwt.autorest.client.AutoRestGwt;
import com.intendia.gwt.autorest.client.RequestResourceBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import jsinterop.annotations.JsType;
import rx.Single;

public class App implements EntryPoint {

    private final String BIKES_RESOURCE_ID = "3bb304f9-9de3-4bac-943e-7acce7e8e8f9";

    @AutoRestGwt @Path("api/action/datastore_search") interface DataService {
        @GET Single<Data> data(@QueryParam("resource_id") String resourceId);
    }

    @JsType(isNative = true, namespace = GLOBAL)
    static class Data {
        public Result result;
    }

    @JsType(isNative = true, namespace = GLOBAL)
    static class Result {
        public Records[] records;
    }

    @JsType(isNative = true, namespace = GLOBAL)
    static class Records {
        public String DIRECCION;
    }

    @Override
    public void onModuleLoad() {
        RootPanel.get().add(new Label("Addresses:"));
        DataService service = new DataService_RestServiceModel(() -> {
            return new RequestResourceBuilder().path("http://datosabiertos.malaga.eu/");
        });
        service.data(BIKES_RESOURCE_ID).subscribe(d -> {
            for (Records record : d.result.records) RootPanel.get().add(new Label(record.DIRECCION));
        });
    }

}