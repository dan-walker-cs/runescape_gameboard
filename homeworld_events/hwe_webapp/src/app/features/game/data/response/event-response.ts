/**
 * Event Response object for backend GET requests.
 */
export interface EventResponse {
    id: number;
    title: string;
    startDt: string;
    endDt: string;
    buyIn: string;
    rulesPath: string;
}