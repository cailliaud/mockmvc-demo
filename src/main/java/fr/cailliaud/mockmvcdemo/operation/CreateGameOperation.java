package fr.cailliaud.mockmvcdemo.operation;

import fr.cailliaud.mockmvcdemo.model.GameType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Accessors(chain = true)
@Getter
@Setter
public class CreateGameOperation {

  @Size(min = 1, max = 255)
  private String name;

  @NotNull(message = "The game type is mandatory")
  private GameType gameType;
}
