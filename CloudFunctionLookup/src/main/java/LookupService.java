import com.google.cloud.compute.v1.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LookupService {

    static List<String> listInstanceGroupInstances(String instanceGroupName) throws IOException {
        List<String> ret = new ArrayList<>();
        try (InstancesClient client = InstancesClient.create()) {
            for (Instance e : client.list("G12-T1D-V2021", "europe-west3-c").iterateAll()) {
                if (e.getStatus() == Instance.Status.RUNNING && e.getName().startsWith(instanceGroupName)) {
                    String ip = e.getNetworkInterfaces(0).getAccessConfigs(0).getNatIP();
                    ret.add(ip);
                }
            }
        }
        return ret;
    }
}

