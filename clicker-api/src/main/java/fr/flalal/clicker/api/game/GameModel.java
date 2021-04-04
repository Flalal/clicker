package fr.flalal.clicker.api.game;

import fr.flalal.clicker.storage.tables.records.GameRecord;
import fr.flalal.clicker.storage.tables.records.PlayerRecord;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameModel {
    private GameRecord gameRecord;
    private PlayerRecord playerRecord;
    private List<GeneratorModel> generatorModels = new ArrayList<>();

}
