import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.ArrayList;
import java.util.List;

public class TranslateService {
    static List<String> TranslateLabels(List<String> labels) {
        List<String> labelsTranslated = new ArrayList<>(labels.size());
        try {
            Translate translate = TranslateOptions.getDefaultInstance().getService();
            for (String label : labels) {
                Translation translation = translate.translate(
                        label,
                        Translate.TranslateOption.sourceLanguage("en"),
                        Translate.TranslateOption.targetLanguage("pt"));
                labelsTranslated.add(translation.getTranslatedText());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return labelsTranslated;

    }
}