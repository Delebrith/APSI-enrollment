export enum APIErrorMessageType {
  UNKNOWN = 'unknown',
}

export interface APIError {
  status: number;
  message: string;
  messageKey: APIErrorMessageType;
  messageParams: { [key: string]: any };
}
