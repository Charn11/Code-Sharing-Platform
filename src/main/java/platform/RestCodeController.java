package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class RestCodeController {

    @Autowired
    CodeService codeService;

    @GetMapping("/code/{UUID}")
    public String getCode(@PathVariable String UUID, Model model){
        try{
            model.addAttribute("codes", codeService.getCodeForPageById(UUID));
            model.addAttribute("title", "Code");
            return "code";
        }catch(RuntimeException e){
            throw new CodeNotFoundException("Could not access URL. Try with correct URL.");
        }
    }

    @GetMapping("/api/code/{UUID}")
    public ResponseEntity<?> getApiCode(@PathVariable String UUID){
        try{
            return  ResponseEntity.ok()
                    .header("Content-Type","application/json")
                    .body(codeService.getCodeForApiById(UUID));
        }catch(RuntimeException e){
            throw new CodeNotFoundException("Could not access URL. Try with correct URL.");
        }
    }

    @PostMapping("/api/code/new")
    public ResponseEntity<?> postApiCodeNew(@RequestBody Map<String, String> newApiCode){
        String apiCode = newApiCode.get("code");
        long time = Long.parseLong(newApiCode.get("time"));
        long views  = Long.parseLong(newApiCode.get("views"));
        Code code = new Code(apiCode, time, views);
        codeService.save(code);
        Map<String, String> map = new HashMap<>();
        map.put("id", code.getId());
        System.out.println(time);
        System.out.println(views);
        System.out.println(apiCode);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/code/new")
    public String getCodeNew(){
        return "codeNew";
    }

    @GetMapping("/code/latest")
    public String getCodeLatest(Model model){
        try {
            model.addAttribute("codes", codeService.getLatestForPage());
            model.addAttribute("title", "Latest");
            return "code";
        }
        catch(RuntimeException e){
            throw new CodeNotFoundException("Could not access URL. Try with correct URL.");
        }
    }

    @GetMapping("/api/code/latest")
    public ResponseEntity<?> getApiCodeLatest(){
        try {
            return ResponseEntity.ok().body(codeService.getLatestForApi());
        }catch(RuntimeException e){
            throw new CodeNotFoundException("Could not access URL. Try with correct URL.");
        }
    }
}
