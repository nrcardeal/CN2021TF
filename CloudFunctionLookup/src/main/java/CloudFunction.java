import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.util.List;
import java.util.logging.Logger;

public class CloudFunction implements HttpFunction {
    private static final Logger logger = Logger.getLogger(CloudFunction.class.getName());
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
       if(httpRequest.getFirstQueryParameter("name").isPresent()) {
           BufferedWriter writer = httpResponse.getWriter();
           String instanceGroup =  httpRequest.getFirstQueryParameter("name").get();
           logger.info(instanceGroup);
           List<String> instances = LookupService.listInstanceGroupInstances(instanceGroup, logger);
           logger.info("Instances: " + instances);
           Gson gson = new Gson();
           writer.write(gson.toJson(instances));
       }

    }
}
