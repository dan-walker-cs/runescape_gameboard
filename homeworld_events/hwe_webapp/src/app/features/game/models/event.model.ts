/**
 * Frontend Data Store Event model-object.
 */
export interface EventModel {
    id: number;
    title: string;
    startDt: string;
    endDt: string;
    buyIn: string;
    rulesPath: string;
}