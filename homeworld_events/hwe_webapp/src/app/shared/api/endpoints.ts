import { environment } from '../../../environments/environment';

const api = environment.apiBaseUrl;

export const endpoints = {
    events: {
        snapshot: `${api}/events/current`,
        scores: (eventId: number) => `${api}/rel/scoresByEvent/${eventId}`
    },
    tiles: {
        snapshotByTeam: (teamId: number) => `${api}/rel/tilesByTeam/${teamId}`,
        streamingByTeam: (teamId: number) => `${api}/rel/tilesByTeam/${teamId}/streaming`,
        update: (relId: number) => `${api}/rel/tilesByTeam/${relId}/update`,
    },
    players: {
        snapshotByTeam: (eventId: number) => `${api}/rel/playerTeamsByEvent/${eventId}`
    },
    boards: {
        snapshot: (eventId: number) => `${api}/boards/byEvent/${eventId}`,
        gridTilesByBoard: (boardId: number) => `${api}/rel/gridTilesByBoard/${boardId}`
    }
} as const;