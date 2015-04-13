
package me.j360.core.bean.json;

import com.google.gson.*;
import me.j360.base.util.GsonUtil;
import me.j360.core.entity.biz.Linkman;

import java.lang.reflect.Type;

/**
 * 
 * @author qianjia
 *
 */
public class LinkmanGsonAdapter implements JsonSerializer<Linkman>,
        JsonDeserializer<Linkman> {
  /**
   * 加载成Json对象，全部转化成String的值
   * */
  public JsonElement serialize(Linkman linkman, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject json = new JsonObject();
    json.addProperty("name", linkman.getName());
    json.addProperty("id", linkman.getId());
    json.addProperty("nickName", linkman.getNickName());

    json.addProperty("duty", linkman.getNickName());
    json.addProperty("mobile", linkman.getNickName());
    json.addProperty("mobile_back", linkman.getNickName());
    json.addProperty("tel", linkman.getNickName());
    json.addProperty("email", linkman.getNickName());

    return json;
  }

  /**
   * Json对象转化成Entity对象，转化String到对应的格式
   * */
  public Linkman deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject o = json.getAsJsonObject();
    Linkman linkman = new Linkman();
    linkman.setName(GsonUtil.getString(o, "name"));
    linkman.setId(GsonUtil.getString(o,"id"));

    return linkman;
  }
}
