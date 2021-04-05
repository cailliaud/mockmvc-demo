package fr.cailliaud.mockmvcdemo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Game {

  protected final String id;
  protected final GameType type;
}
