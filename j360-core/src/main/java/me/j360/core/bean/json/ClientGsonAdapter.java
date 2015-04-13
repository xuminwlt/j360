
package me.j360.core.bean.json;

import com.google.gson.*;
import me.j360.base.util.GsonUtil;
import me.j360.core.entity.biz.Client;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Type;

/**
 * 
 * @author qianjia
 *
 */
@Component
public class ClientGsonAdapter implements JsonSerializer<Client>,
        JsonDeserializer<Client> {

  /**
   * 加载成Json对象，全部转化成String的值
   * */
  public JsonElement serialize(Client client, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject json = new JsonObject();
    json.addProperty("name", client.getName());
    json.addProperty("id", client.getId());
    json.addProperty("nickName", client.getNickName());

    json.addProperty("post", client.getPost());
    json.addProperty("tel", client.getTel());
    json.addProperty("fax", client.getFax());
    json.addProperty("web", client.getWeb());
    json.addProperty("invoiceTitle", client.getInvoiceTitle());
    json.addProperty("staffScale", client.getStaffScale());
    json.addProperty("areaScale", client.getAreaScale());
    json.addProperty("produceScale", client.getProduceScale());
    json.addProperty("salesScale", client.getSalesScale());

    return json;
  }

  /**
   * Json对象转化成Entity对象，转化String到对应的格式
   * */
  public Client deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject o = json.getAsJsonObject();
    Client client = new Client();
    client.setName(GsonUtil.getString(o, "name"));
    client.setId(GsonUtil.getString(o,"id"));

    //Integer sex = GsonUtil.getInteger(o, "sex");
    /*if(new Integer(1).equals(sex)) {
      wxMpUser.setSex("男");
    } else if (new Integer(2).equals(sex)) {
      wxMpUser.setSex("女");
    } else {
      wxMpUser.setSex("未知");
    }*/
    return client;
  }
}
