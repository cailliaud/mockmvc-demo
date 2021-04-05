package fr.cailliaud.mockmvcdemo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoGame extends Game {

  private String name;

  public VideoGame(String id, String name) {
    super(id, GameType.VIDEO_GAME);
    this.name = name;
  }
}
