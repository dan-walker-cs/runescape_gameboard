/**
 * TeamPlayer Response object for backend GET requests.
 */
export interface TeamPlayerResponse {
    teamId: number;
    teamName: string;
    playerNames: string[];
}