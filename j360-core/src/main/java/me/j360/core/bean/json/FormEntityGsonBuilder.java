package me.j360.core.bean.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.j360.core.entity.biz.Client;
import me.j360.core.entity.biz.Linkman;

public class FormEntityGsonBuilder {

  public static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(Client.class, new ClientGsonAdapter());
    INSTANCE.registerTypeAdapter(Linkman.class, new LinkmanGsonAdapter());

  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
