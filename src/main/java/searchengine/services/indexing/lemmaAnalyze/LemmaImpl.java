//package searchengine.services.indexing.lemmaAnalyze;
//
//import lombok.SneakyThrows;
//import org.springframework.stereotype.Service;
//import searchengine.dto.sites.LemmaDTO;
//import searchengine.dto.sites.PageDTO;
//import searchengine.dto.sites.SiteDTO;
//import searchengine.services.indexing.lemmaAnalyze.injectText.InjectText;
//
//import java.util.*;
//import java.util.concurrent.ForkJoinTask;
//import java.util.logging.Logger;
//
//@Service
//public class LemmaImpl implements LemmaService {
//
//    @SneakyThrows
//    @Override
//    public SiteDTO lemma(SiteDTO siteDTO) {
//        ArrayList<ForkJoinTask<PageDTO>> listInjectClass = new ArrayList<>();
//        List<PageDTO> addLemmas = new ArrayList<>();
//        for (PageDTO page : siteDTO.getPageDTO()) {
//            InjectText injectText = new InjectText(page);
//            listInjectClass.add(injectText.fork());
//        }
//        for (ForkJoinTask<PageDTO> text : listInjectClass) {
//            addLemmas.add(text.join());
//            Logger.getLogger(InjectText.class.getName()).info(Thread.currentThread().getName() + " - finish");
//        }
//        siteDTO.setPageDTO(addLemmas);
//        siteDTO.setFrequencyLemmas(frequencyLemmas(addLemmas));
//        return siteDTO;
//    }
//
//    private Map<String, Integer> frequencyLemmas(List<PageDTO> pageDTO){
//        Set<LemmaDTO> frequencyLemmas = new HashSet<>();
//        Map<String, Integer> lemmas = new TreeMap<>();
//        for (PageDTO page:pageDTO) {
//            frequencyLemmas.addAll(page.getLemmas());
//        }
//        for (LemmaDTO lemmaDTO: frequencyLemmas){
//            if (!lemmas.containsKey(lemmaDTO.getLemma())){
//                lemmas.put(lemmaDTO.getLemma(), 1);
//            } else {
//                int count = lemmas.get(lemmaDTO.getLemma()) + 1;
//                lemmas.put(lemmaDTO.getLemma(), count);
//            }
//        }
//
//        return lemmas;
//    }
//}
