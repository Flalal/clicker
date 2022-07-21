import {defineStore} from "pinia";
import {GameRepresentation} from "@/generated/clicker-api-types";

export type ClickerState = {
    game?: GameRepresentation;
};

export const useMainStore = defineStore({
    id: "mainStore",
    state: () =>
        ({
            game: undefined
        } as ClickerState),

    actions: {
        fetchGameByPseudo() {
            return fetch('http://localhost:8080/api/v0/players/Flal/games')
                .then(response => {
                    if (!response.ok) {
                        throw new Error(response.statusText)
                    }
                    return response.json();
                })
                .then((game: GameRepresentation) => this.$state.game = game);
        },
    },
});