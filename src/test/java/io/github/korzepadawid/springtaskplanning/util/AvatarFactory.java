package io.github.korzepadawid.springtaskplanning.util;

import io.github.korzepadawid.springtaskplanning.model.Avatar;
import java.util.UUID;

public abstract class AvatarFactory {

  public static Avatar getAvatar() {
    Avatar avatar = new Avatar();
    avatar.setId(11L);
    avatar.setStorageKey(UUID.randomUUID().toString());
    return avatar;
  }
}
