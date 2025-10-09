import { environment } from '../../../environments/environment';

const api = environment.apiBaseUrl;

export const endpoints = {
  tiles: {
    snapshot: `${api}/tiles`,
    stream: `${api}/tiles/stream`,
    update: (tileId: number) => `${api}/tiles/${tileId}/update`,
  },
  players: {
    snapshot: `${api}/players`,
  },
  events: {
    snapshot: `${api}/events/current`,
    playersByTeam: (eventId: number) => `${api}/rel/event/${eventId}/playersByTeam`
  }
} as const;