package fr.cailliaud.mockmvcdemo.service;

import fr.cailliaud.mockmvcdemo.model.Game;
import fr.cailliaud.mockmvcdemo.model.GameType;
import fr.cailliaud.mockmvcdemo.model.VideoGame;
import fr.cailliaud.mockmvcdemo.operation.CreateGameOperation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class GameService {
  public Optional<Game> getById(String gameId) {
    return Optional.empty();
  }

  public Game create(CreateGameOperation createGameOperation) {
    if (createGameOperation.getGameType() == GameType.VIDEO_GAME) {
      return new VideoGame(
          StringUtils.trimAllWhitespace(createGameOperation.getName().toUpperCase()),
          createGameOperation.getName());
    }
    throw new UnsupportedOperationException();
  }
}
