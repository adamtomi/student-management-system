const BASE_URL = 'http://localhost:8080'

interface BaseApiResponse {
  success: boolean
  status: number
}

interface ApiResponseSuccess<T> extends BaseApiResponse {
  success: true
  data: T
}

interface ApiResponseFailure extends BaseApiResponse {
  success: false
  message: string
}

export type ApiResponse<T> = ApiResponseSuccess<T> | ApiResponseFailure

export enum HttpMethod {
  GET = 'GET',
  POST = 'POST',
  DELETE = 'DELETE'
}

export interface FetchOptions {
  endpoint: string
  method?: HttpMethod
  body?: Record<string, unknown>
}

export async function doFetch<Result = Record<string, unknown>>(options: FetchOptions): Promise<ApiResponse<Result>> {
  try {
    const { endpoint, method, body } = options
    const url = `${BASE_URL}${endpoint}`
    const headers = new Headers()

    if (body) headers.set('content-type', 'application/json')

    const response = await fetch(url, { method: method ?? HttpMethod.GET, headers, body: body ? JSON.stringify(body) : undefined })
    return await response.json() as ApiResponse<Result>
  } catch (error) {
    const errorStr = error instanceof Error ? error.message : JSON.stringify(error)
    console.error(`Fetch failed: ${errorStr}`)

    return { success: false, status: 500, message: 'An unexpected error occurred.' } satisfies ApiResponseFailure
  }
}
