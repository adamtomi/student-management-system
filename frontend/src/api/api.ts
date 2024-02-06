const BASE_URL = 'http://localhost:8080'

interface ApiResponseSuccess<Result> {
  success: true
  data: Result
}

interface ApiResponseFailure {
  success: false
  message: string
}

export type ApiResponse<Result> = ApiResponseSuccess<Result> | ApiResponseFailure

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

export async function doFetch<Result extends Record<string, any> = Record<string, any>>(options: FetchOptions): Promise<ApiResponse<Result>> {
  try {
    const { endpoint, method, body } = options
    const url = `${BASE_URL}${endpoint}`
    const headers = new Headers()

    if (body) headers.set('content-type', 'application/json')

    const response = await fetch(url, { method: method ?? HttpMethod.GET, headers, body: body ? JSON.stringify(body) : undefined })
    return await response.json() as ApiResponseSuccess<Result>
  } catch (error) {
    const errorStr = error instanceof Error ? error.message : JSON.stringify(error)
    console.error(`Fetch failed: ${errorStr}`)

    return { success: false, message: 'An unexpected error occurred.' } satisfies ApiResponseFailure
  }
}
