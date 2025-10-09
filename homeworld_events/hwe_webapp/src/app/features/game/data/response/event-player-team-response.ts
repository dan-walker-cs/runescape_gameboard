/**
 * EventPlayerTeam Response object for backend GET requests.
 */
export interface EventPlayerTeamResponse {
    id: number;
    playerNamesByTeam: Record<string, string[]>
}