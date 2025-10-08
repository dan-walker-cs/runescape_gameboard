/**
 * Utility method for string validation.
 * Returns true: String contains a null value or only whitespace.
 * Returns false: String contains valid data.
 * @param str
 * @returns boolean
 */
export function isNullorBlankString(str: string): boolean {
    return str == null || str.trim().length === 0;
}