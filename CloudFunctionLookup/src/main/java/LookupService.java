import com.google.cloud.compute.v1.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LookupService {

    static List<String> listInstanceGroupInstances(String instanceGroupName, Logger logger) throws IOException {
        List<String> ret = new ArrayList<>();
        try (InstancesClient client = InstancesClient.create()) {
            for (Instance e : client.list("g12-t1d-v2021", "europe-west3-c").iterateAll()) {
                logger.info("Instance" + e.getName());
                logger.info(e.getStatus().name());
                if (e.getStatus() == Instance.Status.RUNNING && e.getName().startsWith(instanceGroupName)) {
                    String ip = e.getNetworkInterfaces(0).getAccessConfigs(0).getNatIP();
                    logger.info("IP: " + ip);
                    ret.add(ip);
                }
            }
        }
        return ret;
    }
}

