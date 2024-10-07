package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CodeService {

    private final CodeRepository codeRepository;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Autowired
    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public Code findCodeById(String id){
        return codeRepository.findCodeById(id);
    }

    public void save(Code toSave) {
        codeRepository.save(toSave);
    }

    public List<Map<String,Object>> getCodeForPageById(String UUID){
        List<Map<String,Object>> mapList = new ArrayList<>();
        Map <String, Object> map = new HashMap<>();
        Code code = codeRepository.findCodeById(UUID);
        System.out.println(code.getTime());
        Instant end = Instant.now();
        map.put("date", code.getDate().format(format));
        map.put("code", code.getCode());
        if(code.isRestrictedByBoth()){
            long timeElapsed = Duration.between(code.getStart(), end).getSeconds();
            code.setStart(end);
            code.setTime(Math.max(code.getTime() - timeElapsed, 0));
            code.setViews(code.getViews()-1);
            codeRepository.save(code);
            map.put("time", code.getTime());
            map.put("views", code.getViews());
        } else if (code.isRestrictedByTime()) {
            long timeElapsed = Duration.between(code.getStart(), end).getSeconds();
            code.setStart(end);
            code.setTime(Math.max(code.getTime() - timeElapsed, 0));
            codeRepository.save(code);
            map.put("time", code.getTime());
        } else if (code.isRestrictedByViews()) {
            code.setViews(code.getViews()-1);
            codeRepository.save(code);
            map.put("views", code.getViews());
        }
        if(code.getViews()<=0&&(code.isRestrictedByBoth()||code.isRestrictedByViews())){
            codeRepository.deleteRow(UUID);
        }
        if(code.getTime()<=0&&(code.isRestrictedByBoth()|| code.isRestrictedByTime())){
            codeRepository.deleteRow(UUID);
            throw new CodeNotFoundException("Not Found.");
        }
        mapList.add(map);
        return mapList;
    }

    public Map<String, Object> getCodeForApiById(String UUID){
        Map <String, Object> map = new HashMap<>();
        Code code = codeRepository.findCodeById(UUID);
        Instant end = Instant.now();
        map.put("date", code.getDate().format(format));
        map.put("code", code.getCode());
        if(code.isRestrictedByBoth()){
            long timeElapsed = Duration.between(code.getStart(), end).getSeconds();
            code.setStart(end);
            code.setTime(Math.max(code.getTime() - timeElapsed, 0));
            code.setViews(code.getViews()-1);
            codeRepository.save(code);
            map.put("time", code.getTime());
            map.put("views", code.getViews());
        } else if (code.isRestrictedByTime()) {
            System.out.println("time: "+code.getTime());
            long timeElapsed = Duration.between(code.getStart(), end).getSeconds();
            System.out.println("elapsed: "+timeElapsed);
            code.setStart(end);
            code.setTime(Math.max(code.getTime() - timeElapsed, 0));
            codeRepository.save(code);
            System.out.println("Updated time: "+code.getTime());
            map.put("time", code.getTime());
            map.put("views", 0);
        } else if (code.isRestrictedByViews()) {
            code.setViews(code.getViews()-1);
            codeRepository.save(code);
            map.put("time", 0);
            map.put("views", code.getViews());
        } else{
            map.put("time", 0);
            map.put("views", 0);
        }
        if(code.getViews()<=0&&(code.isRestrictedByBoth()||code.isRestrictedByViews())){
            codeRepository.deleteRow(UUID);
        }
        if(code.getTime()<=0&&(code.isRestrictedByBoth()||code.isRestrictedByTime())){
            System.out.println("delete: "+code.getTime());
            codeRepository.deleteRow(UUID);
            System.out.println("time");
            throw new CodeNotFoundException("Not Found.");
        }
        return map;
    }

    public List<Map<String,String>> getLatestForPage() {
        List<Map<String, String>> mapList = new ArrayList<>();
        List<Code> codes = codeRepository.getTenLatestUnCodes();
        for (int i = 0; i < codes.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("date", codes.get(i).getDate().format(format));
            map.put("code", codeRepository.findCodeById(codes.get(i).getId()).getCode());
            mapList.add(map);
        }
        return mapList;
    }

    public List<Map<String,Object>> getLatestForApi() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Code> codes = codeRepository.getTenLatestUnCodes();
        for (int i = 0; i < codes.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", codes.get(i).getDate().format(format));
            map.put("code", codeRepository.findCodeById(codes.get(i).getId()).getCode());
            map.put("time", 0);
            map.put("views", 0);
            mapList.add(map);
        }
        return mapList;
    }
}
