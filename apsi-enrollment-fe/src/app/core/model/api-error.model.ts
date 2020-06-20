export enum APIErrorMessageType {
  UNKNOWN = 'UNKNOWN',
  INVALID_CREDENTIALS = 'INVALID_CREDENTIALS',
  REFRESH_TOKEN_NOT_VALID = 'REFRESH_TOKEN_NOT_VALID',
  REFRESH_TOKEN_NOT_FOUND = 'REFRESH_TOKEN_NOT_FOUND',
  EVENT_NOT_FOUND = 'EVENT_NOT_FOUND',
  UNAUTHORIZED_TO_CREATE_EVENT = 'UNAUTHORIZED_TO_CREATE_EVENT',
  PLACE_TOO_SMALL = 'PLACE_TOO_SMALL',
  PLACE_NOT_AVAILABLE = 'PLACE_NOT_AVAILABLE',
  SPEAKER_UNAVAILABLE = 'SPEAKER_UNAVAILABLE',
  PLACE_NOT_FOUND = 'PLACE_NOT_FOUND',
  VALIDATION_FAILED = 'VALIDATION_FAILED',
  USER_NOT_FOUND = 'USER_NOT_FOUND',
  USER_ALREADY_SIGNED_UP = 'USER_ALREADY_SIGNED_UP',
  ATTENDEES_LIMIT_EXCEEDED = 'ATTENDEES_LIMIT_EXCEEDED',
  QR_CODE_GEN_FAILED = 'QR_CODE_GEN_FAILED',
  PAYMENT_NOT_FOUND = 'PAYMENT_NOT_FOUND',
  PAYU_EXCEPTION = 'PAYU_EXCEPTION',
  ENROLLMENT_NOT_FOUND = 'ENROLLMENT_NOT_FOUND',
  ATTENDANCE_NOT_FOUND = 'ATTENDANCE_NOT_FOUND',
  USER_NOT_AUTHORIZED_TO_CHECK_ATTENDANCE = 'USER_NOT_AUTHORIZED_TO_CHECK_ATTENDANCE',
}

export interface APIError {
  status: number;
  message: string;
  code: APIErrorMessageType;
  params: { [key: string]: any };
}
