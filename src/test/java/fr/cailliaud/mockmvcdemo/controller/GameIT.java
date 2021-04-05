package fr.cailliaud.mockmvcdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cailliaud.mockmvcdemo.model.GameType;
import fr.cailliaud.mockmvcdemo.operation.CreateGameOperation;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class GameIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  @DisplayName(
      "Given my game is not recorded, when i try to get it, then i have a message to inform myself that the game is unknown")
  void shouldThrowGameNotFoundWithAnUnknownId() throws Exception {
    // Given
    String gameId = "myGame";

    // When
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/games/{id}", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
        // Then
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            MockMvcResultMatchers.status()
                .reason(Matchers.containsString("The game with id 'myGame' is unknown")));
  }

  @DisplayName(
      "When i try to create a game without providing its type, then the game is not created")
  @Test
  void shoulThrowBadRequestWithInvalidOperation() throws Exception {
    // Given
    String request = "{\"name\":\"World of Warcraft\"}";

    // When
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/games")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
        // Then
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @DisplayName(
      "Given i have a video game to create, when i provide its name and the type Video game, then my game is created")
  @Test
  void shouldCreateVideoGame() throws Exception {
    // Given
    CreateGameOperation createGameOperation =
        new CreateGameOperation().setName("World of Warcraft").setGameType(GameType.VIDEO_GAME);
    String request = objectMapper.writeValueAsString(createGameOperation);

    // When
    String response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/api/games")
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            // Then
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(
                MockMvcResultMatchers.redirectedUrlPattern("http://*/api/games/WORLDOFWARCRAFT"))
            .andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8);
    JSONAssert.assertEquals(
        " {\"id\":\"WORLDOFWARCRAFT\",\"type\":\"VIDEO_GAME\",\"name\":\"World of Warcraft\"}",
        response,
        true);
  }
}
