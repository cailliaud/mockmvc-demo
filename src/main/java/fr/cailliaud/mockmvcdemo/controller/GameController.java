package fr.cailliaud.mockmvcdemo.controller;

import fr.cailliaud.mockmvcdemo.model.Game;
import fr.cailliaud.mockmvcdemo.operation.CreateGameOperation;
import fr.cailliaud.mockmvcdemo.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

  private final GameService gameService;

  @GetMapping("/{id}")
  public Game get(@PathVariable("id") String gameId) {
    return gameService
        .getById(gameId)
        .orElseThrow(
            () ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("The game with id '%s' is unknown", gameId)));
  }

  @PostMapping
  public ResponseEntity<Game> create(@Valid @RequestBody CreateGameOperation createGameOperation) {
    Game game = gameService.create(createGameOperation);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(game.getId())
            .toUri();
    return ResponseEntity.created(location).body(game);
  }
}
