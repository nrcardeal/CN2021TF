import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.util.List;

public class CloudFunction implements HttpFunction {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
       if(httpRequest.getFirstQueryParameter("name").isPresent()) {
           BufferedWriter writer = httpResponse.getWriter();
           String instanceGroup =  httpRequest.getFirstQueryParameter("name").get();
           List<String> instances = LookupService.listInstanceGroupInstances(instanceGroup);
           Gson gson = new Gson();
           gson.toJson(instances);
           writer.write(gson.toString());
       }

    }
}
