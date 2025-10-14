/**
 *  Response object containing Event-Team-Player data associated with points and scoring.
 */
export interface PlayerScoreResponse {
    teamId: number;
    teamName: string;
    playerId: number;
    playerName: string;
    score: number;
}