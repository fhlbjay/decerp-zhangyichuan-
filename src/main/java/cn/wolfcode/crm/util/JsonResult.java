package cn.wolfcode.crm.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor
public class JsonResult {
    private boolean success =true;
    private String message;
    public JsonResult(String message){
        success = false;
        this.message = message;
    }

}
